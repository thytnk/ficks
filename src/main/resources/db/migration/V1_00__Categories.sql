create table Categories (
    category_id   serial      not null primary key
  , code          char(1)     not null unique
  , name          varchar(20) not null
  , display_order int         not null
)
;

insert into Categories(code, name, display_order)
values
  ('A', '提案資料', 10)
, ('B', '技術資料', 20)
, ('C', '設定資料', 30)
, ('D', '手順資料', 40)
, ('E', '業務関連', 50)
;

