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

public class Association extends Pair<End, End> {

    private String name;
    private String leftEnd;
    private String rightEnd;
    private String leftEntityName;
    private String rightEntityName;

    public Association(End left, End right) {
        super(left, right);
        this.name = left.getAssociation();
        this.leftEnd = this.getLeft().getOpp();
        this.rightEnd = this.getRight().getOpp();
        this.leftEntityName = this.getLeft().getCurrentClass();
        this.rightEntityName = this.getRight().getCurrentClass();
    }

    @Override
    public End getLeft() {
        if (super.getLeft().getName().compareTo(super.getRight().getName()) > 0)
            return super.getLeft();
        else
            return super.getRight();
    }

    @Override
    public End getRight() {
        if (super.getLeft().getName().compareTo(super.getRight().getName()) > 0)
            return super.getRight();
        else
            return super.getLeft();
    }

    public String getName() {
        return name;
    }

    public String getLeftEnd() {
        return leftEnd;
    }

    public String getRightEnd() {
        return rightEnd;
    }

    public String getLeftEntityName() {
        return leftEntityName;
    }

    public String getRightEntityName() {
        return rightEntityName;
    }

    public Boolean isManyToMany() {
        return this.getLeft().getMult() == Multiplicity.MANY
            && this.getRight().getMult() == Multiplicity.MANY;
    }

    public Boolean isManyToOne() {
        return this.getLeft().getMult() == Multiplicity.MANY
            ^ this.getRight().getMult() == Multiplicity.MANY;
    }

    public End getManyEnd() {
        if (this.getLeft().getMult() == Multiplicity.MANY)
            return this.getLeft();
        else
            return this.getRight();
    }
    
    public End getOneEnd() {
        if (this.getLeft().getMult() == Multiplicity.ONE)
            return this.getLeft();
        else
            return this.getRight();
    }

    public boolean isOneToOne() {
        return this.getLeft().getMult() != Multiplicity.MANY
            && this.getRight().getMult() != Multiplicity.MANY;
    }

    @Override
    public String toString() {
        return String.format("%1$s (%2$s) %3$s <--> %4$s (%5$s) %6$s",
            this.leftEntityName, this.leftEnd, super.getRight().getMult(),
            super.getLeft().getMult(), this.rightEnd, this.rightEntityName);
    }
}