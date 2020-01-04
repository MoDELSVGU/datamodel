/**************************************************************************
Copyright 2019 Vietnamese-German-University

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author: ngpbh
***************************************************************************/


package org.vgu.dm2schema.sql;

public class SQLSchemaTemplate {
    public static final String CREATE_DATABASE = 
        "CREATE DATABASE %1$s \r\n"
        + " DEFAULT CHARACTER SET utf8 \r\n" 
        + " DEFAULT COLLATE utf8_general_ci";
    public static final String CREATE_FUNCTION =
        "DELIMITER %1$s\r\n" 
            + "CREATE FUNCTION %2$s ()\r\n"
            + "RETURNS INT DETERMINISTIC\r\n" 
            + "%3$s\r\n" 
            + "DELIMITER ";
    public static final String CREATE_INVARIANT_FUNCTION = 
        "DELIMITER %1$s\r\n" 
        + "CREATE FUNCTION %2$s ()\r\n"
        + "RETURNS INT DETERMINISTIC\r\n" 
        + "BEGIN\r\n"
        + "DECLARE result INT DEFAULT 0;\r\n"
        + "SELECT res INTO result FROM (%3$s) AS TEMP_result;\r\n"
        + "RETURN (result);\r\n" 
        + "END %1$s\r\n" 
        + "DELIMITER ";
    public static final String CREATE_TRIGGER =
        "DELIMITER %1$s\r\n" + 
        "CREATE TRIGGER %2$s %3$s %4$s ON %5$s\r\n" + 
        "FOR EACH ROW\r\n" + 
        "%6$s\r\n" + 
        "DELIMITER ";
    public static final String CREATE_INVARIANT_TRIGGER =
        "DELIMITER %1$s\r\n" + 
        "CREATE TRIGGER %2$s %3$s %4$s ON %5$s\r\n" + 
        "FOR EACH ROW\r\n" + 
        "BEGIN\r\n" + 
        "DECLARE _result INT DEFAULT 0;\r\n" + 
        "SELECT %6$s INTO _result;\r\n" + 
        "IF (_result = 0)\r\n" + 
        "THEN\r\n" + 
        "SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invariant violated';\r\n" + 
        "END IF;\r\n" + 
        "END %1$s\r\n" +
        "DELIMITER ";
}
