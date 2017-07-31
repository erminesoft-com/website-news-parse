package com.erminesoft.service;

import com.erminesoft.dto.ArticleDto;
import com.erminesoft.dto.IncomeListModelParser;
import com.erminesoft.dto.MainBlock;

import java.util.List;
import java.util.Map;

public interface ParserService {

    Map<String, Object> getFeed(MainBlock mainBlock);

    String getTitle(IncomeListModelParser incomeModelParse);

    String getLink(IncomeListModelParser incomeModelParse);

    String getImage(IncomeListModelParser incomeModelParse);

    String getDesc(IncomeListModelParser incomeModelParse);

    String getTime(IncomeListModelParser incomeModelParse);

    List<ArticleDto> getListArticles(IncomeListModelParser incomeListModelParse);

    IncomeListModelParser saveConfig(IncomeListModelParser incomeListModelParse);

    List<MainBlock> getListWebSite();

    IncomeListModelParser loadConfig(Long id);

    void deleteConfig(Long id);

    List<ArticleDto> getListArticlesById(Long id);

    boolean updateConfig(IncomeListModelParser incomeListModelParse);
}
