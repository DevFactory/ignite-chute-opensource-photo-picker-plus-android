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
package com.chute.sdk.v2_1.model;

import android.util.Log;
import com.chute.sdk.v2_1.utils.MD5;
import java.io.File;

/**
 * The {@link LocalAssetModel} class represents a concept of a local asset.
 * <p>
 * Each local asset contains status, file, priority.
 */
public class LocalAssetModel {

    public static final String TAG = LocalAssetModel.class.getSimpleName();

    /**
     * Enumeration for the status of the local asset. It can be one of the
     * following types: UNVERIFIED, NEW, INITIALIZED, COMPLETE or SKIP.
     */
    public enum AssetStatus {
        UNVERIFIED("unverified"),
        NEW("new"),
        INITIALIZED("initialized"),
        COMPLETE(
                "complete"),
        SKIP("skip");

        /**
         * Method used for getting the status of the local asset.
         */
        private AssetStatus(String name) {
            this.name = name;
        }

        /**
         * Name of the status.
         */
        private final String name;

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return name;
        }

    }

    /**
     * The unique identifier of the local asset.
     */
    private String assetId;

    /**
     * The File type of the local asset.
     */
    private File file;

    /**
     * The priority of the local asset.
     */
    private int priority;

    /**
     * Status of the local asset.
     */
    private AssetStatus assetStatus;

    /**
     * The md5 of the file.
     */
    private String fileMD5;

    /**
     * File type.
     */
    private String identifier;

    /**
     * Constructor.
     */
    public LocalAssetModel() {
        super();
        this.assetStatus = AssetStatus.UNVERIFIED;
        this.priority = 1;
    }

    /**
     * Getter and setter methods.
     */
    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File localImageFile) {
        this.file = localImageFile;
    }

    public void setFile(String path) {
        this.file = new File(path);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public AssetStatus getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(AssetStatus assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSize() {
        return String.valueOf(file.length());
    }

    /**
     * Method used for getting MD5 checksum
     */
    public String calculateFileMD5() {
        try {
            this.fileMD5 = MD5.getMD5Checksum(this.file.getPath());
            return this.fileMD5;
        } catch (Exception e) {
            Log.w(TAG, "", e);
        }
        return "";
    }

    /**
     * Method used for resolving the asset status.
     *
     * @param assetStatus String variable which represents the asset status.
     * @return {@link AssetStatus#name}.
     */
    public static AssetStatus resolveAssetStatus(String assetStatus) {
        if (assetStatus.contentEquals(AssetStatus.NEW.toString())) {
            return AssetStatus.NEW;
        } else if (assetStatus
                .contentEquals(AssetStatus.INITIALIZED.toString())) {
            return AssetStatus.INITIALIZED;
        } else if (assetStatus.contentEquals(AssetStatus.COMPLETE.toString())) {
            return AssetStatus.COMPLETE;
        } else if (assetStatus.contentEquals(AssetStatus.SKIP.toString())) {
            return AssetStatus.SKIP;
        }
        return AssetStatus.UNVERIFIED;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GCLocalAssetModel [assetId=");
        builder.append(assetId);
        builder.append(", file=");
        builder.append(file);
        builder.append(", priority=");
        builder.append(priority);
        builder.append(", assetStatus=");
        builder.append(assetStatus);
        builder.append(", fileMD5=");
        builder.append(fileMD5);
        builder.append(", identifier=");
        builder.append(identifier);
        builder.append("]");
        return builder.toString();
    }
}