create table links (
    link_id       serial       not null primary key
  , link_url      varchar(400) not null unique
  , link_name     varchar(400) not null
  , display_order int          not null
)
;