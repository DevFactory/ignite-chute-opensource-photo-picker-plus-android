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
package com.chute.android.photopickerplustutorial.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.chute.android.photopickerplustutorial.R;
import com.getchute.android.photopickerplus.api.model.AccountModel;
import com.getchute.android.photopickerplus.api.model.AssetModel;
import com.getchute.android.photopickerplus.util.intent.PhotoPickerPlusIntentWrapper;
import java.util.ArrayList;
import java.util.List;

public class PhotoPickerPlusTutorialActivity extends FragmentActivity {

    public static final String KEY_MEDIA_LSIT = "keyMediaList";
    private static final String TAG = PhotoPickerPlusTutorialActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gc_activity_main);

        Button startButton = (Button) findViewById(R.id.gcButtonChoosePhotos);
        startButton.setOnClickListener(new OnPhotoPickerClickListener());
    }

    private class OnPhotoPickerClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            PhotoPickerPlusIntentWrapper wrapper = new PhotoPickerPlusIntentWrapper(
                    PhotoPickerPlusTutorialActivity.this);
            wrapper.startActivityForResult(PhotoPickerPlusTutorialActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        final PhotoPickerPlusIntentWrapper wrapper = new PhotoPickerPlusIntentWrapper(
                data);
        List<AssetModel> accountMediaList = wrapper.getMediaCollection();
        AccountModel accountModel = wrapper.getAccountModel();

        if (accountMediaList != null) Log.d(TAG, accountMediaList.toString());
        if (accountModel != null) Log.d(TAG, accountModel.toString());
        Intent intent = new Intent(getApplicationContext(),
                PhotoGridActivity.class);
        intent.putParcelableArrayListExtra(KEY_MEDIA_LSIT, (ArrayList) accountMediaList);
        startActivity(intent);
    }
}
