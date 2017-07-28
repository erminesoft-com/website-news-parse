package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IncomeListModelParser {

    private OneBlock block;
    private RuleOneForBlock title;
    private RuleOneForBlock link;
    private RuleOneForBlock image;
    private RuleOneForBlock desc;
    private RuleOneForBlock time;

    public IncomeListModelParser() {
    }

    @JsonProperty(value = "block")
    public OneBlock getBlock() {
        return block;
    }

    public void setBlock(OneBlock block) {
        this.block = block;
    }

    @JsonProperty(value = "title")
    public RuleOneForBlock getTitle() {
        return title;
    }

    public void setTitle(RuleOneForBlock title) {
        this.title = title;
    }

    @JsonProperty(value = "link")
    public RuleOneForBlock getLink() {
        return link;
    }

    public void setLink(RuleOneForBlock link) {
        this.link = link;
    }

    @JsonProperty(value = "image")
    public RuleOneForBlock getImage() {
        return image;
    }

    public void setImage(RuleOneForBlock image) {
        this.image = image;
    }

    @JsonProperty(value = "desc")
    public RuleOneForBlock getDesc() {
        return desc;
    }

    public void setDesc(RuleOneForBlock desc) {
        this.desc = desc;
    }

    @JsonProperty(value = "time")
    public RuleOneForBlock getTime() {
        return time;
    }

    public void setTime(RuleOneForBlock time) {
        this.time = time;
    }
}
