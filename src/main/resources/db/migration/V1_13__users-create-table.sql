create table Users (
    user_id         serial       not null primary key
  , emp_number      char(6)      not null unique
  , emp_name        varchar(100) not null
  , role            varchar(20)  not null default 'USER'
  , disabled        boolean      not null default false
)
;