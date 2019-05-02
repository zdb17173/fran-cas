package org.fran.microservice.cas.authclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fran
 * @Description
 * @Date 2019/4/25 9:39
 */

@SpringBootApplication
public class ClientApplication {
    //http://127.0.0.1:8081/auth/oauth/authorize?client_id=SampleClientId&redirect_uri=http://127.0.0.1:8082/ui/login&response_type=code&state=isvQu4

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
