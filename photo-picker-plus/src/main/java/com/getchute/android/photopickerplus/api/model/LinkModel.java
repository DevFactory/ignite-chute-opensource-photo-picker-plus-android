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
package com.getchute.android.photopickerplus.api.model;

import java.io.Serializable;

/**
 * The {@link LinkModel} class holds self, asset and exif links.
 */
public class LinkModel implements Serializable {

    /**
     * Self data links.
     */
    private LinkInfoModel self;

    /**
     * Asset links.
     */
    private LinkInfoModel assets;

    /**
     * Exif data links.
     */
    private LinkInfoModel exif;

    /**
     * Geo location links.
     */
    private LinkInfoModel geo;

    /**
     * Hearting info links.
     */
    private LinkInfoModel heart;

    /**
     * Vote info links.
     */
    private LinkInfoModel vote;

    /**
     * Parcel links.
     */
    private LinkInfoModel parcels;

    /**
     * Media links.
     */
    private LinkInfoModel media;

    private LinkInfoModel apps;

    /**
     * Getters and setters
     */
    public LinkInfoModel getSelf() {
        return self;
    }

    public void setSelf(LinkInfoModel self) {
        this.self = self;
    }

    public LinkInfoModel getAssets() {
        return assets;
    }

    public void setAssets(LinkInfoModel assets) {
        this.assets = assets;
    }

    public LinkInfoModel getExif() {
        return exif;
    }

    public void setExif(LinkInfoModel exif) {
        this.exif = exif;
    }

    public LinkInfoModel getGeo() {
        return geo;
    }

    public void setGeo(LinkInfoModel geo) {
        this.geo = geo;
    }

    public LinkInfoModel getHeart() {
        return heart;
    }

    public void setHeart(LinkInfoModel heart) {
        this.heart = heart;
    }

    public LinkInfoModel getVote() {
        return vote;
    }

    public void setVote(LinkInfoModel vote) {
        this.vote = vote;
    }

    public LinkInfoModel getParcels() {
        return parcels;
    }

    public void setParcels(LinkInfoModel parcels) {
        this.parcels = parcels;
    }

    public LinkInfoModel getMedia() {
        return media;
    }

    public void setMedia(LinkInfoModel media) {
        this.media = media;
    }

    public LinkInfoModel getApps() {
        return apps;
    }

    public void setApps(LinkInfoModel apps) {
        this.apps = apps;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("LinkModel{");
        sb.append("self=").append(self);
        sb.append(", assets=").append(assets);
        sb.append(", exif=").append(exif);
        sb.append(", geo=").append(geo);
        sb.append(", heart=").append(heart);
        sb.append(", vote=").append(vote);
        sb.append(", parcels=").append(parcels);
        sb.append(", media=").append(media);
        sb.append(", apps=").append(apps);
        sb.append('}');
        return sb.toString();
    }
}