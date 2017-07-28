package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleOneForBlock {

    private Long id;

    private boolean isDefault;

    private Integer strategy;

    private KeyDto key;

    private String timePattern;

    private boolean isEnable;

    public RuleOneForBlock() {
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("default")
    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @JsonProperty("strategy")
    public Integer getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
    }

    @JsonProperty("key")
    public KeyDto getKey() {
        return key;
    }

    public void setKey(KeyDto key) {
        this.key = key;
    }

    @JsonProperty("pattern")
    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    @JsonProperty("enable")
    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Override
    public String toString() {
        return "RuleOneForBlock{" +
                "isDefault=" + isDefault +
                ", strategy=" + strategy +
                ", key=" + key +
                ", timePattern='" + timePattern + '\'' +
                ", isEnable=" + isEnable +
                '}';
    }
}
