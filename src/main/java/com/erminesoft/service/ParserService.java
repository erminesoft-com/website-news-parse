package com.erminesoft.service;

import com.erminesoft.dto.ArticleDto;
import com.erminesoft.dto.IncomeListModelParser;
import com.erminesoft.dto.OneBlock;

import java.util.List;
import java.util.Map;

public interface ParserService {

    Map<String, Object> getFeed(OneBlock oneBlock);

    String getTitle(IncomeListModelParser incomeModelParse);

    String getLink(IncomeListModelParser incomeModelParse);

    String getImage(IncomeListModelParser incomeModelParse);

    String getDesc(IncomeListModelParser incomeModelParse);

    String getTime(IncomeListModelParser incomeModelParse);

    List<ArticleDto> getListArticles(IncomeListModelParser incomeListModelParse);

    IncomeListModelParser saveConfig(IncomeListModelParser incomeListModelParse);

    List<OneBlock> getListWebSite();

    IncomeListModelParser loadConfig(Long id);

    void deleteConfig(Long id);

    List<ArticleDto> getListArticlesById(Long id);

    Boolean updateConfig(IncomeListModelParser incomeListModelParse);
}
