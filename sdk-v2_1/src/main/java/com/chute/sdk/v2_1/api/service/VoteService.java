package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.VoteModel;
import com.chute.sdk.v2_1.model.response.ResponseModel;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface VoteService {

    //URL_VOTES
    @GET("albums/{album_id}/assets/{asset_id}/votes")
    Observable<ResponseModel<VoteModel>> voteCount(@Path("album_id") String albumId,
            @Path("asset_id") String assetId);

    //URL_VOTES
    @DELETE("albums/{album_id}/assets/{asset_id}/votes")
    Observable<Void> unvote(@Path("album_id") String albumId,
            @Path("asset_id") String assetId);

    //URL_VOTES
    @POST("albums/{album_id}/assets/{asset_id}/votes")
    Observable<ResponseModel<VoteModel>> vote(@Path("album_id") String albumId,
            @Path("asset_id") String assetId);
}
