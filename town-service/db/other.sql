drop table if exists user_extend;

/*==============================================================*/
/* Table: user_extend                                           */
/*==============================================================*/
create table user_extend
(
    ID                   bigint(18) not null,
    CREATE_TIME          datetime default CURRENT_TIMESTAMP,
    MODIFY_TIME          datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ENABLE               char(2) not null default '1',
    MODIFY_ID            varchar(255) not null default ' ',
    CREATE_ID            varchar(255) not null default ' ',
    QUEUE                int(10) not null default 0,
    DESCRIPTION          varchar(255) not null default ' ',
    USERID               bigint(18) not null,
    NAME                 varchar(255) not null default ' ',
    PHONE                varchar(255) not null default ' ',
    WX_CHAT              varchar(255) not null default ' ',
    SHOP_NAME            varchar(255) not null default ' ',
    EMAIL                varchar(255) not null default ' ',
    EXPIRE_TIME          datetime,
    primary key (ID),
    unique key AK_unique (SHOP_NAME)
);
drop table if exists product_order;

/*==============================================================*/
/* Table: product_order                                         */
/*==============================================================*/
create table product_order
(
    ID                   bigint(18) not null,
    CREATE_TIME          datetime not null default CURRENT_TIMESTAMP,
    MODIFY_TIME          datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ENABLE               char(2) not null default '1',
    MODIFY_ID            varchar(255) not null default ' ',
    CREATE_ID            varchar(255) not null default ' ',
    QUEUE                int(10) not null default 0,
    PRODUCT_ID           bigint(18) not null,
    PHONE                varchar(255) not null default ' ',
    NAME                 varchar(255) not null default ' ',
    LOCATION             varchar(255) not null default ' ',
    DESCRIPTION          varchar(255) not null default ' ',
    primary key (ID)
);
drop table if exists products;

/*==============================================================*/
/* Table: products                                              */
/*==============================================================*/
create table products
(
    ID                   bigint(18) not null,
    NAME                 varchar(255) not null default ' ',
    USERID               bigint(18) not null,
    CATEGORY_ID          bigint(18) not null default 0,
    VIEW_COUNT           int(10) not null default 0,
    PRICE                decimal(16,4) not null default 0,
    DESCRIPTION          varchar(255) not null default ' ',
    IMG3                 varchar(500) not null default ' ',
    IMG2                 varchar(500) not null default ' ',
    IMG1                 varchar(500) not null default ' ',
    CREATE_TIME          datetime default CURRENT_TIMESTAMP,
    MODIFY_TIME          datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ENABLE               char(2) not null default '1',
    MODIFY_ID            varchar(255) not null default ' ',
    CREATE_ID            varchar(255) not null default ' ',
    QUEUE                int(10) not null default 0,
    primary key (ID)
);
drop table if exists product_data;

/*==============================================================*/
/* Table: product_data                                          */
/*==============================================================*/
create table product_data
(
    ID                   bigint(18) not null,
    DESCRIPTION          varchar(255) not null default ' ',
    CREATE_TIME          datetime default CURRENT_TIMESTAMP,
    MODIFY_TIME          datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ENABLE               char(2) not null default '1',
    MODIFY_ID            varchar(255) not null default ' ',
    CREATE_ID            varchar(255) not null default ' ',
    QUEUE                int(10) not null default 0,
    PRODUCT_ID           bigint(18) not null,
    IP                   varchar(255) not null default ' ',
    DEVICE               varchar(255) not null default ' ',
    LOCATION             varchar(255) not null default ' ',
    OSNAME               varchar(255) not null default ' ',
    primary key (ID)
);
drop table if exists category;

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
create table category
(
    ID                   bigint(18) not null,
    USERID               bigint(18) not null,
    NAME                 varchar(255) not null default ' ',
    QUEUE                int(10) not null default 0,
    CREATE_TIME          datetime default CURRENT_TIMESTAMP,
    MODIFY_TIME          datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ENABLE               char(2) not null default '1',
    MODIFY_ID            varchar(255) not null default ' ',
    CREATE_ID            varchar(255) not null default ' ',
    DESCRIPTION          varchar(255) not null default ' ',
    primary key (ID)
);
