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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataModel {
//    private Set<Entity> entities;
    private Map<String, Entity> entities;
    private Set<Invariants> invariants;
    private Set<Association> associations;
    
    public DataModel() {
        entities = new HashMap<String, Entity>();
        invariants = new HashSet<Invariants>();
    };

    public DataModel(Object object) throws Exception {

        if (!(object instanceof JSONArray))
            throw new Exception();

//        entities = new HashSet<Entity>();
        entities = new HashMap<String, Entity>();
        invariants = new HashSet<Invariants>();

        @SuppressWarnings("unchecked")
        List<JSONObject> dataModel = (JSONArray) object;

        for (JSONObject element : dataModel) {
            if (element.containsKey("class")) {
                String className = (String) element.get("class");
                entities.put(className, new Entity(element));
            } else if (element.containsKey("invariants")) {
                invariants.add(new Invariants(element));
            }
        }

        formAssociations(this.entities);
    }

    public Map<String, Entity> getEntities() {
        return entities;
    }

    public Set<Invariants> getInvariants() {
        return invariants;
    }
    
    public List<Invariant> getInvariantsFlatten() {
        return invariants.stream().flatMap(ArrayList::stream).collect(Collectors.toList());
    }

    public Set<Association> getAssociations() {
        return associations;
    }

    public void formAssociations(Map<String, Entity> entities) {
        List<End> ends = new ArrayList<>();

        for (Map.Entry<String, Entity> entry : entities.entrySet()) {
            for (End end : entry.getValue().getEnds()) {
                ends.add(end);
            }
        }

        this.associations = filterDuplicatedAssociation(ends);
    }
    
    public void formAssociations() {
        List<End> ends = new ArrayList<>();

        for (Map.Entry<String, Entity> entry : entities.entrySet()) {
            for (End end : entry.getValue().getEnds()) {
                ends.add(end);
            }
        }

        this.associations = filterDuplicatedAssociation(ends);
    }

    private Set<Association> filterDuplicatedAssociation(
            List<End> ends) {

        List<End> filteredEnds = new ArrayList<End>();
        Set<Association> pairEnds = new HashSet<Association>();

        for (int i = 0; i < ends.size(); i++) {
            boolean matchingFlag = false;
            for (int j = 0; j < filteredEnds.size(); j++) {
                if (filteredEnds.get(j).equals(ends.get(i))) {
                    pairEnds.add(new Association(filteredEnds.get(j).getAssociation(), filteredEnds.get(j),
                            ends.get(i)));
                    matchingFlag = true;
                    break;
                }
            }

            if (!matchingFlag) {
                filteredEnds.add(ends.get(i));
            }
        }

        return pairEnds;
    }

    public void setEntities(Map<String, Entity> entities) {
        this.entities = entities;
    }

    public void setInvariants(Set<Invariants> invariants) {
        this.invariants = invariants;
    }

    public void setAssociations(Set<Association> associations) {
        this.associations = associations;
    }
    
}
