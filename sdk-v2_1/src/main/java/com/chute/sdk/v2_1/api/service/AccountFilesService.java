package com.chute.sdk.v2_1.api.service;

import com.chute.sdk.v2_1.model.AccountBaseModel;
import com.chute.sdk.v2_1.model.response.ResponseModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface AccountFilesService {

    @GET("v2/{account_name}/{account_shortcut}/files")
    Observable<ResponseModel<AccountBaseModel>> accountRoot(
            @Path("account_name") String accountName,
            @Path("account_shortcut") String accountShortcut);

    @GET("v2/{account_name}/{account_shortcut}/folders/{folder_id}/files")
    Observable<ResponseModel<AccountBaseModel>> accountSingle(
            @Path("account_name") String accountName,
            @Path("account_shortcut") String accountShortcut, @Path("folder_id") String folderId);
}
