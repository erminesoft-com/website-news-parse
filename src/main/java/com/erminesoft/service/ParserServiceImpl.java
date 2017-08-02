package com.erminesoft.service;

import com.erminesoft.dto.*;
import com.erminesoft.model.Key;
import com.erminesoft.model.Rule;
import com.erminesoft.model.WebSite;
import com.erminesoft.repository.KeyRepository;
import com.erminesoft.repository.RuleRepository;
import com.erminesoft.repository.WebsiteRepository;
import com.erminesoft.worker.Worker;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.*;

@Service
public class ParserServiceImpl implements ParserService {

    private final static Logger logger = LoggerFactory.getLogger(ParserServiceImpl.class);

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private Worker parser;

    @Autowired
    private TimeService timeService;

    @Override
    public Map<String, Object> getFeed(MainBlock mainBlock) {
        Elements elements = parser.getElementsBySiteAndStrategy(mainBlock);
        if (elements != null && elements.size() != 0) {
            Map<String, Object> result = new HashMap<>();
            result.put("feed", elements.get(0).toString());
            result.put("size", elements.size());
            return result;
        }
        return Collections.emptyMap();
    }

    @Override
    public String getTitle(IncomeListModelParser incomeModelParse) {
        String block = getMainBlock(incomeModelParse);
        logger.info("getTitle() block = {}", block);
        String result = parser.getOneTitleFromBlockHtml(incomeModelParse.getTitle(), block);
        logger.info("getTitle() result = {}", result);
        return result;
    }

    @Override
    public String getLink(IncomeListModelParser incomeModelParse) {
        String block = getMainBlock(incomeModelParse);
        logger.info("getLink() block = {}", !StringUtils.isEmpty(block));
        return parser.getOneLinkFromBlockHtml(incomeModelParse.getLink(), block);
    }

    @Override
    public String getImage(IncomeListModelParser incomeModelParse) {
        String block = getMainBlock(incomeModelParse);
        logger.info("getImage() block = {}", !StringUtils.isEmpty(block));
        return parser.getOneImageFromBlockHtml(incomeModelParse.getImage(), block);
    }

    @Override
    public String getDesc(IncomeListModelParser incomeModelParse) {
        String block = getMainBlock(incomeModelParse);
        logger.info("getDesc() block = {}", !StringUtils.isEmpty(block));
        return parser.getOneDescriptionFromBlockHtml(incomeModelParse.getDesc(), block);
    }

    @Override
    public String getTime(IncomeListModelParser incomeModelParse) {
        String block = getMainBlock(incomeModelParse);
        logger.info("getTime() block exist = {}, length= {}", !StringUtils.isEmpty(block), block.length());
        String result = parser.getOneTimeFromBlockHtml(incomeModelParse.getTime(), block);
        logger.info("getTime() string after parse = {}", result);

        String finishTimeString = timeService.getFinishTime(result, incomeModelParse.getBlock().getSite());
        logger.info("Leaving getTime() - {}", finishTimeString);
        return finishTimeString;
    }

    @Override
    public List<ArticleDto> getListArticles(IncomeListModelParser incomeListModelParse) {

        if (incomeListModelParse.getBlock().getStrategy() == 1) {
            List<ArticleDto> result = getListByRrs(incomeListModelParse.getBlock().getSite());
            try {
                if (incomeListModelParse.getImage().getKey().getDefaultLink() != null) {
                    result.forEach(x -> x.setImageUrl(incomeListModelParse.getImage().getKey().getDefaultLink()));
                }
            } catch (NullPointerException e) {
                logger.warn("Get new by rss without default image");
            }
            return result;
        }

        List<ArticleDto> result = new ArrayList<>();
        int[] i = {1};
        Elements elements = parser.getElementsBySiteAndStrategy(incomeListModelParse.getBlock());

        elements.forEach(element ->  {
            ArticleDto articleDto = new ArticleDto();

            String block = element.toString();

            String title = parser.getOneTitleFromBlockHtml(incomeListModelParse.getTitle(), block);
            String link = parser.getOneLinkFromBlockHtml(incomeListModelParse.getLink(), block);

            if (incomeListModelParse.getImage() != null && incomeListModelParse.getImage().isEnable()) {
                String image = parser.getOneImageFromBlockHtml(incomeListModelParse.getImage(), block);
                articleDto.setImageUrl(image);
            }
            if (incomeListModelParse.getDesc() != null && incomeListModelParse.getDesc().isEnable()) {
                String desc = parser.getOneDescriptionFromBlockHtml(incomeListModelParse.getDesc(), block);
                articleDto.setDescription(desc);
            }
            if (incomeListModelParse.getTime() != null && incomeListModelParse.getTime().isEnable()) {
                String prepareTime = parser.getOneTimeFromBlockHtml(incomeListModelParse.getTime(), block);
                String finishTime = timeService.getFinishTime(prepareTime, incomeListModelParse.getBlock().getSite());
                articleDto.setDate(finishTime);
            }

            articleDto.setTitle(title);
            articleDto.setLink(link);
            articleDto.setId(i[0]++);

            result.add(articleDto);
        });
        return result;
    }

