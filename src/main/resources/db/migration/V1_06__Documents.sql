create table Documents (
    document_id     serial       not null primary key
  , code            char(12)              unique
  , large_code      int          not null
  , small_code      serial       not null
  , revision        int          not null
  , category        char(1)      not null references categories(code)
  , purpose         int          not null references purposes(purpose_id)
  , area            int          not null references areas(area_id)
  , result          int          not null references results(result_id)
  , reason          int          not null references reasons(reason_id)
  , dept_name       varchar(200)
  , emp_number      varchar(20)
  , author_name     varchar(200)
  , publish_date    date         not null
  , description     varchar
  , customer_name   varchar(200)
  , disabled        boolean      not null default false
  , register_date   date         not null
  , file_name       varchar(200) not null
  , print_direction varchar(10)  not null
  , print_date      date
  , index_date      date
)
;