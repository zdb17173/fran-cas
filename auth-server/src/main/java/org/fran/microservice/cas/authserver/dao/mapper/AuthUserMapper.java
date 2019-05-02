package org.fran.microservice.cas.authserver.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fran.microservice.cas.authserver.dao.po.AuthUser;


@Mapper
public interface AuthUserMapper {

    AuthUser selectUserPermission(String userName);

    AuthUser selectUserPermissionById(Long userId);
}