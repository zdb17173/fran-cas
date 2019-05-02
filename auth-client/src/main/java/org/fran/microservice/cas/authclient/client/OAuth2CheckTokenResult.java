package org.fran.microservice.cas.authclient.client;

import lombok.Data;

import java.util.List;

/**
 * @author fran
 * @Description
 * @Date 2019/4/30 18:20
 */
@Data
public class OAuth2CheckTokenResult {
    boolean active;
    long exp;
    String user_name;
    String client_id;
    List<String> authorities;
    List<String> scope;
}
