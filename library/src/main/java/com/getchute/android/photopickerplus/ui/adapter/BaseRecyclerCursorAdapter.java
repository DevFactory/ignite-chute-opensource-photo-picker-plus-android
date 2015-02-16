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
package com.getchute.android.photopickerplus.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chute.sdk.v2.model.enums.AccountType;
import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.config.PhotoPicker;
import com.getchute.android.photopickerplus.models.enums.DisplayType;
import com.getchute.android.photopickerplus.util.PhotoPickerPreferenceUtil;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseRecyclerCursorAdapter extends CursorRecyclerViewAdapter<BaseRecyclerCursorAdapter.ViewHolder> implements
  OnScrollListener {

  protected int dataIndex;
  protected Context context;
  public Map<Integer, String> tick = new HashMap<Integer, String>();
  protected boolean shouldLoadImages = true;

  public BaseRecyclerCursorAdapter(Context context, Cursor c) {
    super(context, c);
    this.context = context;
    dataIndex = getDataIndex(c);
    AccountType accountType = PhotoPickerPreferenceUtil.get().getAccountType();
    Map<AccountType, DisplayType> accountMap = PhotoPicker.getInstance().getAccountDisplayType();

  }

  public abstract int getDataIndex(Cursor cursor);

  abstract public void setViewClickListener(View view, String path,
                                            int position);

  abstract public void setPlayButtonVisibility(ImageView imageView);

  abstract public void loadImageView(ImageView imageView, Cursor cursor);

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageViewThumb;
    public ImageView imageViewTick;
    public ImageView imageViewVideo;
    public View viewSelect;
    public FrameLayout frameLayout;

    public ViewHolder(View itemView) {
      super(itemView);
      imageViewThumb = (ImageView) itemView
        .findViewById(R.id.gcImageViewThumb);
      imageViewTick = (ImageView) itemView
        .findViewById(R.id.gcImageViewTick);
      imageViewVideo = (ImageView) itemView
        .findViewById(R.id.gcImageViewVideo);
      viewSelect = itemView.findViewById(R.id.gcViewSelect);
      itemView.setTag(ViewHolder.this);
    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gc_adapter_assets_grid, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
    String path = cursor.getString(dataIndex);
    holder.imageViewTick.setTag(path);
    if (shouldLoadImages) {
      loadImageView(holder.imageViewThumb, cursor);
    }
    if (tick.containsKey(cursor.getPosition())) {
      holder.imageViewTick.setVisibility(View.VISIBLE);
      holder.viewSelect.setVisibility(View.VISIBLE);
      holder.itemView.setBackgroundColor(context.getResources().getColor(
        R.color.sky_blue));
    } else {
      holder.imageViewTick.setVisibility(View.GONE);
      holder.viewSelect.setVisibility(View.GONE);
      holder.itemView.setBackgroundColor(context.getResources().getColor(
        R.color.gray_light));
    }
    holder.imageViewVideo.setVisibility(View.VISIBLE);
    setViewClickListener(holder.itemView, path, cursor.getPosition());
    setPlayButtonVisibility(holder.imageViewVideo);

  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem,
                       int visibleItemCount, int totalItemCount) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {
    switch (scrollState) {
      case OnScrollListener.SCROLL_STATE_FLING:
      case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
        shouldLoadImages = false;
        break;
      case OnScrollListener.SCROLL_STATE_IDLE:
        shouldLoadImages = true;
        notifyDataSetChanged();
        break;
    }

  }

  public String getItem(int position) {
    final Cursor cursor = getCursor();
    cursor.moveToPosition(position);
    return cursor.getString(dataIndex);
  }

  @Override
  public void changeCursor(Cursor cursor) {
    super.changeCursor(cursor);
    dataIndex = getDataIndex(cursor);

  }


}
