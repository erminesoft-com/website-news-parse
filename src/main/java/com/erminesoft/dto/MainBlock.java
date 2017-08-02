package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainBlock {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("site")
    private String site;

    @JsonProperty("name")
    private String name;

    @JsonProperty("strategy")
    private Integer strategy;

    @JsonProperty("key")
    private String key;

    @JsonProperty("second")
    private String keySecond;
}
