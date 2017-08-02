package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IncomeListModelParser {

    @JsonProperty(value = "block")
    private MainBlock block;

    @JsonProperty(value = "title")
    private RulesOneBlock title;

    @JsonProperty(value = "link")
    private RulesOneBlock link;

    @JsonProperty(value = "image")
    private RulesOneBlock image;

    @JsonProperty(value = "desc")
    private RulesOneBlock desc;

    @JsonProperty(value = "time")
    private RulesOneBlock time;
}
