package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KeyDto {

    private Long id;
    private String one;
    private String two;
    private String linkPrefix;
    private String defaultLink;

    public KeyDto() {
    }

    public KeyDto(String one, String two, String linkPrefix, String defaultLink) {
        this.one = one;
        this.two = two;
        this.linkPrefix = linkPrefix;
        this.defaultLink = defaultLink;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("one")
    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    @JsonProperty("two")
    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    @JsonProperty("prefix")
    public String getLinkPrefix() {
        return linkPrefix;
    }

    public void setLinkPrefix(String linkPrefix) {
        this.linkPrefix = linkPrefix;
    }

    @JsonProperty("link_default")
    public String getDefaultLink() {
        return defaultLink;
    }

    public void setDefaultLink(String defaultLink) {
        this.defaultLink = defaultLink;
    }

    @Override
    public String toString() {
        return "KeyDto{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                '}';
    }
}
