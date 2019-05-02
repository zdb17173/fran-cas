package org.fran.microservice.cas.authserverjdbc.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.fran.microservice.cas.authserverjdbc.dao.po.AuthUser;


@Mapper
public interface AuthUserMapper {

    AuthUser selectUserPermission(String userName);

    AuthUser selectUserPermissionById(Long userId);
}