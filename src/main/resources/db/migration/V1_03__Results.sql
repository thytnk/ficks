create table Results (
    result_id     int         not null primary key
  , name          varchar(20) not null
  , display_order int         not null
)
;


insert into Results
values
  ( 1, '成功', 10)
, ( 0, '失敗', 20)
, ( 9, 'なし', 90)
;
