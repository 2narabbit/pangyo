package com.adinstar.pangyo.model.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KTokenInfo {
    private long id;
    private long expiresInMillis;
    private long appId;
}
