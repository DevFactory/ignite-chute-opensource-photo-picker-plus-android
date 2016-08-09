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
package com.chute.sdk.v2_1.api.authentication;

import android.accounts.AccountAuthenticatorActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.chute.sdk.v2_1.api.Chute;
import com.chute.sdk.v2_1.model.body.LoginRequestModel;
import com.chute.sdk.v2_1.model.enums.AccountType;
import com.chute.sdk.v2_1.model.response.LoginResponseModel;
import com.chute.sdk.v2_1.utils.Utils;
import rx.Subscriber;

public class AuthenticationActivity extends AccountAuthenticatorActivity {

    private static final String TAG = AuthenticationActivity.class
            .getSimpleName();

    public static final int CODE_HTTP_EXCEPTION = 4;
    public static final int CODE_HTTP_ERROR = 5;
    public static final int CODE_PARSER_EXCEPTION = 6;
    public static final int RESULT_DIFFERENT_CHUTE_USER_AUTHENTICATED = 7;
    public static final String INTENT_DIFFERENT_CHUTE_USER_TOKEN =
            "intent_different_chute_user_token";

    private WebView webViewAuthentication;

    private AuthenticationFactory authenticationFactory;

    private ProgressBar pb;
    private String loadWebViewUrl;
    private AccountType accountType;
    private boolean shouldClearCookiesForAccount;
    private boolean shouldClearAllCookies;
    private CookieManager cookieManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        authenticationFactory = AuthenticationFactory.getInstance();
        accountType = AccountType.values()[getIntent().getExtras().getInt(
                AuthenticationFactory.EXTRA_ACCOUNT_TYPE)];
        boolean shouldRetainSession = getIntent().getExtras().getBoolean(
                AuthenticationFactory.EXTRA_RETAIN_SESSION);
        loadWebViewUrl = authenticationFactory.getAuthenticationURL(
                accountType, shouldRetainSession);
        shouldClearCookiesForAccount = getIntent().getExtras().getBoolean(
                AuthenticationFactory.EXTRA_COOKIE_ACCOUNTS);
        shouldClearAllCookies =
                getIntent().getExtras().getBoolean(AuthenticationFactory.EXTRA_ALL_COOKIES);

        webViewAuthentication = new WebView(this);
        webViewAuthentication.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        webViewAuthentication.setWebViewClient(new AuthWebViewClient());
        webViewAuthentication.getSettings().setJavaScriptEnabled(true);
        webViewAuthentication
                .setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        CookieSyncManager.createInstance(webViewAuthentication.getContext());
        cookieManager = CookieManager.getInstance();

        final WebSettings mWebSettings = webViewAuthentication.getSettings();
        if (TokenAuthenticationProvider.getInstance().isTokenValid() == false
                || shouldClearAllCookies == true) {
            clearAllCookies(mWebSettings);
        }

        if (shouldClearCookiesForAccount == true) {
            CookieSyncManager.getInstance().sync(); // Get the cookie from
            // cookie jar

            String cookieUrl = accountType.getLoginMethod().toLowerCase()
                    + ".com";
            String cookie = cookieManager.getCookie(cookieUrl);
            if (cookie == null) {
                Log.d(TAG, "No cookies");
            } else {
                Log.d(TAG, "cookie: " + cookie);
                removeCookies(cookie, cookieUrl);
            }
        }

        final FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(

                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        pb = new ProgressBar(this);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                100, 100);
        layoutParams.gravity = Gravity.CENTER;
        pb.setLayoutParams(layoutParams);
        frameLayout.addView(webViewAuthentication);
        frameLayout.addView(pb);
        setContentView(frameLayout);
        webViewAuthentication.loadUrl(loadWebViewUrl);
    }

    private class AuthWebViewClient extends WebViewClient {

        /**
         * @author darko.grozdanovski
         */

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view,
                final String url) {
            Log.e(TAG, "Override " + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(final WebView view, final String url,
                final Bitmap favicon) {
            Log.d(TAG, "Page started " + url);
            pb.setVisibility(View.VISIBLE);
            try {
                if (authenticationFactory.isRedirectUri(url)) {
                    final Bundle params = Utils.decodeUrl(url);
                    final String code = params.getString("code");
                    if (TextUtils.isEmpty(code)) {
                        setResult(CODE_HTTP_ERROR);
                        finish();
                    }
                    view.stopLoading();

                    String clientId =
                            AuthenticationFactory.getInstance().getAuthConstants().clientId;
                    String clientSecret =
                            AuthenticationFactory.getInstance().getAuthConstants().clientSecret;

                    LoginRequestModel model = new LoginRequestModel(code, clientId, clientSecret);

                    pb.setVisibility(View.VISIBLE);
                    Chute.getAuthService().login(model)
                            .subscribe(new Subscriber<LoginResponseModel>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG,
                                            "Response Not Valid, " + " Reason: " + e.getMessage());
                                    setResult(CODE_HTTP_ERROR);
                                    pb.setVisibility(View.GONE);
                                    finish();
                                }

                                @Override
                                public void onNext(LoginResponseModel loginResponseModel) {
                                    TokenAuthenticationProvider tokenProvider =
                                            TokenAuthenticationProvider
                                                    .getInstance();
                                    String token = loginResponseModel.getAccessToken();
                                    Log.d(TAG, "Token: " + token);
                                    if (tokenProvider.isTokenValid()
                                            && !token.equals(tokenProvider.getToken())) {
                                        Intent intent = new Intent();
                                        intent.putExtra(INTENT_DIFFERENT_CHUTE_USER_TOKEN, token);
                                        setResult(RESULT_DIFFERENT_CHUTE_USER_AUTHENTICATED,
                                                intent);
                                    } else {
                                        tokenProvider.setToken(token);
                                        setResult(Activity.RESULT_OK);
                                    }
                                    pb.setVisibility(View.GONE);
                                    finish();
                                }
                            });
                }
                if (authenticationFactory
                        .isAuthenticationCancelcedRedirectUri(url)) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            } catch (final Exception e) {
                setResult(CODE_HTTP_EXCEPTION);
                finish();
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(final WebView view, final String url) {
            Log.e(TAG, "Page finished " + url);
            if (shouldClearCookiesForAccount == true) {
                CookieSyncManager.getInstance().sync();
            }
            pb.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(final WebView view, final int errorCode,
                final String description, final String failingUrl) {
            Log.e(TAG, "Error " + failingUrl);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.e(TAG, "SslError: " + error.toString());
            handler.proceed();
        }
    }

    private void removeCookies(String cookie, String url) {
        String[] cookieValues = cookie.split(";");
        for (int i = 0; i < cookieValues.length; ++i) {
            String[] parts = cookieValues[i].split("=", 2);
            String cookieString = "cookieName=;expires=Sat, 31 Dec 2005 23:59:59 GMT;";
            cookieManager.setCookie(url, cookieString);
            cookieManager.removeSessionCookie();
            cookieManager.removeExpiredCookie();
        }
    }

    private void clearAllCookies(WebSettings mWebSettings) {
        webViewAuthentication.clearCache(true);
        mWebSettings.setSavePassword(false);
        mWebSettings.setSaveFormData(false);
        this.getBaseContext().deleteDatabase("webview.db");
        this.getBaseContext().deleteDatabase("webviewCache.db");
        cookieManager.removeAllCookie();
    }
}
