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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataModel {
    private Set<Entity> entities;
    private Set<Invariants> invariants;
    private Set<Association> associations;

    public DataModel(Object object) throws Exception {
        if (!(object instanceof JSONArray))
            throw new Exception();
        entities = new HashSet<Entity>();
        invariants = new HashSet<Invariants>();
        @SuppressWarnings("unchecked")
        List<JSONObject> dataModel = (JSONArray) object;
        for (JSONObject element : dataModel) {
            if (element.containsKey("class")) {
                entities.add(new Entity(element));
            } else if (element.containsKey("invariants")) {
                invariants.add(new Invariants(element));
            }
        }
        collectAssociations(this.entities);
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public Set<Invariants> getInvariants() {
        return invariants;
    }
    
    public Set<Association> getAssociations() {
        return associations;
    }
    
    private void collectAssociations(Set<Entity> entities) {
        List<End> ends = entities.stream().map(Entity::getEnds)
            .flatMap(Set::stream).distinct().collect(Collectors.toList());
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
                    pairEnds
                        .add(new Association(filteredEnds.get(j), ends.get(i)));
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

}
