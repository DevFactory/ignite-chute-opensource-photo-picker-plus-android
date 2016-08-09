/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Chute
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.getchute.android.photopickerplus.models;

import com.chute.sdk.v2_1.model.AssetModel;
import com.chute.sdk.v2_1.model.ParcelModel;
import com.chute.sdk.v2_1.model.ProfileModel;
import java.util.List;

/**
 * This class encapsulates the response that is received from the server when
 * uploading the selected media item(s) URL(s).
 *
 * It consists of list of {@link AssetModel}s, cliend ID, time of creation and
 * update, store ID, {@link ParcelModel} and user {@link ProfileModel}.
 */
public class MediaResponseModel {

    /**
     * List of {@link AssetModel}s
     */
    private List<AssetModel> media;

    /**
     * Client ID
     */
    private String clientId;

    /**
     * Time when the {@link AssetModel} is created
     */
    private String createdAt;

    /**
     * Store ID
     */
    private String storeId;

    /**
     * Time when the {@link AssetModel} is updated
     */
    private String updatedAt;

    /**
     * Parcel holding the {@link AssetModel}
     */
    private ParcelModel parcel;

    /**
     * User profile
     */
    private ProfileModel profile;

    public MediaResponseModel() {
    }

    /**
     * Getters and setters
     */
    public List<AssetModel> getMedia() {
        return media;
    }

    public void setMedia(List<AssetModel> assetList) {
        this.media = media;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ParcelModel getParcel() {
        return parcel;
    }

    public void setParcel(ParcelModel parcel) {
        this.parcel = parcel;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MediaResponseModel [media=");
        builder.append(media);
        builder.append(", clientId=");
        builder.append(clientId);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append(", storeId=");
        builder.append(storeId);
        builder.append(", updatedAt=");
        builder.append(updatedAt);
        builder.append(", parcel=");
        builder.append(parcel);
        builder.append(", profile=");
        builder.append(profile);
        builder.append("]");
        return builder.toString();
    }
}
