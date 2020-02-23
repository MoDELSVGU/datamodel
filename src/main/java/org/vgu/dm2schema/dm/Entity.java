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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Entity {
    private String clazz;
    private Set<Attribute> attributes;
    private Set<End> ends;
    private boolean isUserClass;

    public Entity() {
        attributes = new HashSet<Attribute>();
        this.ends = new HashSet<End>();
    };

    public Entity(Object object) throws Exception {
        if (!(object instanceof JSONObject))
            throw new Exception();
        attributes = new HashSet<Attribute>();
        JSONObject entity = (JSONObject) object;
        this.clazz = (String) entity.get("class");
        @SuppressWarnings("unchecked")
        List<JSONObject> attributes = (JSONArray) entity.get("attributes");
        if (Objects.nonNull(attributes)) {
            for (JSONObject attribute : attributes) {
                this.attributes.add(new Attribute(attribute));
            }
        }
        this.ends = new HashSet<End>();
        @SuppressWarnings("unchecked")
        List<JSONObject> ends = (JSONArray) entity.get("ends");
        if (Objects.nonNull(ends)) {
            for (JSONObject obj : ends) {
                End end = new End(obj);
                end.setCurrentClass(this.clazz);
                this.ends.add(end);
            }
        }
        this.isUserClass = entity.containsKey("isUserClass")
            && ((String) entity.get("isUserClass")).equalsIgnoreCase("true");
    }

    public String getName() {
        return clazz;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Set<End> getEnds() {
        return ends;
    }

    @Override
    public String toString() {
        return "Class : " + clazz + "\n";
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setEnds(Set<End> ends) {
        this.ends = ends;
    }

    public boolean isUserClass() {
        return isUserClass;
    }

    public void setUserClass(boolean isUserClass) {
        this.isUserClass = isUserClass;
    }
}
