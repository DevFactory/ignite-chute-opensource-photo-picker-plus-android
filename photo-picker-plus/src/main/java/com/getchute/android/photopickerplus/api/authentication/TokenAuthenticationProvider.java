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
package com.getchute.android.photopickerplus.api.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.getchute.android.photopickerplus.api.utils.PreferenceUtil;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenAuthenticationProvider implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(TokenAuthenticationProvider.getInstance().isTokenValid()) {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + TokenAuthenticationProvider.getInstance().getToken())
                    .build();
            return chain.proceed(request);
        }
        return chain.proceed(chain.request());
    }

    private static final String TOKEN_KEY = "api_key";
    private static final String TAG = TokenAuthenticationProvider.class.getSimpleName();

    private static TokenAuthenticationProvider account;
    private final Context context;
    private String token;

    /**
     * <b> this object will be using a Reference to the application context via
     * getApplicationContext() NOT the Activity context</b> Recomended to be
     * initialized at the application startup or by initializing in your own class
     * extending application
     * <p>
     * <b> Dont forget to set the password on first init </b>
     *
     * @return
     */
    public static synchronized TokenAuthenticationProvider getInstance() {
        if (account == null) {
            throw new RuntimeException("Initialize the Provider first");
        }
        return account;
    }

    public static synchronized void init(Context context) {
        if (account == null) {
            account = new TokenAuthenticationProvider(context);
        }
    }

    private TokenAuthenticationProvider(final Context context) {
        this.context = context.getApplicationContext();
        initializeToken();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        saveApiKey(token);
    }

    public boolean isTokenValid() {
        return !TextUtils.isEmpty(token);
    }

    public boolean clearAuth() {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(TOKEN_KEY);
        boolean commit = editor.commit();
        PreferenceUtil.get().clearAll();
        token = null;
        return commit;
    }

    /**
     * Use as an alternative for saving the token to accounts (Note that using the
     * account manager is a preferred and safer method)
     *
     * @param apiKey the token acquired from chute auth
     * @return if the save was successful
     */
    private boolean saveApiKey(final String apiKey) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(TOKEN_KEY, apiKey);
        boolean commit = editor.commit();
        return commit;
    }

    private String restoreApiKey() {
        SharedPreferences savedSession = PreferenceManager
                .getDefaultSharedPreferences(context);
        return savedSession.getString(TOKEN_KEY, "");
    }

    private void initializeToken() {
        String apiKey = restoreApiKey();
        if (TextUtils.isEmpty(apiKey) == false) {
            this.setToken(apiKey);
        }
    }

}
