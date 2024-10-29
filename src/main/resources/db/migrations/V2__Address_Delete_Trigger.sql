CREATE OR REPLACE FUNCTION delete_address_if_unused()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM companies
                   WHERE address_id = OLD.address_id) AND NOT EXISTS (SELECT 1
                                                                      FROM candidates
                                                                      WHERE address_id = OLD.address_id) AND
       NOT EXISTS (SELECT 1
                   FROM job_openings
                   WHERE address_id = OLD.address_id) THEN
        DELETE FROM addresses WHERE id = OLD.address_id;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER delete_address_after_company
    AFTER DELETE
    ON companies
    FOR EACH ROW
EXECUTE FUNCTION delete_address_if_unused();

CREATE TRIGGER delete_address_after_candidate
    AFTER DELETE
    ON candidates
    FOR EACH ROW
EXECUTE FUNCTION delete_address_if_unused();

CREATE TRIGGER delete_address_after_job_opening
    AFTER DELETE
    ON job_openings
    FOR EACH ROW
EXECUTE FUNCTION delete_address_if_unused();