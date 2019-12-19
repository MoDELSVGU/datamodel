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
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("serial")
public class Invariants extends ArrayList<Invariant> {

    public Invariants(Object object) throws Exception {
        if (!(object instanceof JSONObject))
            throw new Exception();
        JSONObject invariantGroup = (JSONObject) object;
        @SuppressWarnings("unchecked")
        List<JSONObject> invariants = (JSONArray) invariantGroup.get("invariants");
        for (JSONObject invariant : invariants) {
            this.add(new Invariant(invariant));
        }
    }

}
