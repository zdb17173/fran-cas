package org.fran.microservice.cas.authapp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Collection;

/**
 * Created by fran on 2018/2/1.
 */
public class FreemarkerUtil {

    public Authentication getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof OAuth2Authentication){
            OAuth2Authentication oAuth2User = (OAuth2Authentication) authentication;
            Authentication userAuthentication = oAuth2User.getUserAuthentication();
            if(userAuthentication  instanceof UsernamePasswordAuthenticationToken ){
                UsernamePasswordAuthenticationToken pp = (UsernamePasswordAuthenticationToken) userAuthentication;
                return pp;
            }
        }

        return authentication;
    }

    public boolean hasRole(String role){
        if(role == null || "".equals(role))
            return false;
        else{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication!= null){
                Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
                if(roles == null || roles.size() == 0)
                    return false;
                else{
                    for(GrantedAuthority r : roles){
                        if(r.getAuthority().equals(role))
                            return true;
                    }
                }
                return false;
            }else{
                return false;
            }
        }
    }
}
