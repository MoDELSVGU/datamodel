DROP TRIGGER IF EXISTS tg_Enrollment_inv1_insert;
delimiter //
CREATE TRIGGER tg_Enrollment_inv1_insert BEFORE INSERT ON Enrollment
FOR EACH ROW
BEGIN
	IF 
		NEW.ends IS NOT NULL AND NEW.ends <= NEW.starts
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
	END IF;
END //
delimiter ;
DROP TRIGGER IF EXISTS tg_Enrollment_inv1_update;
delimiter //
CREATE TRIGGER tg_Enrollment_inv1_update BEFORE UPDATE ON Enrollment
FOR EACH ROW
BEGIN
	IF 
		NEW.ends IS NOT NULL AND NEW.ends <= NEW.starts
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
	END IF;
END //
delimiter ;