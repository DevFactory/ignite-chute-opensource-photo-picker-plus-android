package com.chute.sdk.v2_1.api.service;

import android.text.TextUtils;
import com.chute.sdk.v2_1.model.response.ListResponseModel;
import java.util.HashMap;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface TagService {

    //URL_ASSETS_TAGS
    @POST("albums/{album_id}/assets/{asset_id}/tags")
    Observable<ListResponseModel<String>> addTags(@Path("album_id") String albumId,
            @Path("asset_id") String assetId,
            @Body HashMap<String, List<String>> tags);

    //? URL_ASSETS_TAGS
    //tags = TextUtils.join(",", tags)
    @DELETE("albums/{album_id}/assets/{asset_id}/tags/{tags}")
    Observable<ListResponseModel<String>> removeTags(@Path("album_id") String albumId,
            @Path("asset_id") String assetId,
            @Path("tags") String tags);

    //URL_ASSETS_TAGS
    @GET("albums/{album_id}/assets/{asset_id}/tags")
    Observable<ListResponseModel<String>> getTags(@Path("album_id") String albumId,
            @Path("asset_id") String assetId);

    //URL_ASSETS_TAGS
    @PUT("albums/{album_id}/assets/{asset_id}/tags")
    Observable<ListResponseModel<String>> replaceTags(@Path("album_id") String albumId,
            @Path("asset_id") String assetId,
            @Body List<String> tags);
}
