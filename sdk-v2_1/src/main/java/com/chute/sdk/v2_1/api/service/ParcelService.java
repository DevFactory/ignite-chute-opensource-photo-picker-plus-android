package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.AssetModel;
import com.chute.sdk.v2_1.model.ParcelModel;
import com.chute.sdk.v2_1.model.response.ListResponseModel;
import com.chute.sdk.v2_1.model.response.ResponseModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ParcelService {

    //URL_PARCELS_GET
    @GET("parcels/{id}")
    Observable<ResponseModel<ParcelModel>> getParcel(@Path("id") String id);

    //URL_PARCELS_ASSETS
    //500
    @GET("parcels/{id}/assets")
    Observable<ListResponseModel<AssetModel>> getParcelAssets(@Path("id") String id);

    //URL_PARCELS_ALBUMS
    @GET("albums/{album_id}/parcels")
    Observable<ListResponseModel<ParcelModel>> getParcelAlbums(@Path("album_id") String albumId);
}
