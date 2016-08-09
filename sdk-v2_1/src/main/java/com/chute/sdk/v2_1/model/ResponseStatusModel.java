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
 * The {@link ResponseStatusModel} class represents the status of the response.
 * <p>
 * It holds info regarding error messages, error codes, protocol version, href
 * and API limitations.
 * 
 */
public class ResponseStatusModel {

	public static final String TAG = ResponseStatusModel.class.getSimpleName();

	/**
	 * Response title.
	 */
	private String title;

	/**
	 * Error message.
	 */
	private String error;

	/**
	 * Protocol version.
	 */
	private int version;

	/**
	 * Error code.
	 */
	private int code;

	/**
	 * Href info.
	 */
	private String href;

	/**
	 * API calls limits.
	 */
	private ApiLimitsModel apiLimits;

	/**
	 * Getters and setters.
	 */
	public String getError() {
		return error;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public ApiLimitsModel getApiLimits() {
		return apiLimits;
	}

	public void setApiLimits(ApiLimitsModel apiLimits) {
		this.apiLimits = apiLimits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseStatusModel [title=");
		builder.append(title);
		builder.append(", error=");
		builder.append(error);
		builder.append(", version=");
		builder.append(version);
		builder.append(", code=");
		builder.append(code);
		builder.append(", href=");
		builder.append(href);
		builder.append(", apiLimits=");
		builder.append(apiLimits);
		builder.append("]");
		return builder.toString();
	}

}
