package org.vgu.dm2schema.dm;

import java.util.Set;
import java.util.stream.Collectors;

public class AssociationClass extends Association {
    private Set<Attribute> attributes;

    public AssociationClass(String name, Set<Attribute> attributes, End left, End right) {
        super(name, left, right);
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        String attrString =
                attributes.stream().map(Attribute::toString).collect(Collectors.joining(", "));
        return this.getName() + " (" + attrString + ") " + this.getLeft() + this.getRight();
    }

    public Set<Attribute> getAttributes() {
        return this.attributes;
    }
}