    @Override
    public IncomeListModelParser saveConfig(IncomeListModelParser incomeListModelParse) {

        WebSite webSite = transformFromIncomeListModelParseToWebSite(incomeListModelParse);

        WebSite saveWebSite = websiteRepository.save(webSite);

        return transformFromWebSite(saveWebSite);
    }

    @Override
    public List<MainBlock> getListWebSite() {
        List<WebSite> webSites = websiteRepository.findAll();
        return transformFromWebSitesList(webSites);
    }

    @Override
    public IncomeListModelParser loadConfig(Long siteId) {
        WebSite webSite = websiteRepository.findOne(siteId);
        return transformFromWebSite(webSite);
    }

    @Override
    public void deleteConfig(Long siteId) {
        WebSite webSite = websiteRepository.findOne(siteId);
        websiteRepository.delete(siteId);
        if (webSite.isTitleEnable()) {
            ruleRepository.delete(webSite.getTitle().getId());
            if (!webSite.getTitle().getIsDefault()) {
                keyRepository.delete(webSite.getTitle().getKey().getId());
            }
        }

        if (webSite.isLinkEnable()) {
            ruleRepository.delete(webSite.getLink().getId());
            if (!webSite.getLink().getIsDefault()) {
                keyRepository.delete(webSite.getLink().getKey().getId());
            }
        }

        if (webSite.isImageEnable()) {
            ruleRepository.delete(webSite.getImage().getId());
            if (!webSite.getImage().getIsDefault()) {
                keyRepository.delete(webSite.getImage().getKey().getId());
            }
        }

        if (webSite.isDescEnable()) {
            if (!webSite.getDesc().getIsDefault()) {
                ruleRepository.delete(webSite.getDesc().getId());
                keyRepository.delete(webSite.getDesc().getKey().getId());
            }
        }

        if (webSite.isTimeEnable()) {
            if (!webSite.getDate().getIsDefault()) {
                ruleRepository.delete(webSite.getDate().getId());
                keyRepository.delete(webSite.getDate().getKey().getId());
            }
        }
    }

    @Override
    public List<ArticleDto> getListArticlesById(Long id) {
        WebSite webSite = websiteRepository.findOne(id);

        if (webSite.getStrategy() == 1) {
            List<ArticleDto> result = getListByRrs(webSite.getSite());
            try{
                if (webSite.getImage().getKey().getDefaultLink() != null){
                    result.forEach(x-> x.setImageUrl(webSite.getImage().getKey().getDefaultLink()));
                }
            } catch (NullPointerException e) {
                logger.warn("Get new by rss without default image");
            }
            return result;
        }

        List<ArticleDto> result = new ArrayList<>();
        int[] i = {1};

        Elements elements = parser.getElementsBySiteAndStrategy(transform(webSite));

        elements.forEach(element -> {
            ArticleDto articleDto = new ArticleDto();

            String block = element.toString();

            if (webSite.isTitleEnable()) {
                String title = parser.getOneTitleFromBlockHtml(transform(webSite.getTitle()), block);
                articleDto.setTitle(title);
            }

            if (webSite.isLinkEnable()) {
                String link = parser.getOneLinkFromBlockHtml(transform(webSite.getLink()), block);
                articleDto.setLink(link);
            }

            if (webSite.isImageEnable()) {
                String image = parser.getOneImageFromBlockHtml(transform(webSite.getImage()), block);
                articleDto.setImageUrl(image);
            }

            if (webSite.isDescEnable()) {
                String desc = parser.getOneDescriptionFromBlockHtml(transform(webSite.getDesc()), block);
                articleDto.setDescription(desc);
            }

            if (webSite.isTimeEnable()) {
                String prepareTime = parser.getOneTimeFromBlockHtml(transform(webSite.getDate()), block);
                String finishTime = timeService.getFinishTime(prepareTime, webSite.getSite());
                articleDto.setDate(finishTime);
            }

            articleDto.setId(i[0]++);
            result.add(articleDto);
        });
        return result;
    }

