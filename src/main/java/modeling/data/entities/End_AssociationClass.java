/**************************************************************************
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

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class End_AssociationClass {

	private String implicitAssociation;
	private String name;
	private String targetClass;
	private String currentClass;
	private List<String> opps;
	private List<String> opptargets;
	private Multiplicity mult;

	public End_AssociationClass(Object object) throws Exception {
		if (!(object instanceof JSONObject))
			throw new Exception();
		JSONObject end = (JSONObject) object;
		this.implicitAssociation = (String) end.get("association");
		this.name = (String) end.get("name");
		this.targetClass = (String) end.get("target");
		this.opps = (JSONArray) end.get("opps");
		this.opptargets = (JSONArray) end.get("opptargets");
		this.mult = Multiplicity.getEnum((String) end.get("mult"));
	}

	public String getImplicitAssociation() {
		return implicitAssociation;
	}

	public void setImplicitAssociation(String implicitAssociation) {
		this.implicitAssociation = implicitAssociation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public List<String> getOpps() {
		return opps;
	}

	public void setOpps(List<String> opps) {
		this.opps = opps;
	}

	public List<String> getOpptargets() {
		return opptargets;
	}

	public void setOpptargets(List<String> opptargets) {
		this.opptargets = opptargets;
	}

	public Multiplicity getMult() {
		return mult;
	}

	public void setMult(Multiplicity mult) {
		this.mult = mult;
	}

	public String getCurrentClass() {
		return currentClass;
	}

	public void setCurrentClass(String currentClass) {
		this.currentClass = currentClass;
	}

}
