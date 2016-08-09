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
package com.getchute.android.photopickerplus.util;

import android.content.Context;
import android.widget.TextView;
import com.chute.sdk.v2_1.model.enums.AccountType;
import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.models.enums.PhotoFilterType;

/**
 * Helper class containing methods needed for appropriately displaying views.
 */
public class UIUtil {

    /**
     * Sets the services fragment label according to the media types it
     * displays.
     *
     * @param context       The Application context.
     * @param textView      {@link TextView} showing the text describing the content of
     *                      the fragment.
     * @param supportImages boolean value indicating if the application supports images.
     * @param supportVideos boolean value indicating if the application supports videos.
     */
    public static void setServiceFragmentLabel(Context context,
            TextView textView, boolean supportImages, boolean supportVideos) {
        if (supportImages == true && supportVideos == false) {
            textView.setText(context.getResources().getString(
                    R.string.select_photo_source));
        } else if (supportVideos == true && supportImages == false) {
            textView.setText(context.getResources().getString(
                    R.string.select_video_source));
        } else {
            textView.setText(context.getResources().getString(
                    R.string.select_media_source));
        }
    }

    public static void setSelectedItemsCount(Context context,
            TextView textView, int count) {
        String useMedia = context.getResources().getString(
                R.string.button_use_media);
        if (count > 0) {
            textView.setText(useMedia + " (" + count + ")");
        } else {
            textView.setText(useMedia);
        }
    }

    public static String getActionBarTitle(Context context,
            AccountType accountType, PhotoFilterType photoFilterType) {
        if (photoFilterType != PhotoFilterType.SOCIAL_MEDIA) {
            return photoFilterType.getName();
        } else {
            return AppUtil.capitalizeFirstLetter(accountType.name()
                    .toLowerCase());
        }
    }
}
