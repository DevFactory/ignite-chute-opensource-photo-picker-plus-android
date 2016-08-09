package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.AssetModel;
import com.chute.sdk.v2_1.model.response.ListResponseModel;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchService {

    //URL_SEARCH_LOCATION
    @GET("search/location") Observable<ListResponseModel<AssetModel>> searchLocation(
            @Query("album_id") String albumId, @Query("latitude") String latitude,
            @Query("longitude") String longitude, @Query("radius") String radius);

    //URL_SEARCH_TAGS
    @GET("search/tags") Observable<ListResponseModel<AssetModel>> searchTags(
            @Query("album_id") String albumId, @Query("query[tags]") List<String> tags);

    //URL_SEARCH_EXIF
    @GET("search/exif") Observable<ListResponseModel<AssetModel>> searchExif(
            @Query("album_id") String albumId, @Query("query[key]") String exif);
}
