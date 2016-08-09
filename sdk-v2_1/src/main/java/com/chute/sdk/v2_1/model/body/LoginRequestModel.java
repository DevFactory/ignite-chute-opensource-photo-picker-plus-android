package com.chute.sdk.v2_1.model.body;

import com.chute.sdk.v2_1.api.authentication.AuthConstants;
import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {
    @SerializedName("grant_type")
    String grantType = "authorization_code";
    @SerializedName("redirect_uri")
    String redirectUri = AuthConstants.CALLBACK_URL;
    String code;

    @SerializedName("client_id")
    String clientId;

    @SerializedName("client_secret")
    String clientSecret;

    public LoginRequestModel(String code, String clientId, String clientSecret) {
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginRequestModel{" +
                "grantType='" + grantType + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", code='" + code + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
