-- DELIMITER ||
-- 
-- DROP TRIGGER IF EXISTS tg_Module_inv1_insert;
-- CREATE TRIGGER tg_Module_inv1_insert
-- BEFORE INSERT ON Module FOR EACH ROW
-- BEGIN
--     -- DECLARE
--     DECLARE oldName VARCHAR(100);
--     DECLARE done BOOLEAN;
-- 
--     DECLARE nameCursor CURSOR FOR SELECT name FROM module WHERE program = NEW.program;
--     DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = true;
-- 
--     OPEN nameCursor;
--     nameLoop: LOOP
--         FETCH nameCursor INTO oldName;
-- 
--         IF NEW.name = oldName THEN 
--             SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
--             CLOSE nameCursor;
--             LEAVE nameLoop;
--         ELSEIF done THEN
--             CLOSE nameCursor;
--             LEAVE nameLoop;
--         END IF;
--     END LOOP nameLoop;
-- 
-- END; ||
-- 
-- DELIMITER ;


DELIMITER ||
DROP TRIGGER IF EXISTS tg_Module_inv4_insert;
CREATE TRIGGER tg_Module_inv4_insert
BEFORE INSERT ON module FOR EACH ROW
BEGIN
    IF 
        EXISTS (SELECT 1 FROM module WHERE program = NEW.program AND name = NEW.name)
    THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
    END IF;
END; ||
DELIMITER ;
