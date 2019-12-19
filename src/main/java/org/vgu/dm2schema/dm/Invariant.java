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

import org.json.simple.JSONObject;

public class Invariant {
    private String label;
    private String ocl;

    public Invariant(Object object) throws Exception {
        if (!(object instanceof JSONObject))
            throw new Exception();
        JSONObject invariant = (JSONObject) object;
        this.label = (String) invariant.get("label");
        this.ocl = (String) invariant.get("ocl");
    }

    public String getLabel() {
        return label;
    }

    public String getOcl() {
        return ocl;
    }
}