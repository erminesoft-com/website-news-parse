package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OneBlock {

    private Long id;
    private String site;
    private String name;
    private Integer strategy;
    private String key;
    private String keySecond;

    public OneBlock() {
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("site")
    public String getSite() {
        return site;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @JsonProperty("strategy")
    public Integer getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("second")
    public String getKeySecond() {
        return keySecond;
    }

    public void setKeySecond(String keySecond) {
        this.keySecond = keySecond;
    }
}
