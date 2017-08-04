package com.erminesoft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestSendToUrlDto {

    @JsonProperty("siteId")
    private Long siteId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("time")
    private String time;

    @JsonProperty("frequency")
    private String frequency;

    @JsonProperty("format")
    private String format;
}
