drop table if exists permission;
drop table if exists userinfo;
/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   ID                   bigint(18) not null,
   LEVEL                int(2) not null default 1,
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   ENABLE               char(2) not null default '1',
   MODIFY_ID            varchar(255) not null default ' ',
   CREATE_ID            varchar(255) not null default ' ',
   QUEUE                int(10) not null default 0,
   DESCRIPTION          varchar(255) not null default ' ',
   ROLE                 varchar(255) not null default ' ',
   primary key (ID)
);/*==============================================================*/
/* Table: userinfo                                              */
/*==============================================================*/
create table userinfo
(
   ID                   bigint(18) not null,
   USER_NAME            varchar(255) not null,
   PASSWORD             varchar(255) not null,
   FIRST_LOGINIP        varchar(255) not null default ' ',
   SALT                 varchar(255) not null default ' ',
   PERMISSION_ID        bigint(18) not null default -1,
   CREATE_TIME          datetime,
   MODIFY_TIME          datetime,
   ENABLE               char(2) not null default '1',
   MODIFY_ID            varchar(255) not null default ' ',
   CREATE_ID            varchar(255) not null default ' ',
   QUEUE                int(10) not null default 0,
   DESCRIPTION          varchar(255) not null default ' ',
   primary key (ID)
);alter table userinfo add constraint FK_Reference_5 foreign key (PERMISSION_ID)
      references permission (ID) on delete restrict on update restrict;