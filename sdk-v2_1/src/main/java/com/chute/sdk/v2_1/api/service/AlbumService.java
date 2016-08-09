package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.AlbumModel;
import com.chute.sdk.v2_1.model.AssetModel;
import com.chute.sdk.v2_1.model.body.AlbumBodyRequestModel;
import com.chute.sdk.v2_1.model.response.ListResponseModel;
import com.chute.sdk.v2_1.model.response.ResponseModel;
import java.util.List;
import java.util.Map;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface AlbumService {


    @POST("albums") Observable<ResponseModel<AlbumModel>> createAlbum(
            @Body AlbumBodyRequestModel albumBodyRequestModel);

    @DELETE("albums/{album_id}") Observable<ResponseModel<AlbumModel>> deleteAlbum(
            @Path("album_id") String albumId);

    @GET("albums/{album_id}") Observable<ResponseModel<AlbumModel>> getAlbum(
            @Path("album_id") String albumId);

    /*
      Map<String, List<String>> requestBody = new HashMap<>();
      requestBody.put("urls", new ArrayList<String>());
     */
    @POST("albums/{album_id}/assets/import") Observable<ListResponseModel<AssetModel>> importAssets(
            @Path("album_id") String albumId,
            @Body Map<String, List<String>> requestBody);

    @GET("albums?include=cover_asset&include=asset")
    Observable<ListResponseModel<AlbumModel>> listAlbumsWithAssetCoverAsset(
            @Query("per_page") String perPage);

    @GET("albums?include=asset") Observable<ListResponseModel<AlbumModel>> listAlbumsWithAsset(
            @Query("per_page") String perPage);

    @GET("albums?include=cover_asset")
    Observable<ListResponseModel<AlbumModel>> listAlbumsWithCoverAsset(
            @Query("per_page") String perPage);

    @GET("albums") Observable<ListResponseModel<AlbumModel>> listAlbums(
            @Query("per_page") String perPage);

    @GET("albums/{album_id}/albums") Observable<ListResponseModel<AlbumModel>> listNestedAlbums(
            @Path("album_id") String albumId);

    @PUT("albums/{id}") Observable<ResponseModel<AlbumModel>> updateAlbum(@Path("id") String id,
            @Body AlbumBodyRequestModel albumBodyRequestModel);

    /*
    Map<String, List<String>> body = new HashMap<>();
    body.put("asset_ids", list);
     */
    @POST("albums/{id}/remove_assets") Observable<Void> removeAssetsFromAlbum(@Path("id") String id,
            @Body Map<String, List<String>> assetIds);
}
