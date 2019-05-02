package org.fran.microservice.cas.authserver.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * @author fran
 * @Description
 * @Date 2019/4/23 17:35
 */
public class MD5PasswordEncoder extends BCryptPasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return MD5Util.encode(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        return Objects.equals(MD5Util.encode(charSequence.toString()), encodedPassword);
    }
}
