/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Chute
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.getchute.android.photopickerplus;

import android.app.Application;
import com.getchute.android.photopickerplus.api.authentication.AuthConstants;
import com.getchute.android.photopickerplus.api.authentication.AuthenticationFactory;
import com.getchute.android.photopickerplus.api.authentication.TokenAuthenticationProvider;
import com.getchute.android.photopickerplus.api.utils.PreferenceUtil;
import com.getchute.android.photopickerplus.util.PhotoPickerPreferenceUtil;

public class PhotoPickerPlusApp extends Application {

    public static final String CLIENT_ID = "53ea3282d652e641af000004";
    public static final String CLIENT_SECRET =
            "8d326442ffbde8661de2d37075dabe45ced9b9fe11d7f02e832fb1e7dd77906b";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtil.init(getApplicationContext());
        PhotoPickerPreferenceUtil.init(getApplicationContext());
        TokenAuthenticationProvider.init(getApplicationContext());
        AuthConstants authConstants = new AuthConstants(CLIENT_ID, CLIENT_SECRET);
        AuthenticationFactory.getInstance().setAuthConstants(authConstants);
    }
}
