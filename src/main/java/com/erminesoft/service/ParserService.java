package com.erminesoft.service;

import com.erminesoft.dto.ArticleDto;
import com.erminesoft.dto.IncomeListModelParser;
import com.erminesoft.dto.MainBlock;

import java.util.List;
import java.util.Map;

public interface ParserService {

    /**
     *Get date from string.
     *
     *@param mainBlock
     *@return map
     */
    Map<String, Object> getFeed(MainBlock mainBlock);

    /**
     *Get title from incomeModelParse.
     *
     *@param incomeModelParse
     *@return String
     */
    String getTitle(IncomeListModelParser incomeModelParse);

    /**
     *Get link from incomeModelParse.
     *
     *@param incomeModelParse
     *@return String
     */
    String getLink(IncomeListModelParser incomeModelParse);

    /**
     *Get image from incomeModelParse.
     *
     *@param incomeModelParse
     *@return String
     */
    String getImage(IncomeListModelParser incomeModelParse);

    /**
     *Get description from incomeModelParse.
     *
     *@param incomeModelParse
     *@return String
     */
    String getDesc(IncomeListModelParser incomeModelParse);

    /**
     *Get time from incomeModelParse.
     *
     *@param incomeModelParse
     *@return String
     */
    String getTime(IncomeListModelParser incomeModelParse);

    /**
     *Get List<ArticleDto> from incomeModelParse.
     *
     *@param incomeListModelParse
     *@return List<ArticleDto>
     */
    List<ArticleDto> getListArticles(IncomeListModelParser incomeListModelParse);

    /**
     *Save configuration for parsing one site.
     *
     *@param incomeListModelParse
     *@return IncomeListModelParser
     */
    IncomeListModelParser saveConfig(IncomeListModelParser incomeListModelParse);

    /**
     *Get list saved sites configurations
     *
     *@return List<MainBlock>
     */
    List<MainBlock> getListWebSite();

    /**
     *Get configuration parsing for particular site
     *@param siteId
     *@return List<MainBlock>
     */
    IncomeListModelParser loadConfig(Long siteId);

    /**
     *Delete configuration parsing for particular site
     *@param siteId
     */
    void deleteConfig(Long siteId);

    /**
     *Load list articles by site id
     *@param siteId
     *@return List<ArticleDto>
     */
    List<ArticleDto> getListArticlesById(Long siteId);

    /**
     *Update configuration for parsing one site.
     *
     *@param incomeListModelParse
     *@return boolean
     */
    boolean updateConfig(IncomeListModelParser incomeListModelParse);
}
