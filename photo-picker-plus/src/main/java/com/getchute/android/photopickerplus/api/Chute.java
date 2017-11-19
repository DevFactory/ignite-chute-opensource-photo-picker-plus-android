// Copyright (c) 2011, Chute Corporation. All rights reserved.
// 
//  Redistribution and use in source and binary forms, with or without modification, 
//  are permitted provided that the following conditions are met:
// 
//     * Redistributions of source code must retain the above copyright notice, this 
//       list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright notice,
//       this list of conditions and the following disclaimer in the documentation
//       and/or other materials provided with the distribution.
//     * Neither the name of the  Chute Corporation nor the names
//       of its contributors may be used to endorse or promote products derived from
//       this software without specific prior written permission.
// 
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
//  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//  OF THE POSSIBILITY OF SUCH DAMAGE.
// 
package com.getchute.android.photopickerplus.api;
import com.getchute.android.photopickerplus.api.authentication.TokenAuthenticationProvider;
import com.getchute.android.photopickerplus.api.retrofit.LoggingInterceptor;
import com.getchute.android.photopickerplus.api.retrofit.RxThreadCallAdapater;
import com.getchute.android.photopickerplus.api.service.AccountFilesService;
import com.getchute.android.photopickerplus.api.service.AccountService;
import com.getchute.android.photopickerplus.api.service.AuthService;
import com.getchute.android.photopickerplus.api.utils.RestConstants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Chute {

    public static final String TAG = Chute.class.getSimpleName();

    private static Gson gson() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return gson;
    }

    private static Retrofit.Builder retrofit() {
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .client(authClient().build())
                .addCallAdapterFactory(
                        new RxThreadCallAdapater(Schedulers.io(), AndroidSchedulers.mainThread()));
        return retrofit;
    }

    private static OkHttpClient.Builder authClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.interceptors().add(new LoggingInterceptor());
        client.interceptors().add(TokenAuthenticationProvider.getInstance());
        return client;
    }

    public static AuthService getAuthService() {
        Retrofit retrofit = retrofit()
                .baseUrl(RestConstants.BASE_URL)
                .build();
        return retrofit.create(AuthService.class);
    }


    public static AccountService getAccountService() {
        Retrofit retrofit = retrofit()
                .baseUrl(RestConstants.BASE_URL)
                .build();
        return retrofit.create(AccountService.class);
    }

    public static AccountFilesService getAccountFilesService() {
        Retrofit retrofit = retrofit()
                .baseUrl(RestConstants.BASE_ACCOUNT_URL)
                .build();
        return retrofit.create(AccountFilesService.class);
    }

}
