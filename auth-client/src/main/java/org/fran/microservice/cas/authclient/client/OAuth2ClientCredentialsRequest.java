package org.fran.microservice.cas.authclient.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuth2ClientCredentialsRequest{
    String grant_type = "client_credentials";
    String client_id;
    String client_secret;
    String code;
    String scope;
    String redirect_uri;
    String state;

    public OAuth2ClientCredentialsRequest(String client_id, String client_secret, String code, String scope, String redirect_uri, String state) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
        this.scope = scope;
        this.redirect_uri = redirect_uri;
        this.state = state;
    }
}