package org.vgu.dm2schema.dm;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class AssociationClass extends Entity {
    public AssociationClass(Object object) throws Exception {
        super();
        JSONObject entity = (JSONObject) object;
        this.clazz = (String) entity.get("association-class");
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
                End end = new End();

                end.setCurrentClass(this.getName());
                end.setMult(Multiplicity.MANY);
                end.setName((String) obj.get("name"));
                end.setTargetClass((String) obj.get("target"));
                end.setOpp(null);
                this.ends.add(end);
            }
        }
    }
}
