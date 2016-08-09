package com.chute.sdk.v2_1.api.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UploadService {

    /*
   addQueryParam("client_id", clientId);
       if (isThumbnail) {
           addQueryParam("thumbnail_size", "200x200");
       }
    */
    @Multipart
    @POST("/v2/upload") Call<ResponseBody> uploadAsset(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file, @Query("client_id") String clientId,
            @Query("thumbnail_size") String thumbnailSize);
}
