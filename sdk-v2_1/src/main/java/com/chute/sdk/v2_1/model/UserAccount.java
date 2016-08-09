package com.chute.sdk.v2_1.model;


public class UserAccount {

  private String id;

  private String createdAt;

  private String updatedAt;

  private String shortcut;

  private String uid;

  private String type;

  private String name;

  private String username;

  private String avatar;

  private String accessKey;
  private String accessSecret;

  private String email;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getShortcut() {
    return shortcut;
  }

  public void setShortcut(String shortcut) {
    this.shortcut = shortcut;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getAccessSecret() {
    return accessSecret;
  }

  public void setAccessSecret(String accessSecret) {
    this.accessSecret = accessSecret;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "UserAccount{" +
            "id='" + id + '\'' +
            ", createdAt='" + createdAt + '\'' +
            ", updatedAt='" + updatedAt + '\'' +
            ", shortcut='" + shortcut + '\'' +
            ", uid='" + uid + '\'' +
            ", type='" + type + '\'' +
            ", name='" + name + '\'' +
            ", username='" + username + '\'' +
            ", avatar='" + avatar + '\'' +
            ", accessKey='" + accessKey + '\'' +
            ", accessSecret='" + accessSecret + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
