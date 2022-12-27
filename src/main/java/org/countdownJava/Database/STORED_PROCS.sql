CREATE OR REPLACE PROCEDURE resetCP()
    LANGUAGE SQL
AS $$
    DROP TABLE IF EXISTS combinations_permutations;
    CREATE TABLE combinations_permutations (
        combination INTEGER NOT NULL
    );
$$;


CREATE OR REPLACE PROCEDURE addColumnsCP(column_name VARCHAR(10))
    LANGUAGE plpgsql
AS $$
DECLARE
    i INTEGER;
BEGIN
    FOR i IN 1..720 LOOP
        EXECUTE 'ALTER TABLE combinations_permutations ADD COLUMN ' || column_name || i || ' VARCHAR(10) UNIQUE';
    END LOOP;
END;
$$;


select * from combinations_permutations

