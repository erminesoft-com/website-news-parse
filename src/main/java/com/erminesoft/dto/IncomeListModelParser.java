package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IncomeListModelParser {

    private MainBlock block;
    private RulesOneBlock title;
    private RulesOneBlock link;
    private RulesOneBlock image;
    private RulesOneBlock desc;
    private RulesOneBlock time;

    public IncomeListModelParser() {
    }

    @JsonProperty(value = "block")
    public MainBlock getBlock() {
        return block;
    }

    public void setBlock(MainBlock block) {
        this.block = block;
    }

    @JsonProperty(value = "title")
    public RulesOneBlock getTitle() {
        return title;
    }

    public void setTitle(RulesOneBlock title) {
        this.title = title;
    }

    @JsonProperty(value = "link")
    public RulesOneBlock getLink() {
        return link;
    }

    public void setLink(RulesOneBlock link) {
        this.link = link;
    }

    @JsonProperty(value = "image")
    public RulesOneBlock getImage() {
        return image;
    }

    public void setImage(RulesOneBlock image) {
        this.image = image;
    }

    @JsonProperty(value = "desc")
    public RulesOneBlock getDesc() {
        return desc;
    }

    public void setDesc(RulesOneBlock desc) {
        this.desc = desc;
    }

    @JsonProperty(value = "time")
    public RulesOneBlock getTime() {
        return time;
    }

    public void setTime(RulesOneBlock time) {
        this.time = time;
    }
}
