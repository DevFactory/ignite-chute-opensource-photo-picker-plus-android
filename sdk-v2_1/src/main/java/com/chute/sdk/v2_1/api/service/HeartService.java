package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.HeartModel;
import com.chute.sdk.v2_1.model.response.ResponseModel;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface HeartService {

    //URL_HEARTS_COUNT
    @GET("albums/{album_id}/assets/{asset_id}/hearts")
    Observable<ResponseModel<HeartModel>> heartCount(@Path("album_id") String albumId, @Path("asset_id") String assetId);

    //URL_HEARTS_REMOVE
    @DELETE("albums/{album_id}/assets/{asset_id}/hearts")
    Observable<ResponseModel<HeartModel>> unheart(@Path("album_id") String albumId, @Path("asset_id") String assetId);

    //URL_HEARTS_CREATE
    @POST("albums/{album_id}/assets/{asset_id}/hearts")
    Observable<ResponseModel<HeartModel>> heart(@Path("album_id") String albumId, @Path("asset_id") String assetId);
}
