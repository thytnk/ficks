ALTER TABLE documents ADD COLUMN
    original_file_name VARCHAR(200);

UPDATE documents SET original_file_name = file_name;

ALTER TABLE documents ALTER COLUMN
    original_file_name SET NOT NULL;
