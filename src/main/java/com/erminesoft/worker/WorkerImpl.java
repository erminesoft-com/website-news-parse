package com.erminesoft.worker;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.erminesoft.dto.OneBlock;
import com.erminesoft.dto.RuleOneForBlock;
import com.erminesoft.exception.CommonProblemException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WorkerImpl implements Worker {

    private final static Logger logger = LoggerFactory.getLogger(WorkerImpl.class);

    private final static String USER_AGENT = "Chrome";
    private final static int TIMEOUT = 10 * 1000;


    @Override
    public Elements getElementsBySiteAndStrategy(OneBlock oneBlock) {
        logger.info("Enter getElementsBySIteAndStategy with site {}, strategy = {}", oneBlock.getSite(), oneBlock.getStrategy());

        Document doc;
        try {
            doc = Jsoup.connect(oneBlock.getSite()).userAgent(USER_AGENT).ignoreContentType(true).timeout(TIMEOUT).get();
        } catch (Exception e) {
            throw new CommonProblemException(e.getLocalizedMessage());
        }

        Elements elements;

        switch (oneBlock.getStrategy()) {
            case 1:
                elements = getBodyRss(oneBlock.getSite());
                break;
            case 2:
                elements = doc.select(oneBlock.getKey());
                break;
            case 3:
                elements = doc.getElementsByAttribute(oneBlock.getKey());
                break;
            case 4:
                elements = doc.getElementsByAttributeValue(oneBlock.getKey(), oneBlock.getKeySecond());
                break;
            case 5:
                elements = doc.getElementsByAttributeValueStarting(oneBlock.getKey(), oneBlock.getKeySecond());
                break;
            case 6:
                elements = doc.getElementsByAttributeValueContaining(oneBlock.getKey(), oneBlock.getKeySecond());
                break;
            case 7:
                Elements result = new Elements();
                Elements temp = doc.getElementsByAttributeValue("class", oneBlock.getKey());
                temp.forEach(x -> x.select(oneBlock.getKeySecond()).forEach(y -> result.add(y)));
                elements = result;
                break;
            case 8:
                elements = doc.getElementsByAttributeValue("class", oneBlock.getKey())
                        .first().getElementsByAttributeValueStarting("class", oneBlock.getKeySecond());
                break;
            default:
                logger.warn("Undefined strategy in feed");
                throw new CommonProblemException("First key is undefined");
        }
        return elements;
    }

    private Elements getBodyRss(String site) {
        logger.info("Entering getBodyRss() site - {}", site);
        Elements elements = new Elements();
        try {
            URL feedUrl = new URL(site);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            elements = new Elements();
            for (SyndEntry syndEntry : feed.getEntries()) {
                Document doc = Jsoup.parseBodyFragment(syndEntry.toString());
                Element body = doc.body();
                elements.add(body);
            }
        } catch (Exception ex) {
            logger.warn("getListByRrs() error", ex.getLocalizedMessage());
        }

        logger.info("Leaving getBodyRss() size result - {}", elements.size());
        return elements;
    }

    @Override
    public String getOneTitleFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText) {

        if (oneBlockNewsDto.isDefault()) {
            logger.info("Parse one title block by default");
            Document doc = Jsoup.parse(inComeText);
            String title;
            try {
                title = doc.select("a").first().attr("title");
            } catch (NullPointerException e) {
                logger.warn("Error parse title by default");
                return null;
            }
            return title;
        }
        logger.info("Parse one block by default with key one = {}", oneBlockNewsDto.getKey().getOne());
        logger.info("Parse one block by default with key two = {}", oneBlockNewsDto.getKey().getTwo());

        String result = null;

        switch (oneBlockNewsDto.getStrategy()) {
            case 1:
                logger.info("Get title by strategy 1");
                result = getTextFromHtml(inComeText, oneBlockNewsDto.getKey().getOne(), oneBlockNewsDto.getKey().getTwo());
                break;
            case 2:
                logger.info("Get title by strategy 2");
                Document doc = Jsoup.parse(inComeText);
                result = doc.select(oneBlockNewsDto.getKey().getOne()).text();
                break;
            case 3:
                logger.info("Get title by strategy 3");
                Document doc1 = Jsoup.parse(inComeText);
                result = doc1.select(oneBlockNewsDto.getKey().getOne()).attr(oneBlockNewsDto.getKey().getTwo());
                break;
        }
        return result;
    }

    @Override
    public String getOneLinkFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText) {

        if (oneBlockNewsDto.isDefault()) {
            logger.info("Parse one block for link by default");
            Document doc = Jsoup.parse(inComeText);
            String link;
            try {
                link = doc.select("a").attr("href");
            } catch (NullPointerException e) {
                logger.warn("Error parse link by default");
                return null;
            }
            return link;
        }

        String result = null;

        switch (oneBlockNewsDto.getStrategy()) {
            case 1:
                logger.info("Get link by strategy 1");
                result = getTextFromHtml(inComeText, oneBlockNewsDto.getKey().getOne(), oneBlockNewsDto.getKey().getTwo());
                if (oneBlockNewsDto.getKey().getLinkPrefix() != null) {
                    result = oneBlockNewsDto.getKey().getLinkPrefix() + result;
                    logger.info("getOneLinkFromBlockHtml() with prefix, link = {}", result);
                }
                break;
            case 2:
                Document doc = Jsoup.parse(inComeText);
                logger.info("Get link by strategy 2");
                result = doc.select(oneBlockNewsDto.getKey().getOne()).text();
                if (oneBlockNewsDto.getKey().getLinkPrefix() != null) {
                    result = oneBlockNewsDto.getKey().getLinkPrefix() + result;
                    logger.info("getOneLinkFromBlockHtml() with prefix, link = {}", result);
                }

                break;
            case 3:
                logger.info("Get link by strategy 3");
                Document doc1 = Jsoup.parse(inComeText);
                result = doc1.select(oneBlockNewsDto.getKey().getOne()).attr(oneBlockNewsDto.getKey().getTwo());
                if (oneBlockNewsDto.getKey().getLinkPrefix() != null) {
                    result = oneBlockNewsDto.getKey().getLinkPrefix() + result;
                    logger.info("getOneLinkFromBlockHtml() with prefix, link = {}", result);
                }
                break;
        }
        return result;
    }

    @Override
    public String getOneImageFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText) {
        logger.info("Entering getOneImageFromBlockHtml()");
        if (oneBlockNewsDto == null) return null;

        if (!oneBlockNewsDto.isDefault() && oneBlockNewsDto.getKey() == null)
            return null;

        if (!oneBlockNewsDto.isDefault() && oneBlockNewsDto.getKey().getDefaultLink() != null) {
            return oneBlockNewsDto.getKey().getDefaultLink();
        }

        if (oneBlockNewsDto.isDefault()) {
            logger.info("Parse one block for image by default");
            Document doc = Jsoup.parse(inComeText);
            String image;
            try {
                image = doc.select("img").attr("abs:src");
            } catch (NullPointerException e) {
                logger.warn("Error parse image by default");
                return null;
            }
            return image;
        }
        logger.info("Parse one block by default with key one = {}", oneBlockNewsDto.getKey().getOne());
        logger.info("Parse one block by default with key two = {}", oneBlockNewsDto.getKey().getTwo());

        String result = null;

        switch (oneBlockNewsDto.getStrategy()) {
            case 1:
                result = getTextFromHtml(inComeText, oneBlockNewsDto.getKey().getOne(), oneBlockNewsDto.getKey().getTwo());
                if (oneBlockNewsDto.getKey().getLinkPrefix() != null) {
                    result = oneBlockNewsDto.getKey().getLinkPrefix() + result;
                    logger.info("getOneImageFromBlockHtml() with prefix, link = {}", result);
                }
                break;
            case 2:
                Document doc = Jsoup.parse(inComeText);
                logger.info("Get image by strategy 2");
                result = doc.select(oneBlockNewsDto.getKey().getOne()).text();
                if (oneBlockNewsDto.getKey().getLinkPrefix() != null) {
                    result = oneBlockNewsDto.getKey().getLinkPrefix() + result;
                    logger.info("getOneImageFromBlockHtml() with prefix, link = {}", result);
                }
                break;
            case 3:
                logger.info("Get image by strategy 3");
                Document doc1 = Jsoup.parse(inComeText);
                result = doc1.select(oneBlockNewsDto.getKey().getOne()).attr(oneBlockNewsDto.getKey().getTwo());
                if (oneBlockNewsDto.getKey().getLinkPrefix() != null) {
                    result = oneBlockNewsDto.getKey().getLinkPrefix() + result;
                    logger.info("getOneImageFromBlockHtml() with prefix, link = {}", result);
                }
                break;
        }
        logger.info("Leaving getOneImageFromBlockHtml() with result = {}", result);
        return result;
    }

    @Override
    public String getOneDescriptionFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText) {
        if (oneBlockNewsDto == null) return null;

        if (oneBlockNewsDto.isDefault()) {
            logger.info("Parse one block for image by default");
            Document doc = Jsoup.parse(inComeText);
            String desc;
            try {
                desc = doc.select("p").text();
            } catch (NullPointerException e) {
                logger.warn("Error parse desc by default");
                return null;
            }
            return desc;
        }
        logger.info("Parse one block by default with key one = {}", oneBlockNewsDto.getKey().getOne());
        logger.info("Parse one block by default with key two = {}", oneBlockNewsDto.getKey().getTwo());

        String result = null;

        switch (oneBlockNewsDto.getStrategy()) {
            case 1:
                result = getTextFromHtml(inComeText, oneBlockNewsDto.getKey().getOne(), oneBlockNewsDto.getKey().getTwo());
                break;
            case 2:
                logger.info("Get desc by strategy 2");
                Document doc1 = Jsoup.parse(inComeText);
                result = doc1.select(oneBlockNewsDto.getKey().getOne()).text();
                break;
            case 3:
                logger.info("Get desc by strategy 2");
                Document doc = Jsoup.parse(inComeText);
                result = doc.select(oneBlockNewsDto.getKey().getOne()).attr(oneBlockNewsDto.getKey().getTwo());
                break;
        }
        return result;
    }

    @Override
    public String getOneTimeFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText) {
        if (oneBlockNewsDto == null) return null;
        logger.info("Entering getOneTimeFromBlockHtml() with strategy = {}", oneBlockNewsDto.getStrategy());

        if (oneBlockNewsDto.isDefault()) {
            logger.info("Parse one block for time by default");
            Document doc = Jsoup.parse(inComeText);
            String time;
            try {
                time = doc.select("time").attr("datetime");
            } catch (NullPointerException e) {
                logger.warn("Error parse time by default");
                return null;
            }
            return time;
        }
        logger.info("Parse one block by default with key one = {}", oneBlockNewsDto.getKey().getOne());
        logger.info("Parse one block by default with key two = {}", oneBlockNewsDto.getKey().getTwo());

        String result = null;

        switch (oneBlockNewsDto.getStrategy()) {
            case 1:
                logger.info("Get time by strategy 1");
                result = getTextFromHtml(inComeText, oneBlockNewsDto.getKey().getOne(), oneBlockNewsDto.getKey().getTwo());
                break;
            case 2:
                logger.info("Get time by strategy 2");
                Document doc1 = Jsoup.parse(inComeText);
                result = doc1.select(oneBlockNewsDto.getKey().getOne()).text();
                break;
            case 3:
                logger.info("Get time by strategy 3");
                Document doc = Jsoup.parse(inComeText);
                result = doc.select(oneBlockNewsDto.getKey().getOne()).attr(oneBlockNewsDto.getKey().getTwo());
                break;
        }
        logger.info("Leaving getOneTimeFromBlockHtml() with result = {}", result);
        return result;
    }

    private String getTextFromHtml(String html, String patternStart, String patternEnd) {
        String HTML_P_TAG_PATTERN = "(?i)" + patternStart + "(.+?)" + patternEnd;
        Matcher matcherDesc;
        Pattern patternTag_P = Pattern.compile(HTML_P_TAG_PATTERN);

        String result = null;
        matcherDesc = patternTag_P.matcher(html);
        while (matcherDesc.find()) {
            result = matcherDesc.group(1);
            break;
        }
        return result;
    }
}
