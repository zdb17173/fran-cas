/*==============================================================*/
/* Table: auth_permission                                       */
/*==============================================================*/
create table auth_permission
(
   id                   bigint not null auto_increment,
   name                 varchar(64) not null,
   parent_id            bigint,
   parent_name          varchar(64),
   show_name            varchar(255) not null,
   path                 varchar(255),
   type                 int comment '0：common 1:ar 2:es 3:fr 4:py',
   create_time          datetime not null default CURRENT_TIMESTAMP,
   display              int not null default 0 comment 'hide 0 display 1',
   status               int not null default 1 comment 'deleted:0  normal:1',
   is_permission        int not null default 1 comment 'path:0  permission:1',
   sort_order           int default 0,
   primary key (id)
);

alter table auth_permission comment '权限表';

/*==============================================================*/
/* Table: auth_role                                             */
/*==============================================================*/
create table auth_role
(
   id                   bigint not null auto_increment,
   name                 varchar(64) not null,
   create_time          datetime default CURRENT_TIMESTAMP,
   status               int not null comment 'deleted:0 normal:1',
   primary key (id)
);

alter table auth_role comment '角色表';

/*==============================================================*/
/* Table: auth_role_permission                                  */
/*==============================================================*/
create table auth_role_permission
(
   id                   bigint not null auto_increment,
   role_id              bigint not null,
   permission_id        bigint not null,
   primary key (id)
);

alter table auth_role_permission comment '角色权限关系表';

/*==============================================================*/
/* Table: auth_user                                             */
/*==============================================================*/
create table auth_user
(
   id                   bigint not null auto_increment,
   name                 varchar(64) not null,
   account              varchar(64),
   foreign_name         varchar(64),
   email                varchar(64),
   phone                varchar(64),
   password             varchar(64) not null,
   status               int not null default 1 comment 'deleted:0  normal:1 ',
   create_time          datetime not null default CURRENT_TIMESTAMP,
   primary key (id)
);

alter table auth_user comment '用户表';

/*==============================================================*/
/* Table: auth_user_role                                        */
/*==============================================================*/
create table auth_user_role
(
   id                   bigint not null auto_increment,
   user_id              bigint not null,
   role_id              bigint not null,
   primary key (id)
);

alter table auth_user_role comment '用户角色关系表';