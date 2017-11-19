// Copyright (c) 2011, Chute Corporation. All rights reserved.
// 
//  Redistribution and use in source and binary forms, with or without modification, 
//  are permitted provided that the following conditions are met:
// 
//     * Redistributions of source code must retain the above copyright notice, this 
//       list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright notice,
//       this list of conditions and the following disclaimer in the documentation
//       and/or other materials provided with the distribution.
//     * Neither the name of the  Chute Corporation nor the names
//       of its contributors may be used to endorse or promote products derived from
//       this software without specific prior written permission.
// 
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
//  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//  OF THE POSSIBILITY OF SUCH DAMAGE.
//
package com.getchute.android.photopickerplus.api.model;

import java.io.Serializable;

/**
 * The {@link AccountModel} class represents the concept of an account.
 * <p>
 * Each account contains type, name, shortcut, user details, access key, secret
 * and time of creation and update.
 * 
 */
public class AccountModel implements Serializable {

  @SuppressWarnings("unused")
  public static final String TAG = AccountModel.class.getSimpleName();
  private static final long serialVersionUID = -7060210556398464481L;

  /**
   * The unique identifier of the account.
   */
  private String id;
  /**
   * Type of the account. It can be: facebook, picasa, flickr, instagram,
   * googledrive, skydrive, google or dropbox.
   */
  private String type;
  /**
   * Account name.
   */
  private String name;
  /**
   * Account user unique identifier.
   */
  private String uid;

  /**
   * Time of creation.
   */
  private String createdAt;
  /**
   * Time of update.
   */
  private String updatedAt;

  /**
   * Account shortcut.
   */
  private String shortcut;

  /**
   * The username of the user currently authenticated.
   */
  private String username;

  /**
   * User avatar
   */
  private String avatar;

  /**
   * Account access key
   */
  private String accessKey;

  /**
   * Account access secret
   */
  private String accessSecret;

  /**
   * E-mail of the user
   */
  private String email;


  /**
   * Getter and setter methods.
   */
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
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


  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AccountModel [id=");
    builder.append(id);
    builder.append(", type=");
    builder.append(type);
    builder.append(", name=");
    builder.append(name);
    builder.append(", uid=");
    builder.append(uid);
    builder.append(", createdAt=");
    builder.append(createdAt);
    builder.append(", updatedAt=");
    builder.append(updatedAt);
    builder.append(", shortcut=");
    builder.append(shortcut);
    builder.append(", username=");
    builder.append(username);
    builder.append(", avatar=");
    builder.append(avatar);
    builder.append(", accessKey=");
    builder.append(accessKey);
    builder.append(", accessSecret=");
    builder.append(accessSecret);
    builder.append(", email=");
    builder.append(email);
    builder.append("]");
    return builder.toString();
  }


}
