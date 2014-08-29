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
package com.chute.android.photopickerplustutorial.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.android.photopickerplustutorial.R;
import com.chute.sdk.v2.model.AssetModel;
import com.getchute.android.photopickerplus.models.enums.MediaType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GridAdapter extends BaseAdapter {

  private static LayoutInflater inflater;
  private ArrayList<AssetModel> collection;
  private Activity context;

  public GridAdapter(final Activity context,
                     final ArrayList<AssetModel> collection) {
    this.context = context;
    if (collection == null) {
      this.collection = new ArrayList<AssetModel>();
    } else {
      this.collection = collection;
    }
    inflater = (LayoutInflater) context
      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return collection.size();
  }

  @Override
  public AssetModel getItem(int position) {
    return collection.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public static class ViewHolder {

    public ImageView imageView;
    public ImageView videoIcon;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.gc_grid_adapter_item, null);
      holder = new ViewHolder();
      holder.imageView = (ImageView) convertView
        .findViewById(R.id.gcImageViewThumb);
      holder.videoIcon = (ImageView) convertView
        .findViewById(R.id.gcImageViewVideo);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    AssetModel asset = getItem(position);

    int orientation = resolveImageOrientation(asset);
    Picasso.with(convertView.getContext()).load(asset.getThumbnail()).fit().centerCrop().into(holder.imageView);
    if (asset.getType().equalsIgnoreCase(
      MediaType.VIDEO.name().toLowerCase())) {
      holder.videoIcon.setVisibility(View.VISIBLE);
    } else {
      holder.videoIcon.setVisibility(View.GONE);
    }
    return convertView;
  }

  public void changeData(ArrayList<AssetModel> collection) {
    this.collection = collection;
    notifyDataSetChanged();
  }

  public int resolveImageOrientation(AssetModel assetModel) {
    int rotation = -1;
    if (TextUtils.isEmpty(assetModel.getId())) {
      return rotation;
    }
    if (MediaType.VIDEO.name().equalsIgnoreCase(assetModel.getType())) {
      return rotation;
    }
    if (assetModel.getUrl().startsWith("file:") == false) {
      return rotation;
    }


    Cursor mediaCursor = context.getContentResolver().query(Uri.parse(assetModel.getId()), new String[]{MediaStore.Images.ImageColumns.ORIENTATION, MediaStore.MediaColumns.SIZE}, null, null, null);

    if (mediaCursor != null && mediaCursor.getCount() != 0) {
      while (mediaCursor.moveToNext()) {
        long size = mediaCursor.getLong(1);
        ALog.d("Media Size " + size);
        //Extra check to make sure that we are getting the orientation from the proper file
        rotation = mediaCursor.getInt(0);
      }
    }
    ALog.d("Media Rotation " + rotation);
    return rotation;
  }
}
