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

import org.json.simple.JSONObject;

public class Attribute {
	private String name;
	private String type;

	public Attribute() {
	}

	public Attribute(Object object) throws Exception {
		if (!(object instanceof JSONObject))
			throw new Exception();

		JSONObject attribute = (JSONObject) object;
		this.name = (String) attribute.get("name");
		this.type = (String) attribute.get("type");
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\n" + "Type: " + type + "\n";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

}
