INSERT INTO history(history_id, user_id, access_date, action)
SELECT document_access_id as history_id
     , user_id
     , access_date
     , 'DOWNLOAD'         as action
  FROM document_access
;

INSERT INTO download_history(history_id, document_id)
SELECT document_access_id as history_id
     , document_id
  FROM document_access
;