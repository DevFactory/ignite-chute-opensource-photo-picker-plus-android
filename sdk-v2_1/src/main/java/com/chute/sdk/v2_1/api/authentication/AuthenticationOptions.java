package com.chute.sdk.v2_1.api.authentication;

public class AuthenticationOptions {

	boolean clearCookiesForAccount;
	boolean shouldRetainSession;
	boolean clearAllCookies;

	private AuthenticationOptions(Builder builder) {
		super();
		this.clearCookiesForAccount = builder.clearCookiesForAccount;
		this.shouldRetainSession = builder.shouldRetainSession;
		this.clearAllCookies = builder.clearAllCookies;
	}

	public static class Builder {

		boolean clearCookiesForAccount = false;
		boolean shouldRetainSession = true;
		boolean clearAllCookies = false;

		public Builder() {
			super();
		}

		public Builder setClearCookiesForAccount(boolean clearCookiesForAccount) {
			this.clearCookiesForAccount = clearCookiesForAccount;
			return this;
		}
		
		public Builder setClearAllCookies(boolean clearAllCookies) {
			this.clearAllCookies = clearAllCookies;
			return this;
		}


		public Builder setShouldRetainSession(boolean shouldRetainSession) {
			this.shouldRetainSession = shouldRetainSession;
			return this;
		}

		public AuthenticationOptions build() {
			return new AuthenticationOptions(this);
		}

	}
}