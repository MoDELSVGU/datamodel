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

public class InvariantFunction {
    public static final String TEMPLATE = "DROP FUNCTION IF EXISTS %1$s;\r\n"
        + "DELIMITER //\r\n" + "CREATE FUNCTION %1$s ()\r\n"
        + "RETURNS INT DETERMINISTIC\r\n" + "BEGIN\r\n"
        + "DECLARE result INT DEFAULT 0;\r\n"
        + "SELECT res INTO result FROM (%2$s) AS TEMP_result;\r\n"
        + "RETURN (result);\r\n" + "END //\r\n" + "DELIMITER ";

    private String name;
    private String sqlInvariant;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(TEMPLATE, name, sqlInvariant);
    }

    public String getSqlInvariant() {
        return sqlInvariant;
    }

    public void setSqlInvariant(String sqlInvariant) {
        this.sqlInvariant = sqlInvariant;
    }

}
