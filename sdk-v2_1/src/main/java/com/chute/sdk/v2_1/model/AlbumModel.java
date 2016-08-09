// Copyright (c) 2011, Chute Corporation. All rights reserved.
//
// Redistribution and use in source and binary forms, with or without modification,
// are permitted provided that the following conditions are met:
//
// * Redistributions of source code must retain the above copyright notice, this
// list of conditions and the following disclaimer.
// * Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
// * Neither the name of the Chute Corporation nor the names
// of its contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
// IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
// INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
// BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
// LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
// OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
// OF THE POSSIBILITY OF SUCH DAMAGE.
//
package com.chute.sdk.v2_1.model;

/**
 * Albums represent a collection of assets which can be created by a single
 * user, viewed by everyone or privileged users.
 * <p>
 * Each album contains links, counters, shortcut, name, which user it belongs
 * to, media and comments moderators and time and date of creation.
 */
public class AlbumModel {

    public static final String TAG = AlbumModel.class.getSimpleName();
    /**
     * Unique identifier.
     */
    private String id;

    /**
     * Album links.
     */
    private LinkModel links;

    /**
     * Album counters.
     */
    private CounterModel counters;

    /**
     * Album Shortcut.
     */
    private String shortcut;

    /**
     * Album name.
     */
    private String name;

    /**
     * The user the album belongs to.
     */
    private UserModel user;

    /**
     * Flag indicating whether media is moderated.
     */
    private boolean moderateMedia = false;

    /**
     * Flag indicating whether comments are moderated.
     */
    private boolean moderateComments = false;

    /**
     * Time and date of creating the album.
     */
    private String createdAt;

    /**
     * Time and date of updating the album.
     */
    private String updatedAt;

    /**
     * Album description.
     */
    private String description;

    /**
     * Parent ID of the album.
     */
    private String parentId;

    /**
     * Number of images in the album
     */
    private int imagesCount;

    /**
     * Album's first asset
     */
    private AssetModel asset;

    private String type;
    private boolean hasNewAssets;

    /**
     * Getters and setters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkModel getLinks() {
        return links;
    }

    public void setLinks(LinkModel links) {
        this.links = links;
    }

    public CounterModel getCounters() {
        return counters;
    }

    public void setCounters(CounterModel counters) {
        this.counters = counters;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public boolean isModerateMedia() {
        return moderateMedia;
    }

    public void setModerateMedia(boolean moderateMedia) {
        this.moderateMedia = moderateMedia;
    }

    public boolean isModerateComments() {
        return moderateComments;
    }

    public void setModerateComments(boolean moderateComments) {
        this.moderateComments = moderateComments;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(int imagesCount) {
        this.imagesCount = imagesCount;
    }

    public AssetModel getAsset() {
        return asset;
    }

    public void setAsset(AssetModel asset) {
        this.asset = asset;
    }

    private void setCoverAsset(AssetModel coverAsset) {
        this.asset = coverAsset;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHasNewAssets() {
        return hasNewAssets;
    }

    public void setHasNewAssets(boolean hasNewAssets) {
        this.hasNewAssets = hasNewAssets;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("AlbumModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", links=").append(links);
        sb.append(", counters=").append(counters);
        sb.append(", shortcut='").append(shortcut).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", user=").append(user);
        sb.append(", moderateMedia=").append(moderateMedia);
        sb.append(", moderateComments=").append(moderateComments);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", imagesCount=").append(imagesCount);
        sb.append(", asset=").append(asset);
        sb.append(", type='").append(type).append('\'');
        sb.append(", hasNewAssets=").append(hasNewAssets);
        sb.append('}');
        return sb.toString();
    }
}