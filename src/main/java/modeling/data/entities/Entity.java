/**************************************************************************
Copyright 2019 Vietnamese-German-University
Copyright 2023 ETH Zurich

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author: hoangnguyen (hoang.nguyen@inf.ethz.ch)
***************************************************************************/

package modeling.data.entities;

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
	// This part is for association class
	private boolean isAssociation = false;
	private Set<End_AssociationClass> end_acs;

	public Entity() {
		attributes = new HashSet<Attribute>();
		this.ends = new HashSet<End>();
	};

	public Entity(Object object) throws Exception {
		if (!(object instanceof JSONObject))
			throw new Exception();
		attributes = new HashSet<Attribute>();
		end_acs = new HashSet<End_AssociationClass>();
		this.ends = new HashSet<End>();
		
		JSONObject entity = (JSONObject) object;
		@SuppressWarnings("unchecked")
		List<JSONObject> attributes = (JSONArray) entity.get("attributes");
		@SuppressWarnings("unchecked")
		List<JSONObject> ends = (JSONArray) entity.get("ends");
		
		this.clazz = (String) entity.get("class");
		
		if (Objects.nonNull(attributes)) {
			for (JSONObject attribute : attributes) {
				this.attributes.add(new Attribute(attribute));
			}
		}
		
		if (entity.containsKey("isAssociation") && (Boolean) entity.get("isAssociation")) {
			this.setAssociation(true);
			for (JSONObject obj : ends) {
				if (obj.containsKey("opptargets")) {
					End_AssociationClass end = new End_AssociationClass(obj);
					end.setCurrentClass(this.clazz);
					this.end_acs.add(end);
				}
			}
		}
		
		if (Objects.nonNull(ends)) {
			for (JSONObject obj : ends) {
				if (!obj.containsKey("opptargets")) {
					End end = new End(obj);
					end.setCurrentClass(this.clazz);
					this.ends.add(end);
				}
			}
		}
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

	public boolean isAssociation() {
		return isAssociation;
	}

	public void setAssociation(boolean isAssociation) {
		this.isAssociation = isAssociation;
	}

	public Set<End_AssociationClass> getEnd_acs() {
		return end_acs;
	}

	public void setEnd_acs(Set<End_AssociationClass> end_acs) {
		this.end_acs = end_acs;
	}
}