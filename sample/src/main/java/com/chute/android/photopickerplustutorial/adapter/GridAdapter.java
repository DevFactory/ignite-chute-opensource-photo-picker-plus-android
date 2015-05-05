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
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chute.android.photopickerplustutorial.R;
import com.chute.android.photopickerplustutorial.activity.PhotoGridActivity;
import com.chute.android.photopickerplustutorial.activity.VideoPlayerActivity;
import com.chute.sdk.v2.model.AssetModel;
import com.getchute.android.photopickerplus.models.enums.MediaType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

  private static final String TAG = GridAdapter.class.getSimpleName();
  private static ArrayList<AssetModel> collection;
  private static Activity context;

  public GridAdapter(final Activity context,
                     final ArrayList<AssetModel> collection) {
    this.context = context;
    if (collection == null) {
      this.collection = new ArrayList<AssetModel>();
    } else {
      this.collection = collection;
    }
  }


  public static AssetModel getItem(int position) {
    return collection.get(position);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = null;
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    itemView = layoutInflater.inflate(R.layout.gc_grid_adapter_item, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    AssetModel asset = getItem(position);
    int orientation = resolveImageOrientation(asset);
    Picasso.with(holder.itemView.getContext()).load(asset.getThumbnail()).placeholder(R.drawable.placeholder).fit().centerCrop().into(holder.imageView);
    if (asset.getType().equalsIgnoreCase(MediaType.VIDEO.name().toLowerCase())) {
      holder.videoIcon.setVisibility(View.VISIBLE);
    } else {
      holder.videoIcon.setVisibility(View.GONE);
    }
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    return collection.size();
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
        Log.d(TAG, "Media Size " + size);
        //Extra check to make sure that we are getting the orientation from the proper file
        rotation = mediaCursor.getInt(0);
      }
    }
    Log.d(TAG, "Media Rotation " + rotation);
    return rotation;
  }

  public static final class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public ImageView videoIcon;

    public ViewHolder(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.gcImageViewThumb);
      videoIcon = (ImageView) itemView.findViewById(R.id.gcImageViewVideo);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          AssetModel asset = getItem(getPosition());
          String type = asset.getType();
          if (type.equals(MediaType.VIDEO.name().toLowerCase())) {
            if (asset.getVideoUrl().contains("http")) {
              Intent intent = new Intent(Intent.ACTION_VIEW);
              intent.setData(Uri.parse(asset.getVideoUrl()));
              context.startActivity(intent);
            } else {
              Intent intent = new Intent(context,
                VideoPlayerActivity.class);
              intent.putExtra(PhotoGridActivity.KEY_VIDEO_PATH, asset.getVideoUrl());
              context.startActivity(intent);
            }
          }
        }
      });
    }
  }
}
