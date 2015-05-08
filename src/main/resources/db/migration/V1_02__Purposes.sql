create table Purposes (
    purpose_id    int         not null primary key
  , purpose_name  varchar(20) not null
  , display_order int         not null
)
;

insert into Purposes
values
  ( 1, '提案書',     10)
, ( 2, '技術書',     20)
, ( 3, '設定書',     30)
, ( 4, 'マニュアル', 40)
, ( 5, '仕様書',     50)
, ( 6, '事例集',     60)
, ( 7, '手順書',     70)
, (99, 'その他',    990)
;

