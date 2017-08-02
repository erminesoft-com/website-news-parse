package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class KeyDto {

    @NonNull
    @JsonProperty("id")
    private Long id;

    @NonNull
    @JsonProperty("one")
    private String one;

    @NonNull
    @JsonProperty("two")
    private String two;

    @NonNull
    @JsonProperty("prefix")
    private String linkPrefix;

    @NonNull
    @JsonProperty("link_default")
    private String defaultLink;

    public KeyDto(String one, String two, String linkPrefix, String defaultLink) {
        this.one = one;
        this.two = two;
        this.linkPrefix = linkPrefix;
        this.defaultLink = defaultLink;
    }
}
