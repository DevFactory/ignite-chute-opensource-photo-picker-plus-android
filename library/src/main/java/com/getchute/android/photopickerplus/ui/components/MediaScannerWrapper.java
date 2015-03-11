/**
 * The MIT License (MIT)

 Copyright (c) 2013 Chute

 Permission is hereby granted, free of charge, to any person obtaining a copy of
 this software and associated documentation files (the "Software"), to deal in
 the Software without restriction, including without limitation the rights to
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.getchute.android.photopickerplus.ui.components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.sdk.v2.model.AssetModel;
import com.getchute.android.photopickerplus.models.enums.MediaType;
import com.getchute.android.photopickerplus.ui.activity.ServicesActivity;
import com.getchute.android.photopickerplus.util.AppUtil;
import com.getchute.android.photopickerplus.util.intent.IntentUtil;

import java.io.File;

public class MediaScannerWrapper implements
  MediaScannerConnection.MediaScannerConnectionClient {
  private MediaScannerConnection mConnection;
  private String mPath;
  private MediaType mMimeType;
  private Intent intent;
  private Context context;

  public MediaScannerWrapper(Context context, String filePath, MediaType mediaType, Intent intent) {
    this.context = context;
    mPath = filePath;
    mMimeType = mediaType;
    mConnection = new MediaScannerConnection(context.getApplicationContext(), this);
    this.intent = intent;
  }

  public void scan() {
    mConnection.connect();
  }

  public void onMediaScannerConnected() {
    mConnection.scanFile(mPath, mMimeType.name().toLowerCase());
  }

  public void onScanCompleted(String path, Uri uri) {
    File file = new File(path);
    Uri uriFromFile = Uri.fromFile(file);

    String imagePath = "";
    if (mMimeType == MediaType.IMAGE) {
      if (AppUtil.hasImageCaptureBug() == false) {
        imagePath = uriFromFile.toString();
      } else {
        ALog.e("Bug " + intent.getData().getPath());
        imagePath = Uri.fromFile(
          new File(AppUtil.getPath(context, intent.getData()))).toString();
      }
    } else if (mMimeType == MediaType.VIDEO) {
      imagePath = uriFromFile.toString();
    }
    final AssetModel model = new AssetModel();
    if (uri != null) {
      model.setId(uri.toString());
    }
    if (mMimeType == MediaType.VIDEO) {
      Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(),
        MediaStore.Images.Thumbnails.MINI_KIND);
      model.setThumbnail(thumbnail != null ? (AppUtil.getImagePath(context.getApplicationContext(), thumbnail)) : null);
      model.setVideoUrl(imagePath);
    } else {
      model.setThumbnail(imagePath);
    }
    model.setUrl(imagePath);
    model.setType(mMimeType.name().toLowerCase());

    IntentUtil.deliverDataToInitialActivity((ServicesActivity) context,
      model, null);
  }
}