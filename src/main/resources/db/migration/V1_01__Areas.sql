create table Areas (
    area_id       int         not null primary key
  , area_name     varchar(20) not null
  , display_order int         not null
)
;


insert into Areas
values
  ( 1, '小学校', 10)
, ( 2, '中学校', 20)
, ( 3, '高校',   30)
, ( 4, '大学',   40)
, ( 5, '公共',   50)
, ( 6, '金融',   60)
, ( 7, '産業',   70)
, (99, 'その他', 990)
;
