package org.fran.microservice.cas.authclient.client;

import lombok.Data;

/**
 * @author fran
 * @Description
 * @Date 2019/4/30 17:10
 */
@Data
public class OAuth2PasswordRequest {
    String grant_type = "password";
    String username;
    String password;
    String scope = "all";

    public OAuth2PasswordRequest(String username, String password){
        this.username = username;
        this.password = password;
    }
}
