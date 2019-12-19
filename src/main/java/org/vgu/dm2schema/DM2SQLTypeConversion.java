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

package org.vgu.dm2schema;

import java.util.Arrays;
import java.util.List;

public class DM2SQLTypeConversion {

    public static String convert(String normalType) {
        switch (normalType) {
        case "String":
            return "VARCHAR";
        case "Boolean":
        case "Integer":
            return "INT";
        case "Date":
            return "DATE";
        case "Datetime":
            return "DATETIME";
        case "Time":
            return "TIME";
        case "Timestamp":
            return "TIMESTAMP";
        default:
            throw new NullPointerException("Unsuported Data Type!!");
        }
    }

    public static List<String> addArgument(String normalType) {
        switch (normalType) {
        case "String":
            return Arrays.asList("100");
        case "Boolean":
            return Arrays.asList("1");
        case "Integer":
            return Arrays.asList("11");
        case "Date":
            return null;
        case "Datetime":
            return null;
        case "Time":
            return null;
        case "Timestamp":
            return null;
        default:
            throw new NullPointerException("Unsuported Data Type!!");
        }
    }

}
