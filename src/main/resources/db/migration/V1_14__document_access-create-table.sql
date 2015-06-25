create table document_access (
    document_access_id serial    not null primary key
  , user_id            integer   not null references users(user_id)
  , document_id        integer   not null references documents(document_id)
  , access_date        timestamp not null default CURRENT_DATE
)
;