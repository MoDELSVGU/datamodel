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

public class Association {

	private String name;
	private End leftEnd;
	private End rightEnd;

	public Association(String name, End left, End right) {
		this.name = name;
		this.leftEnd = this.getLeftEnd();
		this.rightEnd = this.getRightEnd();
	}

	public End getLeftEnd() {
		return this.leftEnd;
	}

	public End getRightEnd() {
		return this.rightEnd;
	}

	public String getName() {
		return name;
	}

	public String getLeftEndName() {
		return this.leftEnd.getOpp();
	}

	public String getRightEndName() {
		return this.rightEnd.getOpp();
	}

	public Boolean isManyToMany() {
		return this.leftEnd.getMult() == Multiplicity.MANY && this.rightEnd.getMult() == Multiplicity.MANY;
	}

	public Boolean isManyToOne() {
		return this.leftEnd.getMult() == Multiplicity.MANY ^ this.rightEnd.getMult() == Multiplicity.MANY;
	}

	public boolean isOneToOne() {
		return this.leftEnd.getMult() != Multiplicity.MANY && this.rightEnd.getMult() != Multiplicity.MANY;
	}

	public End getManyEnd() {
		if (this.leftEnd.getMult() == Multiplicity.MANY)
			return this.leftEnd;
		else
			return this.rightEnd;
	}

	@Override
	public String toString() {
		return String.format("%1$s [%2$s] <--> [%3$s] %4$s", this.leftEnd.getCurrentClass(), this.leftEnd.getMult(),
				this.rightEnd.getMult(), this.rightEnd.getCurrentClass());
	}
}
