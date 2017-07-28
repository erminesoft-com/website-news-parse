package com.erminesoft.service;

import com.erminesoft.dto.ArticleDto;
import com.erminesoft.dto.IncomeListModelParser;
import com.erminesoft.dto.OneBlock;

import java.util.List;
import java.util.Map;

public interface ParserService {

    Map<String, Object> getFeed(OneBlock oneBlock);

    String getTitle(IncomeListModelParser incomeModelParce);

    String getLink(IncomeListModelParser incomeModelParce);

    String getImage(IncomeListModelParser incomeModelParce);

    String getDesc(IncomeListModelParser incomeModelParce);

    String getTime(IncomeListModelParser incomeModelParce);

    List<ArticleDto> getListArticles(IncomeListModelParser incomeListModelParse);

    IncomeListModelParser saveConfig(IncomeListModelParser incomeListModelParse);

    List<OneBlock> getListWebSite();

    IncomeListModelParser loadConfig(Long id);

    void deleteConfig(Long id);

    List<ArticleDto> getListArticlesById(Long id);

    Boolean updateConfig(IncomeListModelParser incomeListModelParse);
}