    private MainBlock transform(WebSite webSite) {
        MainBlock oneBlock = new MainBlock();
        oneBlock.setSite(webSite.getSite());
        oneBlock.setStrategy(webSite.getStrategy());
        oneBlock.setKey(webSite.getKeyFirst());
        if (webSite.getKeySecond() != null) {
            oneBlock.setKeySecond(webSite.getKeySecond());
        }
        if (webSite.getId() != 0) {
            oneBlock.setId(webSite.getId());
        }
        return oneBlock;
    }

    @Override
    public boolean updateConfig(IncomeListModelParser incomeListModelParse) {
        WebSite incomeSite = transformFromIncomeListModelParseToWebSite(incomeListModelParse);
        websiteRepository.save(incomeSite);
        return true;
    }

    private RulesOneBlock transform(Rule rule) {
        RulesOneBlock result = new RulesOneBlock();
        result.setDefault(rule.getIsDefault());
        result.setStrategy(rule.getStrategy());
        if (rule.getKey() != null) {
            KeyDto keyDto = new KeyDto(rule.getKey().getKeyOne(), rule.getKey().getKeyTwo(), rule.getKey().getPrefix(),
                    rule.getKey().getDefaultLink());
            result.setKey(keyDto);
        }
        return result;
    }

    private IncomeListModelParser transformFromWebSite(WebSite webSite) {
        IncomeListModelParser result = new IncomeListModelParser();

        MainBlock oneBlock = new MainBlock();

        oneBlock.setId(webSite.getId());
        oneBlock.setSite(webSite.getSite());
        oneBlock.setName(webSite.getName());
        oneBlock.setStrategy(webSite.getStrategy());

        if (oneBlock.getStrategy() == 1) {
            result.setBlock(oneBlock);
            if (webSite.isImageEnable()) {
                RulesOneBlock imageDeafult = new RulesOneBlock();
                imageDeafult.setEnable(true);
                imageDeafult.setId(webSite.getImage().getId());
                KeyDto keyDto = new KeyDto();
                keyDto.setId(webSite.getImage().getKey().getId());
                keyDto.setDefaultLink(webSite.getImage().getKey().getDefaultLink());
                imageDeafult.setKey(keyDto);
                result.setImage(imageDeafult);
            }
            return result;
        }

        oneBlock.setKey(webSite.getKeyFirst());
        if (webSite.getKeySecond() != null) {
            oneBlock.setKeySecond(webSite.getKeySecond());
        }
        result.setBlock(oneBlock);


        RulesOneBlock title = new RulesOneBlock();
        title.setEnable(webSite.isTitleEnable());
        if (webSite.isTitleEnable()) {
            title.setId(webSite.getTitle().getId());
            title.setDefault(webSite.getTitle().getIsDefault());
            if (!title.isDefault()) {
                title.setStrategy(webSite.getTitle().getStrategy());
                KeyDto keyDto = new KeyDto();
                keyDto.setId(webSite.getTitle().getKey().getId());
                keyDto.setOne(webSite.getTitle().getKey().getKeyOne());
                if (webSite.getTitle().getKey().getKeyTwo() != null)
                    keyDto.setTwo(webSite.getTitle().getKey().getKeyTwo());
                title.setKey(keyDto);
            }
        }
        result.setTitle(title);

        RulesOneBlock link = new RulesOneBlock();
        link.setEnable(webSite.isLinkEnable());
        if (webSite.isLinkEnable()) {
            link.setId(webSite.getLink().getId());
            link.setDefault(webSite.getLink().getIsDefault());
            if (!link.isDefault()) {
                link.setStrategy(webSite.getLink().getStrategy());
                KeyDto keyDto = new KeyDto();
                keyDto.setId(webSite.getLink().getKey().getId());
                keyDto.setOne(webSite.getLink().getKey().getKeyOne());
                if (webSite.getLink().getKey().getKeyTwo() != null)
                    keyDto.setTwo(webSite.getLink().getKey().getKeyTwo());

                if (webSite.getLink().getKey().getPrefix() != null) {
                    keyDto.setLinkPrefix(webSite.getLink().getKey().getPrefix());
                }
                link.setKey(keyDto);
            }
        }
        result.setLink(link);

        RulesOneBlock image = new RulesOneBlock();
        image.setEnable(webSite.isImageEnable());
        if (webSite.isImageEnable()) {
            image.setId(webSite.getImage().getId());
            image.setDefault(webSite.getImage().getIsDefault());
            if (!image.isDefault()) {
                image.setStrategy(webSite.getImage().getStrategy());
                KeyDto keyDto = new KeyDto();
                keyDto.setId(webSite.getImage().getKey().getId());
                if (webSite.getImage().getKey().getDefaultLink() != null) {
                    keyDto.setDefaultLink(webSite.getImage().getKey().getDefaultLink());
                }
                if (webSite.getImage().getKey().getKeyOne() != null) {
                    keyDto.setOne(webSite.getImage().getKey().getKeyOne());
                }
                if (webSite.getImage().getKey().getKeyTwo() != null)
                    keyDto.setTwo(webSite.getImage().getKey().getKeyTwo());
                if (webSite.getImage().getKey().getPrefix() != null) {
                    keyDto.setLinkPrefix(webSite.getImage().getKey().getPrefix());
                }
                image.setKey(keyDto);
            }
        }
        result.setImage(image);

        RulesOneBlock desc = new RulesOneBlock();
        desc.setEnable(webSite.isDescEnable());
        if (webSite.isDescEnable()) {
            desc.setId(webSite.getDesc().getId());
            desc.setDefault(webSite.getDesc().getIsDefault());
            if (!desc.isDefault()) {
                desc.setStrategy(webSite.getDesc().getStrategy());
                KeyDto keyDto = new KeyDto();
                keyDto.setId(webSite.getDesc().getKey().getId());
                keyDto.setOne(webSite.getDesc().getKey().getKeyOne());
                if (webSite.getDesc().getKey().getKeyTwo() != null)
                    keyDto.setTwo(webSite.getDesc().getKey().getKeyTwo());
                desc.setKey(keyDto);
            }
        }
        result.setDesc(desc);

        RulesOneBlock time = new RulesOneBlock();
        time.setEnable(webSite.isTimeEnable());
        if (webSite.isTimeEnable()) {
            time.setId(webSite.getDate().getId());
            time.setEnable(webSite.isTimeEnable());
            time.setDefault(webSite.getDate().getIsDefault());
            if (!time.isDefault()) {
                time.setStrategy(webSite.getDate().getStrategy());
                KeyDto keyDto = new KeyDto();
                keyDto.setId(webSite.getDate().getKey().getId());
                keyDto.setOne(webSite.getDate().getKey().getKeyOne());
                if (webSite.getDate().getKey().getKeyTwo() != null)
                    keyDto.setTwo(webSite.getDate().getKey().getKeyTwo());
                time.setKey(keyDto);
            }
        }
        result.setTime(time);
        return result;
    }

