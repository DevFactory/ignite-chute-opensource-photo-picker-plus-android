package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.AssetModel;
import com.chute.sdk.v2_1.model.GeoLocationModel;
import com.chute.sdk.v2_1.model.response.ListResponseModel;
import com.chute.sdk.v2_1.model.response.ResponseModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GeoLocationService {

    //URL_ASSETS_GEO_LOCATION
    @GET("albums/{album_id}/assets/{asset_id}/geo")
    Observable<ResponseModel<GeoLocationModel>> getLocation(@Path("album_id") String albumId, @Path("asset_id") String assetId);

    //URL_ASSETS_GET_GEO_LOCATION
    @GET("assets/geo/{latitude},{longitude}/{radius}")
    Observable<ListResponseModel<AssetModel>> getAssets(@Path("latitude") String latitude, @Path("longitude") String longitude, @Path("radius") String radius);
}
