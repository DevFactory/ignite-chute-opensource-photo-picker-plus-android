package com.getchute.android.photopickerplus.api.model.response;


public class LoginResponseModel {

  String accessToken;
  String tokenType;
  String error;
  String errorDescription;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("LoginResponseModel{");
    sb.append("accessToken='").append(accessToken).append('\'');
    sb.append(", tokenType='").append(tokenType).append('\'');
    sb.append(", error='").append(error).append('\'');
    sb.append(", errorDescription='").append(errorDescription).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
