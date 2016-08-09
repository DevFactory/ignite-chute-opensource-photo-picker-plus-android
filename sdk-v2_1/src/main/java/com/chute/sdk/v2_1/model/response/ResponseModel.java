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
package com.chute.sdk.v2_1.model.response;

import com.chute.sdk.v2_1.model.PaginationModel;
import com.chute.sdk.v2_1.model.ResponseStatusModel;

/**
 * 
 * The {@link ResponseModel} class represents a concept of the JSON response
 * received from the server. It consists of {@link PaginationModel},
 * {@link ResponseStatusModel} and data object.
 * 
 * @param <T>
 *            The response received can be of any kind.
 */
public class ResponseModel<T> {

	public static final String TAG = ResponseModel.class.getSimpleName();

	/**
	 * Data object
	 */
	private T data;

	/**
	 * Pagination
	 */
	private PaginationModel pagination;

	/**
	 * Response status
	 */
	private ResponseStatusModel response;

	/**
	 * Getters and setters
	 */
	public PaginationModel getPagination() {
		return pagination;
	}

	public void setPagination(PaginationModel pagination) {
		this.pagination = pagination;
	}

	public ResponseStatusModel getResponse() {
		return response;
	}

	public void setResponse(ResponseStatusModel response) {
		this.response = response;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseModel [data=");
		builder.append(data);
		builder.append(", pagination=");
		builder.append(pagination);
		builder.append(", response=");
		builder.append(response);
		builder.append("]");
		return builder.toString();
	}

}
