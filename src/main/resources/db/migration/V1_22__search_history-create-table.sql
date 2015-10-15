CREATE TABLE search_history (
    history_id     INTEGER     NOT NULL REFERENCES history(history_id) ON DELETE CASCADE
  , category       VARCHAR(26)
  , purpose        INTEGER     REFERENCES purposes(purpose_id)
  , area           INTEGER     REFERENCES areas(area_id)
  , result         INTEGER     REFERENCES results(result_id)
  , reason         INTEGER     REFERENCES reasons(reason_id)
  , customer       VARCHAR(200)
  , author         VARCHAR(200)
  , published_from DATE
  , published_to   DATE
  , keyword        VARCHAR
  , page           INTEGER     NOT NULL
  , count          INTEGER     NOT NULL
)
;