package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.AssetModel;
import com.chute.sdk.v2_1.model.body.AssetBodyRequestModel;
import com.chute.sdk.v2_1.model.response.ListResponseModel;
import com.chute.sdk.v2_1.model.response.ResponseModel;
import java.util.HashMap;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface AssetService {

    @GET("albums/{id}/assets")
    Observable<ListResponseModel<AssetModel>> listAssets(@Path("id") String id, @Query("per_page") String perPage);

    @GET("albums/{album_id}/assets/{asset_id}")
    Observable<ResponseModel<AssetModel>> getAsset(@Path("album_id") String albumId, @Path("asset_id") String assetId, @Query("per_page") String perPage);

    @POST("albums/{album_id}/assets/{asset_id}/copy/{new_album_id}")
    Observable<ResponseModel<AssetModel>> copyAsset(@Path("album_id") String albumId, @Path("asset_id") String assetId, @Path("new_album_id") String newAlbumId);

    @DELETE("albums/{album_id}/assets/{asset_id}")
    Observable<ResponseModel<AssetModel>> deleteAsset(@Path("album_id") String albumId, @Path("asset_id") String assetId);

    @GET("albums/{album_id}/assets/{asset_id}/exif")
    Observable<ResponseModel<HashMap<String, String>>> assetExif(@Path("album_id") String albumId, @Path("asset_id") String assetId);

    @POST("albums/{album_id}/assets/{asset_id}/move/{new_album_id}")
    Observable<ResponseModel<AssetModel>> moveAsset(@Path("album_id") String albumId, @Path("asset_id") String assetId, @Path("new_album_id") String newAlbumId);

    @PUT("albums/{album_id}/assets/{asset_id}")
    Observable<ResponseModel<AssetModel>> updateAsset(@Path("album_id") String albumId, @Path("asset_id") String assetId, @Body AssetBodyRequestModel assetBodyRequestModel);
}
