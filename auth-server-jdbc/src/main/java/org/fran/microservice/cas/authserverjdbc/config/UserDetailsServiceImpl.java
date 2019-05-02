package org.fran.microservice.cas.authserverjdbc.config;

import org.fran.microservice.cas.authserverjdbc.dao.mapper.AuthUserMapper;
import org.fran.microservice.cas.authserverjdbc.dao.po.AuthPermission;
import org.fran.microservice.cas.authserverjdbc.dao.po.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author fran
 * @Description
 * @Date 2019/4/23 17:05
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    AuthUserMapper authUserMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AuthUser user = authUserMapper.selectUserPermission(userName);
        if(user!= null){
            List<GrantedAuthority> authorities = new ArrayList<>();
            if(user.getAuthPermissionList()!= null && user.getAuthPermissionList().size() > 0){
                for(AuthPermission perm : user.getAuthPermissionList()){
                    if (perm.getIsPermission() == 1){
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + perm.getName()));
                    }
                }
                authorities.add(new SimpleGrantedAuthority("ROLE_common"));
            }
            CustomUser ed = new CustomUser(
                    user.getId().toString(),
                    user.getAccount(),
                    user.getPassword(),//user pwd
                    authorities);
            return ed;
        }else{
            throw new UsernameNotFoundException(userName);
        }
    }

    public static class CustomUser implements UserDetails{
        String uid;
        String userName;
        String passWord;
        List<GrantedAuthority> grantedAuthority;

        public CustomUser(
                String uid,
                String userName,
                String passWord,
                List<GrantedAuthority> grantedAuthority
        ){
            this.uid = uid;
            this.userName = userName;
            this.passWord = passWord;
            this.grantedAuthority = grantedAuthority;
        }

        @Override
        public String toString(){
            return "uid["+ uid + "] name["+ userName +"] role["+ grantedAuthority +"]";
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return grantedAuthority;
        }

        @Override
        public String getPassword() {
            return passWord;
        }

        @Override
        public String getUsername() {
            return userName;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
