ALTER TABLE documents ADD COLUMN
    update_date TIMESTAMP;

UPDATE documents SET update_date = register_date;

ALTER TABLE documents ALTER COLUMN
    update_date SET NOT NULL;
