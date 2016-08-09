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

/**
 * The {@link SourceModel} class holds asset source information such as source
 * URL, service, import URL and original URL.
 * 
 */
public class SourceModel implements Serializable {

	public static final String TAG = SourceModel.class.getSimpleName();

	/**
	 * Asset source.
	 */
	private String source;

	/**
	 * Source ID.
	 */
	private String sourceId;

	/**
	 * Source URL.
	 */
	private String sourceUrl;

	/**
	 * Source service.
	 */
	private String service;

	/**
	 * Import ID.
	 */
	private String importId;

	/**
	 * Import URL.
	 */
	private String importUrl;

	/**
	 * Original URL.
	 */
	private String originalUrl;


	/**
	 * Getters and setters.
	 */
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}

	public String getImportUrl() {
		return importUrl;
	}

	public void setImportUrl(String importUrl) {
		this.importUrl = importUrl;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SourceModel [source=");
		builder.append(source);
		builder.append(", sourceId=");
		builder.append(sourceId);
		builder.append(", sourceUrl=");
		builder.append(sourceUrl);
		builder.append(", service=");
		builder.append(service);
		builder.append(", importId=");
		builder.append(importId);
		builder.append(", importUrl=");
		builder.append(importUrl);
		builder.append(", originalUrl=");
		builder.append(originalUrl);
		builder.append("]");
		return builder.toString();
	}

}
