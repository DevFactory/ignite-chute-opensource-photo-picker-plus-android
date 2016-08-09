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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An asset represents a single media item and all information connected to it,
 * viewed by everyone or privileged users.
 * <p>
 * Each asset consists of links, thumbnail, URL, type, caption, dimensions,
 * source and the user which the asset belongs to.
 */
public class AssetModel implements Serializable {

    public static final String TAG = AssetModel.class.getSimpleName();
    private static final long serialVersionUID = -7060210544600464481L;

    /**
     * Unique identifier.
     */
    private String id;

    /**
     * Asset links.
     */
    private LinkModel links;

    /**
     * Thumbnail of the asset.
     */
    private String thumbnail;

    /**
     * Asset URL.
     */
    private String url;

    private String videoUrl;

    /**
     * Asset type.
     * <p>
     * It can be image or video.
     */
    private String type;

    /**
     * Asset caption information.
     */
    private String caption;

    /**
     * Width and height of the asset.
     */
    private DimensionsModel dimensions;

    /**
     * Asset source information.
     */
    private SourceModel source;

    /**
     * The user the asset belongs to.
     */
    private UserModel user;

    /**
     * Number of asset votes.
     */
    private int votes;

    /**
     * Number of asset hearts.
     */
    private int hearts;

    /**
     * Asset tags.
     */
    private List<String> tags;

    /**
     * Time and date of creating the asset.
     */
    private String createdAt;

    /**
     * Time and date of updating the asset.
     */
    private String updatedAt;

    /**
     * Asset shortcut.
     */
    private String shortcut;

    /**
     * Asset location.
     */
    private LocationModel location;

    /**
     * Asset origins.
     */
    private String service;

    /**
     * Chute asset ID.
     */
    private String chuteAssetId;

    /**
     * Flag indicating whether the asset is in portrait.
     */
    private boolean isPortrait;

    /**
     * Username of the user.
     */
    private String username;

    private AccountModel account;
    private String accountId;

    /**
     * Getters and setters
     */
    public String getId() {
        return id;
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

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getChuteAssetId() {
        return chuteAssetId;
    }

    public void setChuteAssetId(String chuteAssetId) {
        this.chuteAssetId = chuteAssetId;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public DimensionsModel getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionsModel dimensions) {
        this.dimensions = dimensions;
    }

    public SourceModel getSource() {
        return source;
    }

    public void setSource(SourceModel source) {
        this.source = source;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public boolean isPortrait() {
        return isPortrait;
    }

    public void setPortrait(boolean isPortrait) {
        this.isPortrait = isPortrait;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("AssetModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", links=").append(links);
        sb.append(", thumbnail='").append(thumbnail).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", videoUrl='").append(videoUrl).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", caption='").append(caption).append('\'');
        sb.append(", dimensions=").append(dimensions);
        sb.append(", source=").append(source);
        sb.append(", user=").append(user);
        sb.append(", votes=").append(votes);
        sb.append(", hearts=").append(hearts);
        sb.append(", tags=").append(tags);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append(", shortcut='").append(shortcut).append('\'');
        sb.append(", location=").append(location);
        sb.append(", service='").append(service).append('\'');
        sb.append(", chuteAssetId='").append(chuteAssetId).append('\'');
        sb.append(", isPortrait=").append(isPortrait);
        sb.append(", username='").append(username).append('\'');
        sb.append(", account=").append(account);
        sb.append(", accountId='").append(accountId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
