DROP TRIGGER IF EXISTS tg_Enrollment_inv2_insert;
delimiter //
CREATE TRIGGER tg_Enrollment_inv2_insert BEFORE INSERT ON Enrollment
FOR EACH ROW
BEGIN
	IF 
		EXISTS (SELECT 1 FROM Program WHERE Program_id = NEW.program AND doe >= NEW.starts)
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
	END IF;
END //
delimiter ;
DROP TRIGGER IF EXISTS tg_Enrollment_inv2_update;
delimiter //
CREATE TRIGGER tg_Enrollment_inv2_update BEFORE UPDATE ON Enrollment
FOR EACH ROW
BEGIN
	IF 
		EXISTS (SELECT 1 FROM Program WHERE Program_id = NEW.program AND doe >= NEW.starts)
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
	END IF;
END //
delimiter ;
DROP TRIGGER IF EXISTS tg_Enrollment_inv2_update_Program;
delimiter //
CREATE TRIGGER tg_Enrollment_inv2_update_Program BEFORE UPDATE ON Program
FOR EACH ROW
BEGIN
	IF 
		EXISTS (SELECT 1 FROM Enrollment WHERE program = OLD.Program_id AND NEW.doe >= starts)
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
	END IF;
END //
delimiter ;
