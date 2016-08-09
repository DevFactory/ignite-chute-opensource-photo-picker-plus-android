package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.UserModel;
import com.chute.sdk.v2_1.model.response.ResponseModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface UserService {

    //URL_USERS_GET_CURRENT
    @GET("me")
    Observable<ResponseModel<UserModel>> currentUser();

    //URL_USERS_GET
    @GET("users/{id}")
    Observable<ResponseModel<UserModel>> getUser(@Path("id") String id);

    //URL_USERS_UPDATE
    @PUT("users/{id}")
    Observable<ResponseModel<UserModel>> updateUser(@Path("id") String id, @Body String name);
}
