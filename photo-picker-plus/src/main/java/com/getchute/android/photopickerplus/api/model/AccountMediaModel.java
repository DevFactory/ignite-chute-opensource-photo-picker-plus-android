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

import com.getchute.android.photopickerplus.api.model.enums.AccountMediaType;
import com.getchute.android.photopickerplus.api.model.interfaces.AccountMedia;

/**
 * The {@link AccountMediaModel} class represents the concept of a media item
 * from a specific album that belongs to an account. Each media item contains
 * URL, thumbnail URL, dimensions and caption.
 * 
 */
public class AccountMediaModel implements AccountMedia {

	@SuppressWarnings("unused")
	private static final String TAG = AccountMediaModel.class.getSimpleName();

	/**
	 * The unique identifier of the media item.
	 */
	private String id;

	/**
	 * Item name or caption.
	 */
	private String caption;

	/**
	 * Item dimensions
	 */
	private String dimensions;

	/**
	 * The URL that shows the location of the image item.
	 */
	private String imageUrl;

	/**
	 * The URL that shows the location of the video item.
	 */
	private String videoUrl;

	/**
	 * The URL that shows the location of the item thumbnail.
	 */
	private String thumbnail;

	/**
	 * Getter and setter methods.
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.v2.model.interfaces.AccountMedia#getViewType()
	 */
	@Override
	public AccountMediaType getViewType() {
		return AccountMediaType.FILE;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountMediaModel [id=");
		builder.append(id);
		builder.append(", caption=");
		builder.append(caption);
		builder.append(", dimensions=");
		builder.append(dimensions);
		builder.append(", imageUrl=");
		builder.append(imageUrl);
		builder.append(", videoUrl=");
		builder.append(videoUrl);
		builder.append(", thumbnail=");
		builder.append(thumbnail);
		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.v2.model.interfaces.AccountMedia#getName()
	 */
	@Override
	public String getName() {
		return getCaption();
	}

}
