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
package com.getchute.android.photopickerplus.util.intent;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.getchute.android.photopickerplus.api.model.AccountModel;
import com.getchute.android.photopickerplus.api.model.AssetModel;
import com.getchute.android.photopickerplus.api.model.enums.AccountType;
import com.getchute.android.photopickerplus.models.enums.PhotoFilterType;
import com.getchute.android.photopickerplus.ui.activity.AssetActivity;
import java.util.ArrayList;

/**
 * {@link PhotosIntentWrapper} is a wrapper class that wraps the following
 * parameters needed for the intent:
 * <ul>
 * <li> {@link AccountModel}
 * <li>Album ID
 * <li>List of {@link AssetModel}s
 * <li>{@link PhotoFilterType}
 * </ul>
 */
public class PhotosIntentWrapper extends IntentWrapper {

  public static final int ACTIVITY_FOR_RESULT_STREAM_KEY = 113;

  public static final String KEY_PHOTO_COLLECTION = "photoCollection";
  private static final String KEY_ACCOUNT = "account";
  private static final String KEY_ALBUM_ID = "albumId";
  private static final String KEY_FILTER_TYPE = "filter_type";
  private static final String KEY_ACCOUNT_TYPE = "account_type";

  public PhotosIntentWrapper(Context context) {
    super(context, AssetActivity.class);
  }

  public PhotosIntentWrapper(Intent intent) {
    super(intent);
  }

  public AccountModel getAccount() {
    return (AccountModel) getIntent().getExtras().getSerializable(KEY_ACCOUNT);
  }

  public void setAccount(AccountModel account) {
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_ACCOUNT, account);
    getIntent().putExtras(bundle);
  }

  public String getAlbumId() {
    return getIntent().getExtras().getString(KEY_ALBUM_ID);
  }

  public void setAlbumId(String albumId) {
    getIntent().putExtra(KEY_ALBUM_ID, albumId);
  }

  @SuppressWarnings("unchecked")
  public ArrayList<AssetModel> getMediaCollection() {
    return (ArrayList<AssetModel>) getIntent().getExtras().getSerializable(
      KEY_PHOTO_COLLECTION);
  }

  public void setMediaCollection(ArrayList<AssetModel> mediaCollection) {
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_PHOTO_COLLECTION, mediaCollection);
    getIntent().putExtras(bundle);
  }

  public PhotoFilterType getFilterType() {
    return (PhotoFilterType) getIntent().getExtras().get(KEY_FILTER_TYPE);
  }

  public void setFilterType(PhotoFilterType type) {
    getIntent().putExtra(KEY_FILTER_TYPE, type);
  }

  public AccountType getAccountType() {
    return (AccountType) getIntent().getExtras().get(KEY_ACCOUNT_TYPE);
  }

  public void setAccountType(AccountType type) {
    getIntent().putExtra(KEY_ACCOUNT_TYPE, type);
  }

  public void startActivityForResult(Activity context, int code) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      context.startActivityForResult(getIntent(), code, ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
    } else {
      context.startActivityForResult(getIntent(), code);
    }
  }
}
