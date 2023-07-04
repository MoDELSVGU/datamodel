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

@author: thian
@author: hoangnguyen (hoang.nguyen@inf.ethz.ch)
***************************************************************************/

package modeling.data.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import modeling.data.config.Config;
import modeling.data.utils.DmUtils;

public class DataModel {
	private Map<String, Entity> entities;
	private Set<Association> associations;

	public DataModel() {
		entities = new HashMap<String, Entity>();
	};

	public DataModel(Object object) throws Exception {
		
		List<JSONObject> dataModel = null;
		
		if (!(object instanceof JSONArray)) {
			if (object instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject) object;
				if (jsonObject.containsKey("version") && Config.VERSION.equals((String) jsonObject.get("version"))) {
					dataModel = DmUtils.transform((JSONArray) jsonObject.get("dataModel"));
				}
			} else {
				throw new Exception();
			}
		} else {
			dataModel = (JSONArray) object;
		}
			
		entities = new HashMap<String, Entity>();

		for (JSONObject element : dataModel) {
			if (element.containsKey("class")) {
				String className = (String) element.get("class");
				entities.put(className, new Entity(element));
			}
		}

		formAssociations(this.entities);
	}

	public Map<String, Entity> getEntities() {
		return entities;
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

	private Set<Association> filterDuplicatedAssociation(List<End> ends) {

		List<End> filteredEnds = new ArrayList<End>();
		Set<Association> pairEnds = new HashSet<Association>();

		for (int i = 0; i < ends.size(); i++) {
			boolean matchingFlag = false;
			for (int j = 0; j < filteredEnds.size(); j++) {
				if (filteredEnds.get(j).equals(ends.get(i))) {
					pairEnds.add(
							new Association(filteredEnds.get(j).getAssociation(), filteredEnds.get(j), ends.get(i)));
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

	public void setAssociations(Set<Association> associations) {
		this.associations = associations;
	}

}
