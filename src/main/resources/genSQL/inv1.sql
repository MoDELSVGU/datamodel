DROP TRIGGER IF EXISTS tg_Program_inv1_insert;
delimiter //
CREATE TRIGGER tg_Program_inv1_insert BEFORE INSERT ON Program
FOR EACH ROW
BEGIN
	IF 
		NEW.doe > CURDATE()
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
	END IF;
END //
delimiter ;
DROP TRIGGER IF EXISTS tg_Program_inv1_update;
delimiter //
CREATE TRIGGER tg_Program_inv1_update BEFORE UPDATE ON Program
FOR EACH ROW
BEGIN
	IF 
		NEW.doe > CURDATE()
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';
	END IF;
END //
delimiter ;