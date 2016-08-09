package com.chute.sdk.v2_1.model;

import java.io.Serializable;
import java.util.List;

public class OauthAppModel implements Serializable {

    private String id;
    private String name;
    private List<String> scopes;
    private List<String> permissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("OauthAppModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", scopes=").append(scopes);
        sb.append(", permissions=").append(permissions);
        sb.append('}');
        return sb.toString();
    }
}
