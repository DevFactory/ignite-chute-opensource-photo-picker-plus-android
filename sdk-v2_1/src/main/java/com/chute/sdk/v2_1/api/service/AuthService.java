package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.body.LoginRequestModel;
import com.chute.sdk.v2_1.model.response.LoginResponseModel;
import java.util.Map;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface AuthService {

    //URL_AUTHENTICATION_TOKEN
    @POST("oauth/token")
    Observable<LoginResponseModel> login(@Body LoginRequestModel loginRequestModel);
}
