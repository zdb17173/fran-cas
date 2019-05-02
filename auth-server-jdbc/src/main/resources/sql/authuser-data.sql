
/*初始化三个权限，editor、copyEditor、chiefEditor*/
insert into `auth_permission` (`id`, `name`, `parent_id`, `parent_name`, `show_name`, `path`, `type`, `create_time`, `display`, `status`, `is_permission`, `sort_order`) values('1','editor',NULL,NULL,'editor',NULL,NULL,'CURRENT_TIMESTAMP','0','1','1','0');
insert into `auth_permission` (`id`, `name`, `parent_id`, `parent_name`, `show_name`, `path`, `type`, `create_time`, `display`, `status`, `is_permission`, `sort_order`) values('2','copyEditor',NULL,NULL,'copyEditor',NULL,NULL,'CURRENT_TIMESTAMP','0','1','1','0');
insert into `auth_permission` (`id`, `name`, `parent_id`, `parent_name`, `show_name`, `path`, `type`, `create_time`, `display`, `status`, `is_permission`, `sort_order`) values('3','chiefEditor',NULL,NULL,'chiefEditor',NULL,NULL,'CURRENT_TIMESTAMP','0','1','1','0');

/*初始化一个角色，editor*/
insert into `auth_role` (`id`, `name`, `create_time`, `status`) values('1','editor',NULL,'1');

/*为角色初始化三个权限*/
insert into `auth_role_permission` (`id`, `role_id`, `permission_id`) values('1','1','1');
insert into `auth_role_permission` (`id`, `role_id`, `permission_id`) values('2','1','2');
insert into `auth_role_permission` (`id`, `role_id`, `permission_id`) values('3','1','3');

/*初始化一个用户*/
insert into `auth_user` (`id`, `name`, `account`, `foreign_name`, `email`, `phone`, `password`, `status`, `create_time`) values('1','ed_name','ed','ed_en_name',NULL,NULL,'c4ca4238a0b923820dcc509a6f75849b','1','2019-02-20 11:24:44');

/*初始化用户角色*/
insert into `auth_user_role` (`id`, `user_id`, `role_id`) values('1','1','1');