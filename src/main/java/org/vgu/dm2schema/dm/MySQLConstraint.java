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

package org.vgu.dm2schema.dm;

/**
 * The Class MySQLConstraint.
 */
public class MySQLConstraint extends Constraint {

    /**
     * The Constant NOT_NULL. In MySQL NOT NULL constraint allows to specify
     * that a column can not contain any NULL value. MySQL NOT NULL can be used
     * to CREATE and ALTER a table.
     */
    public static final String NOT_NULL = "NOT NULL";

    /**
     * The Constant UNIQUE. The UNIQUE constraint in MySQL does not allow to
     * insert a duplicate value in a column. The UNIQUE constraint maintains the
     * uniqueness of a column in a table. More than one UNIQUE column can be
     * used in a table.
     */
    public static final String UNIQUE = "UNIQUE";

    /**
     * The Constant PRIMARY_KEY. A PRIMARY KEY constraint for a table enforces
     * the table to accept unique data for a specific column and this constraint
     * creates a unique index for accessing the table faster.
     */
    public static final String PRIMARY_KEY = "PRIMARY KEY";

    /**
     * The Constant FOREIGN_KEY. A FOREIGN KEY in MySQL creates a link between
     * two tables by one specific column of both tables. The specified column in
     * one table must be a PRIMARY KEY and referred by the column of another
     * table known as FOREIGN KEY.
     */
    public static final String FOREIGN_KEY = "FOREIGN KEY";

    /**
     * The Constant CHECK. A CHECK constraint controls the values in the
     * associated column. The CHECK constraint determines whether the value is
     * valid or not from a logical expression.
     */
    public static final String CHECK = "CHECK (%1$s)";

    /**
     * The Constant DEFAULT. In a MySQL table, each column must contain a value
     * ( including a NULL). While inserting data into a table, if no value is
     * supplied to a column, then the column gets the value set as DEFAULT.
     */
    public static final String DEFAULT = "DEFAULT";

    public static final String AUTO_INCREMENT = "AUTO_INCREMENT";

    public MySQLConstraint(Object object) throws Exception {
        super(object);
    }

    public String getConstraint() {
        switch (super.getConstraint()) {
        case "unique":
            return UNIQUE;
        case "notnull":
            return NOT_NULL;
        default:
            return null;
        }
    }
}
