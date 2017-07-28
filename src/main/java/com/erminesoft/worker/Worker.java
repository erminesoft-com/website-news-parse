package com.erminesoft.worker;

import com.erminesoft.dto.OneBlock;
import com.erminesoft.dto.RuleOneForBlock;
import org.jsoup.select.Elements;

public interface Worker {

    Elements getElementsBySiteAndStrategy(OneBlock oneBlock);
    String getOneTitleFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText);
    String getOneLinkFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText);
    String getOneImageFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText);
    String getOneDescriptionFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText);
    String getOneTimeFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String inComeText);
}
