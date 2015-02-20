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
      model.setThumbnail(AppUtil.getImagePath(context.getApplicationContext(), thumbnail));
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