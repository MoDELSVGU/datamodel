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

@author: thian
***************************************************************************/

package org.vgu.dm2schema.dm;

import java.io.File;
import java.io.FileReader;

import org.json.simple.parser.JSONParser;

public class DmUtilsTest {

    public static void main(String[] args) throws Exception {
        File dataModelFile = new File("src/main/resources/genSQL/university_test.json");
//        File SQLschemaFile = new File("genSQL/university_test.sql");
//        String databaseName = "unidb";

        DataModel dataModel = new DataModel(new JSONParser().parse(
                new FileReader(dataModelFile.getAbsolutePath())));

        // test DmUtils
        // getAssociationName();
        String assocName = DmUtils.getAssociationName(dataModel,
                "University", "programs");
        System.out.println(assocName);

        // getAssociationOppClassName();
        String assocOppClassName = DmUtils.getAssociationEndTargetClassName(
                dataModel, "University", "programs");
        System.out.println(assocOppClassName);

        // getAssociationOppClassName();
        String oppAssocName = DmUtils.getAssociationEndSourceClassName(
                dataModel, "University", "programs");
        System.out.println(oppAssocName);

        System.out.print("getAssociationOppClassName() --> ");
        boolean isEndOfClass = DmUtils.isAssociationEndOfClass(
                dataModel, "Program", "module_periods");
        System.out.println(isEndOfClass);

        // isClass()
        boolean isClass = DmUtils.isClass( dataModel, "Error");
        System.out.println(isClass);

        System.out.print("isPropertyOfClass() --> ");
        boolean isPropertyOfClass = DmUtils.isPropertyOfClass(dataModel, "Student", "firstname");
        System.out.println(isPropertyOfClass);
    }
}
