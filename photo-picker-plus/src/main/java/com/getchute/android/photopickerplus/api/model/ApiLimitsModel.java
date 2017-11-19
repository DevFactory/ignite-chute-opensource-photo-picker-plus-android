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


/**
 * The {@link ApiLimitsModel} class wraps up information regarding limit for
 * calls to the REST API.
 * <p>
 * The calls are divided into maximum monthly and hourly, as well as available
 * monthly and hourly.
 */
public class ApiLimitsModel {

	/**
	 * Number of maximum monthly calls.
	 */
	private long maxMonthlyCalls;

	/**
	 * Number of available monthly calls.
	 */
	private long availableMonthlyCalls;

	/**
	 * Number of maximum hourly calls.
	 */
	private long maxHourlyCalls;

	/**
	 * Number of available hourly calls.
	 */
	private long availableHourlyCalls;

	/**
	 * Getters and setters.
	 */
	public long getMaxMonthlyCalls() {
		return maxMonthlyCalls;
	}

	public void setMaxMonthlyCalls(long maxMonthlyCalls) {
		this.maxMonthlyCalls = maxMonthlyCalls;
	}

	public long getAvailableMonthlyCalls() {
		return availableMonthlyCalls;
	}

	public void setAvailableMonthlyCalls(long availableMonthlyCalls) {
		this.availableMonthlyCalls = availableMonthlyCalls;
	}

	public long getMaxHourlyCalls() {
		return maxHourlyCalls;
	}

	public void setMaxHourlyCalls(long maxHourlyCalls) {
		this.maxHourlyCalls = maxHourlyCalls;
	}

	public long getAvailableHourlyCalls() {
		return availableHourlyCalls;
	}

	public void setAvailableHourlyCalls(long availableHourlyCalls) {
		this.availableHourlyCalls = availableHourlyCalls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApiLimitsModel [maxMonthlyCalls=");
		builder.append(maxMonthlyCalls);
		builder.append(", availableMonthlyCalls=");
		builder.append(availableMonthlyCalls);
		builder.append(", maxHourlyCalls=");
		builder.append(maxHourlyCalls);
		builder.append(", availableHourlyCalls=");
		builder.append(availableHourlyCalls);
		builder.append("]");
		return builder.toString();
	}

}