package com.getchute.android.photopickerplus.api.service;

import com.getchute.android.photopickerplus.api.model.AccountModel;
import com.getchute.android.photopickerplus.api.model.UserAccount;
import com.getchute.android.photopickerplus.api.model.response.ListResponseModel;
import com.getchute.android.photopickerplus.api.model.response.ResponseModel;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface AccountService {

    @GET("me/accounts")
    Observable<ListResponseModel<AccountModel>> currentUserAccounts();

    @GET("me/accounts")
    Observable<ListResponseModel<AccountModel>> getAccountsWithToken(
        @Query("oauth_token") String token);

    @DELETE("me/accounts/{account_id}")
    Observable<Void> currentUserUnlinkAccounts(
        @Path("account_id") String accountId);

    @GET("accounts/{account_id}")
    Observable<ResponseModel<UserAccount>> userAccount(@Path("account_id") String accountId);

}

