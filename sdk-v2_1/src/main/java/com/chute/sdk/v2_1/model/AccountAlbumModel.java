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

import com.chute.sdk.v2_1.model.enums.AccountMediaType;
import com.chute.sdk.v2_1.model.interfaces.AccountMedia;
import java.util.List;

/**
 * {@link AccountAlbumModel} class represents the concept of an album from a
 * specific account. Each album contains ID and name.
 */
public class AccountAlbumModel implements AccountMedia {

    @SuppressWarnings("unused")
    private static final String TAG = AccountAlbumModel.class.getSimpleName();

    /**
     * The unique identifier of the album.
     */
    private String id;
    /**
     * Name of the album.
     */
    private String name;

    private List<LinkInfoModel> links;

    /**
     * Default non-args constructor.
     */
    public AccountAlbumModel() {
        super();
    }

    /**
     * Getter and setter methods.
     */
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

    public List<LinkInfoModel> getLinks() {
        return links;
    }

    public void setLinks(List<LinkInfoModel> links) {
        this.links = links;
    }

	/*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("AccountAlbumModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", links=").append(links);
        sb.append('}');
        return sb.toString();
    }

    /*
         * (non-Javadoc)
         *
         * @see com.chute.sdk.v2.model.interfaces.AccountMedia#getViewType()
         */
    @Override
    public AccountMediaType getViewType() {
        return AccountMediaType.FOLDER;
    }
}