    private List<MainBlock> transformFromWebSitesList(List<WebSite> list) {
        List<MainBlock> result = new ArrayList<>();
        list.forEach(x -> {
            MainBlock oneBlock = new MainBlock();
            oneBlock.setId(x.getId());
            oneBlock.setSite(x.getSite());
            oneBlock.setName(x.getName());
            result.add(oneBlock);
        });
        return result;
    }

    private WebSite transformFromIncomeListModelParseToWebSite(IncomeListModelParser incomeListModelParse) {

        MainBlock block = incomeListModelParse.getBlock();

        WebSite webSite = new WebSite();
        if (incomeListModelParse.getBlock().getId() != null) {
            webSite.setId(incomeListModelParse.getBlock().getId());
        }
        webSite.setSite(block.getSite());
        webSite.setName(block.getName());
        webSite.setStrategy(block.getStrategy());
        if (block.getKey() != null){
            webSite.setKeyFirst(block.getKey());
        }
        if (block.getStrategy() > 3) {
            webSite.setKeySecond(block.getKeySecond());
        }

        if (incomeListModelParse.getTitle() != null && incomeListModelParse.getTitle().isEnable()) {
            Rule title = new Rule();
            title.setIsDefault(incomeListModelParse.getTitle().isDefault());
            if (incomeListModelParse.getTitle().getId() != null) {
                title.setId(incomeListModelParse.getTitle().getId());
            }
            if (!title.getIsDefault()) {
                title.setStrategy(incomeListModelParse.getTitle().getStrategy());
                Key keyTitle = new Key();
                if (incomeListModelParse.getTitle().getKey().getId() != null) {
                    keyTitle.setId(incomeListModelParse.getTitle().getKey().getId());
                }
                keyTitle.setKeyOne(incomeListModelParse.getTitle().getKey().getOne());
                if (incomeListModelParse.getTitle().getKey().getTwo() != null) {
                    keyTitle.setKeyTwo(incomeListModelParse.getTitle().getKey().getTwo());
                }
                if (incomeListModelParse.getTitle().getKey().getLinkPrefix() != null) {
                    keyTitle.setPrefix(incomeListModelParse.getTitle().getKey().getLinkPrefix());
                }
                Key saveKeyTitle = keyRepository.save(keyTitle);
                title.setKey(saveKeyTitle);
            }
            webSite.setTitleEnable(incomeListModelParse.getTitle() != null);
            Rule saveTitle = ruleRepository.save(title);
            webSite.setTitle(saveTitle);
        } else {
            webSite.setTitleEnable(false);
        }

        if (incomeListModelParse.getLink() != null && incomeListModelParse.getLink().isEnable()) {
            Rule link = new Rule();
            link.setIsDefault(incomeListModelParse.getLink().isDefault());
            if (incomeListModelParse.getLink().getId() != null) {
                link.setId(incomeListModelParse.getLink().getId());
            }
            if (!link.getIsDefault()) {
                link.setStrategy(incomeListModelParse.getLink().getStrategy());
                Key keyLink = new Key();
                if (incomeListModelParse.getLink().getKey().getId() != null) {
                    keyLink.setId(incomeListModelParse.getLink().getKey().getId());
                }
                keyLink.setKeyOne(incomeListModelParse.getLink().getKey().getOne());

                if (incomeListModelParse.getLink().getKey().getTwo() != null) {
                    keyLink.setKeyTwo(incomeListModelParse.getLink().getKey().getTwo());
                }

                if (incomeListModelParse.getLink().getKey().getLinkPrefix() != null) {
                    keyLink.setPrefix(incomeListModelParse.getLink().getKey().getLinkPrefix());
                }

                Key saveKeyLink = keyRepository.save(keyLink);
                link.setKey(saveKeyLink);
            }
            webSite.setLinkEnable(incomeListModelParse.getLink() != null);
            Rule saveLink = ruleRepository.save(link);
            webSite.setLink(saveLink);
        } else {
            webSite.setLinkEnable(false);
        }

        if (incomeListModelParse.getImage() != null && incomeListModelParse.getImage().isEnable()) {
            Rule image = new Rule();
            image.setIsDefault(incomeListModelParse.getImage().isDefault());
            if (incomeListModelParse.getImage().getId() != null) {
                image.setId(incomeListModelParse.getImage().getId());
            }
            if (!image.getIsDefault()) {
                image.setStrategy(incomeListModelParse.getImage().getStrategy());
                Key keyImage = new Key();
                if (incomeListModelParse.getImage().getKey().getId() != null) {
                    keyImage.setId(incomeListModelParse.getImage().getKey().getId());
                }
                if (!StringUtils.isEmpty(incomeListModelParse.getImage().getKey().getDefaultLink())) {
                    keyImage.setDefaultLink(incomeListModelParse.getImage().getKey().getDefaultLink());
                }
                if (incomeListModelParse.getImage().getKey().getOne() != null) {
                    keyImage.setKeyOne(incomeListModelParse.getImage().getKey().getOne());
                }
                if (incomeListModelParse.getImage().getKey().getTwo() != null) {
                    keyImage.setKeyTwo(incomeListModelParse.getImage().getKey().getTwo());
                }
                if (!StringUtils.isEmpty(incomeListModelParse.getImage().getKey().getLinkPrefix())) {
                    keyImage.setPrefix(incomeListModelParse.getImage().getKey().getLinkPrefix());
                }
                if (incomeListModelParse.getImage().getKey().getTwo() != null) {
                    keyImage.setKeyTwo(incomeListModelParse.getImage().getKey().getTwo());
                }
                Key saveKeyImage = keyRepository.save(keyImage);
                image.setKey(saveKeyImage);
            }
            webSite.setImageEnable(incomeListModelParse.getImage() != null);
            Rule saveImage = ruleRepository.save(image);
            webSite.setImage(saveImage);
        } else {
            webSite.setImageEnable(false);
        }

        if (incomeListModelParse.getDesc() != null && incomeListModelParse.getDesc().isEnable()) {
            Rule desc = new Rule();
            desc.setIsDefault(incomeListModelParse.getDesc().isDefault());
            if (incomeListModelParse.getDesc().getId() != null) {
                desc.setId(incomeListModelParse.getDesc().getId());
            }
            if (!desc.getIsDefault()) {
                desc.setStrategy(incomeListModelParse.getDesc().getStrategy());
                Key keyDesc = new Key();
                if (incomeListModelParse.getDesc().getKey().getId() != null) {
                    keyDesc.setId(incomeListModelParse.getDesc().getKey().getId());
                }
                keyDesc.setKeyOne(incomeListModelParse.getDesc().getKey().getOne());
                keyDesc.setKeyTwo(incomeListModelParse.getDesc().getKey().getTwo());
                Key saveKeyDesc = keyRepository.save(keyDesc);
                desc.setKey(saveKeyDesc);
            }
            webSite.setDescEnable(incomeListModelParse.getDesc() != null);
            Rule saveDesc = ruleRepository.save(desc);
            webSite.setDesc(saveDesc);
        } else {
            webSite.setDescEnable(false);
        }

        if (incomeListModelParse.getTime() != null && incomeListModelParse.getTime().isEnable()) {
            Rule time = new Rule();
            time.setIsDefault(incomeListModelParse.getTime().isDefault());
            if (incomeListModelParse.getTime().getId() != null) {
                time.setId(incomeListModelParse.getTime().getId());
            }
            if (!time.getIsDefault()) {
                time.setStrategy(incomeListModelParse.getTime().getStrategy());
                Key keyTime = new Key();
                if (incomeListModelParse.getTime().getKey().getId() != null) {
                    keyTime.setId(incomeListModelParse.getTime().getKey().getId());
                }
                keyTime.setKeyOne(incomeListModelParse.getTime().getKey().getOne());
                keyTime.setKeyTwo(incomeListModelParse.getTime().getKey().getTwo());
                Key saveKeyTime = keyRepository.save(keyTime);
                time.setKey(saveKeyTime);
            } else {
                time.setIsDefault(true);
            }
            webSite.setTimeEnable(incomeListModelParse.getTime() != null);
            Rule saveTime = ruleRepository.save(time);
            webSite.setDate(saveTime);
        } else {
            webSite.setTimeEnable(false);
        }
        logger.info("Web-site transform - {}", webSite.toString());
        return webSite;
    }

    private String getMainBlock(IncomeListModelParser incomeModelParce) {
        Map<String, Object> oneFeed = getFeed(incomeModelParce.getBlock());
        return (String) oneFeed.get("feed");
    }

    private List<ArticleDto> getListByRrs(String site) {
        logger.info("Entering getListByRrs() site - {}", site);
        List<ArticleDto> result = new ArrayList<>();
        try {
            URL feedUrl = new URL(site);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            int id = 1;
            for (SyndEntry syndEntry : feed.getEntries()) {
                ArticleDto articleDto = new ArticleDto();
                articleDto.setId(id++);
                articleDto.setTitle(syndEntry.getTitle());
                articleDto.setLink(syndEntry.getLink());
                articleDto.setDescription(syndEntry.getDescription().getValue());
                articleDto.setDate(syndEntry.getPublishedDate().toString());
                result.add(articleDto);
            }
        } catch (Exception ex) {
            logger.warn("getListByRrs() error", ex.getLocalizedMessage());
        }

        logger.info("Leaving getListByRrs() size result - {}", result.size());
        return result;
    }

}
