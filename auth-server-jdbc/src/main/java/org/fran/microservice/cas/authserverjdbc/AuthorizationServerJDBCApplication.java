package org.fran.microservice.cas.authserverjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author fran
 * @Description
 * @Date 2019/5/1 18:32
 */
@SpringBootApplication
@EnableResourceServer
public class AuthorizationServerJDBCApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerJDBCApplication.class, args);
    }

}
