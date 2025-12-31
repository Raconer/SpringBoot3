package com.spring.oauth.social.instagram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstagramUser {
    private String id;
    private String username;

    @JsonProperty("account_type")
    private String accountType;
}
