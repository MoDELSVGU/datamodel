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

import java.util.Set;

public class DmUtils {

    public static String getAssociationName(DataModel dm,
            String className, String endName) {

        Set<Association> assocs = dm.getAssociations();

        for (Association assoc : assocs) {
            if (assoc.getLeftEntityName().equals(className)
                    && assoc.getRightEnd().equals(endName)) {
                return assoc.getName();
            }

            if (assoc.getRightEntityName().equals(className)
                    && assoc.getLeftEnd().equals(endName)) {
                return assoc.getName();
            }
        }

        return null;
    }

    public static String getAssociationOppClassName(DataModel dm,
            String className, String endName) {

        End end = getEnd(dm, className, endName);

        try {
            return end.getTargetClass();

        } catch (Exception e) {
//            e.printStackTrace();

            return null;
        }
    }

    public static String getOppositeAssociationName(DataModel dm,
            String className, String endName) {
        End end = getEnd(dm, className, endName);

        try {

            return end.getOpp();

        } catch (Exception e) {

//            e.printStackTrace();
            return null;
        }
    }

    public static boolean isAssociationEndOfClass(DataModel dm,
            String className, String endName) {

        try {
            End end = getEnd(dm, className, endName);

            return end.getName().equals(endName);

        } catch (Exception e) {

//            e.printStackTrace();
            return false;
        }
    }

    public static boolean isClass(DataModel dm, String className) {
        return dm.getEntities().get(className) != null;
    }

    public static boolean isPropertyOfClass(DataModel dm,
            String className, String attName) {

        try {
            Attribute att = getAttribute(dm, className, attName);

            return att.getName().equals(attName);
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }

    public static String getAttributeType(DataModel dm,
            String className, String attName) {
        Attribute att = getAttribute(dm, className, attName);

        try {
            return att.getType();

        } catch (Exception e) {

//            e.printStackTrace();
            return null;
        }
    }

    public static boolean isAssocM2M(DataModel dm, String className,
            String endName) {
        return getAssocRelationship(dm, className, endName)
                .equals("M2M");
    }

    public static boolean isAssocO2M(DataModel dm, String className,
            String endName) {
        return getAssocRelationship(dm, className, endName)
                .equals("O2M");
    }

    public static boolean isAssocM2O(DataModel dm, String className,
            String endName) {
        return isAssocO2M(dm, className, endName);
    }

    public static boolean isAssocO2O(DataModel dm, String className,
            String endName) {
        return getAssocRelationship(dm, className, endName)
                .equals("O2O");
    }

    public static boolean isEndMultMany(DataModel dm, String className,
            String endName) {
        End end = getEnd(dm, className, endName);

        try {

            return end.getMult().getValue().equals("*");
        } catch (Exception e) {

//            e.printStackTrace();
            return false;
        }
    }

    public static boolean isEndMultOne(DataModel dm, String className,
            String endName) {

        End end = getEnd(dm, className, endName);

        try {

            return end.getMult().getValue().equals("1");

        } catch (Exception e) {

//            e.printStackTrace();
            return false;
        }

    }

    private static String getAssocRelationship(DataModel dm,
            String className, String endName) {

        try {
            End end = getEnd(dm, className, endName);
            String rightMult = end.getMult().getValue();

            String targetClass = getAssociationOppClassName(dm,
                    className, endName);
            Entity entity = dm.getEntities().get(targetClass);
            String leftMult = "";

            for (End oppositeEnd : entity.getEnds()) {
                if (oppositeEnd.getTargetClass().equals(className)) {
                    leftMult = oppositeEnd.getMult().getValue();
                }
            }

            return getRelationshipType(leftMult, rightMult);

        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    private static String getRelationshipType(String leftMult,
            String rightMult) {
        boolean isLeftMultOne = leftMult.equals("1");
        boolean isRightMultOne = rightMult.equals("1");

        if (isLeftMultOne ^ isRightMultOne) {
            return "O2M";
        } else if (isLeftMultOne && isRightMultOne) {
            return "O2O";
        } else {
            return "M2M";
        }
    }

    private static End getEnd(DataModel dm, String className,
            String endName) {
        Entity entity = dm.getEntities().get(className);
        Set<End> ends = entity.getEnds();

        for (End end : ends) {
            if (end.getName().equals(endName)) {
                return end;
            }
        }

        return null;
    }

    private static Attribute getAttribute(DataModel dm,
            String className, String attName) {
        Entity entity = dm.getEntities().get(className);

        Set<Attribute> atts = entity.getAttributes();

        for (Attribute att : atts) {
            if (att.getName().equals(attName)) {
                return att;
            }
        }

        return null;
    }

}
