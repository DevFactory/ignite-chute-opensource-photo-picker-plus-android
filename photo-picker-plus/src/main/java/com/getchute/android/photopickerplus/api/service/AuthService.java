package com.getchute.android.photopickerplus.api.service;

import com.getchute.android.photopickerplus.api.model.body.LoginRequestModel;
import com.getchute.android.photopickerplus.api.model.response.LoginResponseModel;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthService {

    //URL_AUTHENTICATION_TOKEN
    @POST("oauth/token")
    Observable<LoginResponseModel> login(@Body LoginRequestModel loginRequestModel);
}
