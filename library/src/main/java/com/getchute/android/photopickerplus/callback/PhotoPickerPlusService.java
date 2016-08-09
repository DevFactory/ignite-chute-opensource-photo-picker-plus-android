package com.getchute.android.photopickerplus.callback;

import com.chute.sdk.v2_1.api.authentication.TokenAuthenticationProvider;
import com.chute.sdk.v2_1.retrofit.LoggingInterceptor;
import com.chute.sdk.v2_1.retrofit.RxThreadCallAdapater;
import com.chute.sdk.v2_1.utils.RestConstants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhotoPickerPlusService {

    private static Gson gson() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return gson;
    }

    private static OkHttpClient.Builder authClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.interceptors().add(new LoggingInterceptor());
        client.interceptors().add(TokenAuthenticationProvider.getInstance());
        return client;
    }

    private static Retrofit.Builder retrofit() {
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .client(authClient().build())
                .addCallAdapterFactory(
                        new RxThreadCallAdapater(Schedulers.io(), AndroidSchedulers.mainThread()));
        return retrofit;
    }

    public static CallbackService getCallbackService() {
        Retrofit retrofit = retrofit()
                .baseUrl(RestConstants.BASE_URL)
                .build();
        return retrofit.create(CallbackService.class);
    }
}
