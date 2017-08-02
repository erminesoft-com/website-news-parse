package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RulesOneBlock {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("default")
    private boolean isDefault;

    @JsonProperty("strategy")
    private Integer strategy;

    @JsonProperty("key")
    private KeyDto key;

    @JsonProperty("pattern")
    private String timePattern;

    @JsonProperty("enable")
    private boolean isEnable;
}
