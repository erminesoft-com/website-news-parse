package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class ArticleDto {

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "image")
    private String imageUrl;

    @JsonProperty(value = "date")
    private String date;

    @JsonProperty(value = "link")
    private String link;
}
