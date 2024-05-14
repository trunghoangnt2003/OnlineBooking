package org.frog.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


public class Role_Feature {

    private Feature feature;

    private Role role;

    public Role_Feature(Feature feature, Role role) {
        this.feature = feature;
        this.role = role;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
