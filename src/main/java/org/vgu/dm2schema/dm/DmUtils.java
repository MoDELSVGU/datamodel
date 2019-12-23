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

import java.util.Optional;
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

    public static String getAssociationEndTargetClassName(DataModel dm,
            String className, String endName) {
        End end = getEnd(dm, className, endName);
        return Optional.ofNullable(end).map(End::getTargetClass).orElse(null);
    }

    public static String getAssociationEndSourceClassName(DataModel dm,
            String className, String endName) {
        End end = getEnd(dm, className, endName);
        return Optional.ofNullable(end).map(End::getOpp).orElse(null);
    }

    public static boolean isAssociationEndOfClass(DataModel dm,
            String className, String endName) {
        End end = getEnd(dm, className, endName);
        return Optional.ofNullable(end).map(End::getName).map(v -> v.equals(endName)).orElse(false);
    }

    public static boolean isClass(DataModel dm, String className) {
        return dm.getEntities().containsKey(className);
    }

    public static boolean isPropertyOfClass(DataModel dm,
            String className, String attName) {
        Attribute att = getAttribute(dm, className, attName);
        return Optional.ofNullable(att).map(Attribute::getName).map(v -> v.equals(attName)).orElse(false);
    }

    public static String getAttributeType(DataModel dm,
            String className, String attName) {
        Attribute att = getAttribute(dm, className, attName);
        return Optional.ofNullable(att).map(Attribute::getType).orElse(null);
    }

    public static boolean isAssocM2M(DataModel dm, String className,
            String endName) {
        return getAssocRelationship(dm, className, endName)
                == AssociationType.MANY_TO_MANY;
    }

    public static boolean isAssocM2O(DataModel dm, String className,
            String endName) {
        return getAssocRelationship(dm, className, endName)
                == AssociationType.MANY_TO_ONE;
    }

    public static boolean isAssocO2O(DataModel dm, String className,
            String endName) {
        return getAssocRelationship(dm, className, endName)
                == AssociationType.ONE_TO_ONE;
    }

    public static boolean isEndMultMany(DataModel dm, String className,
            String endName) {
        End end = getEnd(dm, className, endName);
        return Optional.ofNullable(end).map(End::getMult).map(v -> v == Multiplicity.MANY).orElse(false);
    }

    public static boolean isEndMultOne(DataModel dm, String className,
            String endName) {
        End end = getEnd(dm, className, endName);
        return Optional.ofNullable(end).map(End::getMult).map(v -> v == Multiplicity.ONE).orElse(false);
    }

    private static AssociationType getAssocRelationship(DataModel dm,
            String className, String endName) {

        try {
            End end = getEnd(dm, className, endName);
            Multiplicity rightMult = end.getMult();

            String targetClass = getAssociationEndTargetClassName(dm,
                    className, endName);
            Entity entity = dm.getEntities().get(targetClass);
            Multiplicity leftMult = null;

            for (End oppositeEnd : entity.getEnds()) {
                if (oppositeEnd.getTargetClass().equals(className)) {
                    leftMult = oppositeEnd.getMult();
                    break;
                }
            }

            return getRelationshipType(leftMult, rightMult);

        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    private static AssociationType getRelationshipType(Multiplicity leftMult,
            Multiplicity rightMult) {
        boolean isLeftMultOne = leftMult == Multiplicity.ONE;
        boolean isRightMultOne = rightMult == Multiplicity.ONE;

        if (isLeftMultOne ^ isRightMultOne)
            return AssociationType.MANY_TO_ONE;
        if (isLeftMultOne && isRightMultOne)
            return AssociationType.ONE_TO_ONE;
        return AssociationType.MANY_TO_MANY;
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

    public static boolean isSuperClassOf(DataModel dm, String typeToCheck,
        String referredType) {
        // TODO Will be consider later when we have generalization
        return false;
    }

}
