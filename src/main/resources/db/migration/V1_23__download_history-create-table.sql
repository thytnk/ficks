CREATE TABLE download_history (
    history_id     INTEGER     NOT NULL REFERENCES history(history_id) ON DELETE CASCADE
  , document_id    INTEGER     NOT NULL REFERENCES documents(document_id)
  , referrer       VARCHAR(20)
  , referrer_page  INTEGER
  , referrer_index INTEGER
)
;