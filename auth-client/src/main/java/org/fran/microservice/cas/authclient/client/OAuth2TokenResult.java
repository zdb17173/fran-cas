package org.fran.microservice.cas.authclient.client;

import lombok.Data;

@Data
public class OAuth2TokenResult{
    String access_token;
    String token_type;
    String refresh_token;
    int expires_in;
    String scope;
}