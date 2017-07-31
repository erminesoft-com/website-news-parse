package com.erminesoft.worker;

import com.erminesoft.dto.OneBlock;
import com.erminesoft.dto.RuleOneForBlock;
import org.jsoup.select.Elements;

public interface Worker {

    /**
     *@param oneBlock
     * Parse by type strategy:
     * 1. by rss news
     * 2. by tag (<item></item>)
     * 3. by attribute (<item attribute="..."></item>)
     * 4. by attribute and value (<item attribute="value"></item>)
     * 5. by attribute and value starting (<item attribute="value starting"></item>)
     * 6. by attribute and value containing (<item attribute="value containing"></item>)
     * 7. by double select (First - attribute = class value=key1, Second - attribute = class value=key2)
     * 8. by double select (First - attribute = class, value=key, Second attribute and value starting)
     * @return elements(jsoup library class)
     */
    Elements getElementsBySiteAndStrategy(OneBlock oneBlock);

    /**
     *@params oneBlockNewsDto, inComeText
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @return string
     */
    String getOneTitleFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String incomingText);

    /**
     *@params oneBlockNewsDto, inComeText
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @return string
     */
    String getOneLinkFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String incomingText);

    /**
     *@params oneBlockNewsDto, inComeText
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @return string
     */
    String getOneImageFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String incomingText);

    /**
     *@params oneBlockNewsDto, inComeText
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @return string
     */
    String getOneDescriptionFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String incomingText);

    /**
     *@params oneBlockNewsDto, inComeText
     * Parse by type strategy oneBlockNewsDto:
     * 1. From first key to second
     * 2. by tag (<item></item>)
     * 3. by attribute and attribute value((<item attribute="value"></item>)
     * @return string
     */
    String getOneTimeFromBlockHtml(RuleOneForBlock oneBlockNewsDto, String incomingText);
}
