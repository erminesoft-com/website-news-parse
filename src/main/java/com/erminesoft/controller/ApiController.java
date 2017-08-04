package com.erminesoft.controller;

import com.erminesoft.dto.ArticleDto;
import com.erminesoft.dto.IncomeListModelParser;
import com.erminesoft.dto.MainBlock;
import com.erminesoft.dto.RequestSendToUrlDto;
import com.erminesoft.service.FileService;
import com.erminesoft.service.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1", method = RequestMethod.GET)
public class ApiController {
    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ParserService parserService;

    @Autowired
    private FileService fileService;

    /**
     * Get bock feed news by site and strategy
     *
     * @return map with string block html, and number news
     */
    @RequestMapping(value = "/feed", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getFeedBlock(
            @RequestBody MainBlock oneBlock ) {
        logger.info("getOneFeedBlock with site = {}, strategy = {}", oneBlock.getSite(), oneBlock.getStrategy());
        Map<String, Object> oneFeed = parserService.getFeed(oneBlock);
        logger.info("Exit feed {}", oneFeed != null ? oneFeed.get("feed") : null);
        return oneFeed;
    }

    /**
     * Get title by default or strategy and key
     *
     * @return map
     */
    @RequestMapping(value = "/title", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTitle(
            @RequestBody IncomeListModelParser incomeModelParce) {

        if (StringUtils.isEmpty(incomeModelParce)) return null;

        logger.info("getTitle with default true = {} ", incomeModelParce.getTitle().isDefault());
        String result = parserService.getTitle(incomeModelParce);
        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        logger.info("Exit getTitle = {}", result);
        return map;
    }

    /**
     * Get Link by default or strategy and key
     *
     * @return map
     */
    @RequestMapping(value = "/link", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getLink(
            @RequestBody IncomeListModelParser incomeModelParce) {

        if (StringUtils.isEmpty(incomeModelParce)) return null;

        logger.info("getLink with default true = {} ", incomeModelParce.getLink().isDefault());
        String result = parserService.getLink(incomeModelParce);
        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        logger.info("Exit getTitle = {}", result);
        return map;
    }

    /**
     * Get Image Link by default or strategy and key
     *
     * @return map
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getImage(
            @RequestBody IncomeListModelParser incomeModelParce) {

        if (StringUtils.isEmpty(incomeModelParce)) return null;

        logger.info("getImage with default = {} ", incomeModelParce.getImage().isDefault());
        String result = parserService.getImage(incomeModelParce);
        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        logger.info("Exit getImage = {}", result);
        return map;
    }

    /**
     * Get Description text by default or strategy and key
     *
     * @return map
     */
    @RequestMapping(value = "/desc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getDescription(
            @RequestBody IncomeListModelParser incomeModelParce) {

        if (StringUtils.isEmpty(incomeModelParce)) return null;

        logger.info("getOneDescription with default true = {} ", incomeModelParce.getDesc().isDefault());
        String result = parserService.getDesc(incomeModelParce);
        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        logger.info("Exit getTitle = {}", result);
        return map;
    }

    /**
     * Get Time text by default or strategy and key
     *
     * @return map
     */
    @RequestMapping(value = "/time", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTime(
            @RequestBody IncomeListModelParser incomeModelParce) {

        if (StringUtils.isEmpty(incomeModelParce)) return null;

        logger.info("getTime with default true = {} ", incomeModelParce.getTime().isDefault());
        String result = parserService.getTime(incomeModelParce);
        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        logger.info("Exit getTime = {}", result);
        return map;
    }

    /**
     * Get list
     *
     * @return list<ArticleDto>
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleDto> getList(@RequestBody IncomeListModelParser incomeListModelParse) {

        if (StringUtils.isEmpty(incomeListModelParse)) return null;
        logger.info("Enter getList");

        List<ArticleDto> list = parserService.getListArticles(incomeListModelParse);

        logger.info("Exit getList() size list = {} ", list.size());
        return list;
    }

    /**
     * Save configuration for parse site
     *
     * @return IncomeListModelParser
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeListModelParser save(@RequestBody IncomeListModelParser incomeListModelParse) {

        if (StringUtils.isEmpty(incomeListModelParse)) return null;
        logger.info("Enter save");

        IncomeListModelParser result = parserService.saveConfig(incomeListModelParse);

        logger.info("Exit save() size result = {} ", result != null);
        return result;
    }

    /**
     * Save configuration for parse site
     *
     * @return map
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> update(@RequestBody IncomeListModelParser incomeListModelParse) {

        if (StringUtils.isEmpty(incomeListModelParse)) return null;
        logger.info("Enter update");

        Boolean result = parserService.updateConfig(incomeListModelParse);

        Map<String, Object> map = new HashMap<>();
        map.put("data", result);

        logger.info("Exit save() size result = {} ", result);
        return map;
    }

    /**
     * Load list web-site
     *
     * @return List<MainBlock>
     */
    @RequestMapping(value = "/load_list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MainBlock> loadListWebSite() {
        logger.info("Entering loadListWebSite()");
        List<MainBlock> result = parserService.getListWebSite();
        logger.info("Exit loadListWebSite() size result = {} ", result.size());
        return result;
    }

    /**
     * Load config by site id
     *
     * @return IncomeListModelParser
     */
    @RequestMapping(value = "/config/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeListModelParser loadConfig(@PathVariable Long id) {
        logger.info("Entering loadConfig() with id = {}", id);
        IncomeListModelParser result = parserService.loadConfig(id);
        logger.info("Leaving loadConfig()");
        return result;
    }

    /**
     * Delete site by id
     *
     * @return map
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteConfig(@PathVariable Long id) {
        logger.info("Entering deleteConfig() with id = {}", id);
        parserService.deleteConfig(id);
        logger.info("Leaving deleteConfig()");
        Map<String, Object> map = new HashMap<>();
        map.put("data", true);
        return map;
    }

    /**
     * Get list by id
     *
     * @return list<ArticleDto>
     */
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleDto> getListById(@PathVariable Long id) {
        if (StringUtils.isEmpty(id)) return null;
        logger.info("Enter getListById - {}", id);
        List<ArticleDto> list = parserService.getListArticlesById(id);
        logger.info("Exit getListById() size list = {} ", list.size());
        return list;
    }

    /**
     * Get file name for download
     *
     * @return String
     */
    @RequestMapping(value = "/file/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getFileName(@PathVariable Long id) {
        if (StringUtils.isEmpty(id)) return null;
        logger.info("Enter getFileName - {}", id);
        String result = fileService.createFile(id);
        logger.info("Exit getFileName() file name = {} ", result);
        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        return map;
    }

    /**
     * Download file by file name
     */
    @RequestMapping(value = "/download/{file_name:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        logger.info("Enter getFile - {}", fileName);
        fileService.downloadFile(fileName, response);
    }


    /**
     * Send parse news to URL
     */
    @RequestMapping(value = "/push/url", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> sendToUrl(@RequestBody RequestSendToUrlDto requestSendToUrlDto) {
        logger.info("Enter sendToUrl with requestSendToUrlDto = {}", requestSendToUrlDto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", true);
        logger.info("Leaving sendToUrl with result = {} ", true);
        return map;
    }

    /**
     * Send parse news to Email
     */
    @RequestMapping(value = "/push/email", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> sendToEmail(@RequestParam ("id") Long id,
                                           @RequestParam("email") String email,
                                           @RequestParam("format") String foramt
                                           ) {
        logger.info("Enter sendToUrl with sendToEmail, id = {}, email = {}, format = {}", id, email, foramt);
        Map<String, Object> map = new HashMap<>();
        map.put("data", true);
        logger.info("Leaving sendToEmail with result = {} ", true);
        return map;
    }
}
