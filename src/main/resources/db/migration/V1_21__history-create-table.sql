CREATE TABLE history (
    history_id     SERIAL      NOT NULL PRIMARY KEY
  , user_id        INTEGER     NOT NULL REFERENCES users(user_id)
  , access_date    TIMESTAMP   NOT NULL DEFAULT CURRENT_DATE
  , action         VARCHAR(10) NOT NULL
)
;