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

@author: ngpbh, thian
***************************************************************************/

package org.vgu.dm2schema.dm;

import org.json.simple.JSONObject;

public class End {
    private String association;
    private String name;
    private String targetClazz;
    private String currentClazz;
    private String opp;
    private Multiplicity mult;
    
    public End() {};

    public End(Object object) throws Exception {
        if (!(object instanceof JSONObject))
            throw new Exception();
        JSONObject end = (JSONObject) object;
        this.association = (String) end.get("association");
        this.name = (String) end.get("name");
        this.targetClazz = (String) end.get("target");
        this.opp = (String) end.get("opp");
        this.mult = Multiplicity.getEnum((String) end.get("mult"));
    }

    public String getCurrentClass() {
        return currentClazz;
    }

    public void setCurrentClass(String currentClazz) {
        this.currentClazz = currentClazz;
    }

    public String getName() {
        return name;
    }

    public String getTargetClass() {
        return targetClazz;
    }

    public String getOpp() {
        return opp;
    }

    public Multiplicity getMult() {
        return mult;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        End other = (End) obj;
        if (name == null || opp == null || targetClazz == null
            || currentClazz == null)
            return false;
        if (other.getName() == null || other.getOpp() == null
            || other.getTargetClass() == null || other.getCurrentClass() == null)
            return false;
        if (name.equals(other.getName()) && opp.equals(other.getOpp())
            && targetClazz.equals(other.getTargetClass())
            && currentClazz.equals(other.getCurrentClass()))
            if (mult != other.mult)
                return false;
            else return true;
        if (name.equals(other.getOpp()) && opp.equals(other.getName())
            && targetClazz.equals(other.getCurrentClass())
            && currentClazz.equals(other.getTargetClass()))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "name : " + this.name + "\n" 
                + "targetClass : " + this.targetClazz + "\n"
                + "currentClass : " + this.currentClazz + "\n"
                + "opp : " + this.opp + "\n"
                + "mult : " + this.mult + ";\n";
    }

    public void setTargetClass(String targetClazz) {
        this.targetClazz = targetClazz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpp(String opp) {
        this.opp = opp;
    }

    public void setMult(Multiplicity mult) {
        this.mult = mult;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }
    
}
