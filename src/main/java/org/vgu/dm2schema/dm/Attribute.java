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

public class Attribute {
    private String name;
    private String type;
    private Set<Constraint> constraints;
    
    public Attribute() {
        constraints = new HashSet<Constraint>();
    }

    public Attribute(Object object) throws Exception {
        if (!(object instanceof JSONObject))
            throw new Exception();

        constraints = new HashSet<Constraint>();

        JSONObject attribute = (JSONObject) object;
        this.name = (String) attribute.get("name");
        this.type = (String) attribute.get("type");
        @SuppressWarnings("unchecked")
        List<String> constraints = (JSONArray) attribute.get("constraints");
        if(Objects.nonNull(constraints)) {
            for (String constraint : constraints) {
                this.constraints.add(new MySQLConstraint(constraint));
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }
    
    @Override
    public String toString() {
        return "Name: " + name + "\n"
                + "Type: " + type + "\n";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }
    
    

}
