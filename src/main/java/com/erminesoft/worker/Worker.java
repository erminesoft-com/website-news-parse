package com.erminesoft.worker;

import com.erminesoft.dto.MainBlock;
import com.erminesoft.dto.RulesOneBlock;
import org.jsoup.select.Elements;

public interface Worker {

    /**
     * Parse by type strategy:
     * 1. by rss news
     * 2. by tag (<item></item>)
     * 3. by attribute (<item attribute="..."></item>)
     * 4. by attribute and value (<item attribute="value"></item>)
     * 5. by attribute and value starting (<item attribute="value starting"></item>)
     * 6. by attribute and value containing (<item attribute="value containing"></item>)
     * 7. by double select (First - attribute = class value=key1, Second - attribute = class value=key2)
     * 8. by double select (First - attribute = class, value=key, Second attribute and value starting)
     * @param oneBlock
     * @return elements(jsoup library class)
     */
    Elements getElementsBySiteAndStrategy(MainBlock oneBlock);

    /**
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @params oneBlockNewsDto, inComeText
     * @return string
     */
    String getOneTitleFromBlockHtml(RulesOneBlock oneBlockNewsDto, String incomingText);

    /**
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @params oneBlockNewsDto, inComeText
     * @return string
     */
    String getOneLinkFromBlockHtml(RulesOneBlock oneBlockNewsDto, String incomingText);

    /**
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @params oneBlockNewsDto, inComeText
     * @return string
     */
    String getOneImageFromBlockHtml(RulesOneBlock oneBlockNewsDto, String incomingText);

    /**
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @params oneBlockNewsDto, inComeText
     * @return string
     */
    String getOneDescriptionFromBlockHtml(RulesOneBlock oneBlockNewsDto, String incomingText);

    /**
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @params oneBlockNewsDto, inComeText
     * @return string
     */
    String getOneTimeFromBlockHtml(RulesOneBlock oneBlockNewsDto, String incomingText);
}
