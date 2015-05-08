create table Reasons (
    reason_id     int         not null primary key
  , reason_name   varchar(20) not null
  , display_order int         not null
)
;

insert into Reasons
values
  (1, '価格',         10)
, (2, '顧客要件',     20)
, (3, 'プレゼン力',   30)
, (4, '機能・性能',   40)
, (5, 'サービス品質', 50)
, (6, '差別化',       60)
, (7, '政治的判断',   70)
, (8, '総合的判断',   80)
;
