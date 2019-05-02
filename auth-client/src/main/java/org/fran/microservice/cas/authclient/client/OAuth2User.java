package org.fran.microservice.cas.authclient.client;

import lombok.Data;

import java.util.List;

/**
 * @author fran
 * @Description
 * @Date 2019/4/30 17:43
 */
@Data
public class OAuth2User {
    OAuth2Principal principal;

    @Data
    public static class OAuth2Principal{
        String username;
        String password;
        boolean enabled;
        boolean accountNonLocked;
        boolean accountNonExpired;
        boolean credentialsNonExpired;
        List authorities;
    }
}
