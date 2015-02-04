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

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chute.sdk.v2.model.AccountAlbumModel;
import com.chute.sdk.v2.model.AccountBaseModel;
import com.chute.sdk.v2.model.AccountMediaModel;
import com.chute.sdk.v2.model.enums.AccountMediaType;
import com.chute.sdk.v2.model.interfaces.AccountMedia;
import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.models.enums.DisplayType;
import com.getchute.android.photopickerplus.ui.activity.AssetActivity;
import com.getchute.android.photopickerplus.ui.activity.ServicesActivity;
import com.getchute.android.photopickerplus.ui.listener.ListenerAccountAssetsSelection;
import com.getchute.android.photopickerplus.ui.listener.ListenerItemCount;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AssetAccountRecyclerAdapter extends RecyclerView.Adapter<AssetAccountRecyclerAdapter.ViewHolder> implements
  ListenerAccountAssetsSelection {

  private static final int TYPE_MAX_COUNT = 2;

  public Map<Integer, AccountMediaModel> tick;
  private final FragmentActivity context;
  private List<AccountMedia> rows;
  private AdapterItemClickListener adapterItemClickListener;
  private ListenerItemCount itemCountListener;
  private DisplayType displayType;

  public interface AdapterItemClickListener {

    public void onFolderClicked(int position);

    public void onFileClicked(int position);
  }

  public AssetAccountRecyclerAdapter(FragmentActivity context,
                                     AccountBaseModel baseModel,
                                     AdapterItemClickListener adapterItemClicklistener,
                                     DisplayType displayType, ListenerItemCount itemCountListener) {
    this.context = context;
    this.adapterItemClickListener = adapterItemClicklistener;
    this.itemCountListener = itemCountListener;
    tick = new HashMap<Integer, AccountMediaModel>();
    rows = new ArrayList<AccountMedia>();

    if (baseModel.getFiles() != null) {
      for (AccountMediaModel file : baseModel.getFiles()) {
        rows.add(file);
      }
    }
    if (baseModel.getFolders() != null) {
      for (AccountAlbumModel folder : baseModel.getFolders()) {
        rows.add(folder);
      }
    }
    if (context.getResources().getBoolean(R.bool.has_two_panes)) {
      ((ServicesActivity) context).setAssetsSelectListener(this);
    } else {
      ((AssetActivity) context).setAssetsSelectListener(this);
    }
    this.displayType = displayType;
  }


  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = null;
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    if (displayType == DisplayType.LIST) {
      itemView = layoutInflater.inflate(R.layout.gc_adapter_assets_list, parent, false);
    } else {
      itemView = layoutInflater.inflate(R.layout.gc_adapter_assets_grid, parent, false);
    }
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    int type = getItemViewType(position);
    holder.imageViewThumb.setTag(position);
    if (type == AccountMediaType.FOLDER.ordinal()) {
      holder.imageViewTick.setVisibility(View.GONE);
      holder.textViewFolderTitle.setVisibility(View.VISIBLE);
      String folderName = ((AccountAlbumModel) getItem(position))
        .getName();
      holder.textViewFolderTitle.setText(folderName != null ? folderName
        : "");
      holder.textViewFolderTitle.setTextSize(12f);
      if (displayType == DisplayType.LIST) {
        holder.textViewFolderTitle.setTextColor(context.getResources()
          .getColor(R.color.grey));
      }
      holder.imageViewThumb.setBackgroundResource(R.drawable.folder);
      holder.itemView.setOnClickListener(new OnFolderClickedListener(position));
    } else if (type == AccountMediaType.FILE.ordinal()) {
      AccountMediaModel file = (AccountMediaModel) getItem(position);
      if (displayType == DisplayType.LIST) {
        holder.textViewFolderTitle.setVisibility(View.VISIBLE);
        holder.textViewFolderTitle.setText(file.getCaption());
        holder.textViewFolderTitle.setTextSize(16f);
        holder.textViewFolderTitle.setTextColor(context.getResources()
          .getColor(R.color.grey));
      }
      holder.imageViewTick.setVisibility(View.VISIBLE);

      Picasso.with(context).load(file.getThumbnail()).fit().centerCrop().into(holder.imageViewThumb);
      holder.itemView.setOnClickListener(new OnFileClickedListener(position));
      if (file.getVideoUrl() != null) {
        holder.imageViewVideo.setVisibility(View.VISIBLE);
      }
    }

    if (tick.containsKey(position)) {
      holder.imageViewTick.setVisibility(View.VISIBLE);
      holder.itemView.setBackgroundColor(context.getResources().getColor(
        R.color.sky_blue));
      holder.viewSelect.setVisibility(View.VISIBLE);
    } else {
      holder.imageViewTick.setVisibility(View.GONE);
      holder.itemView.setBackgroundColor(context.getResources().getColor(
        R.color.gray_light));
      holder.viewSelect.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemViewType(int position) {
    return rows.get(position).getViewType().ordinal();
  }

  @Override
  public int getItemCount() {
    return rows.size();
  }

  public Object getItem(int position) {
    return rows.get(position);
  }

  public long getItemId(int position) {
    return position;
  }


  public ArrayList<AccountMediaModel> getPhotoCollection() {
    final ArrayList<AccountMediaModel> photos = new ArrayList<AccountMediaModel>();
    final Iterator<AccountMediaModel> iterator = tick.values().iterator();
    while (iterator.hasNext()) {
      photos.add(iterator.next());
    }
    return photos;
  }

  public void toggleTick(final int position) {
    if (getItemCount() > position) {
      if (getItemViewType(position) == AccountMediaType.FILE.ordinal()) {
        if (tick.containsKey(position)) {
          tick.remove(position);
        } else {
          tick.put(position, (AccountMediaModel) getItem(position));
        }
        itemCountListener.onSelectedImagesCount(tick.size());
      }
    }
    notifyDataSetChanged();
  }

  private final class OnFolderClickedListener implements OnClickListener {

    int position;

    private OnFolderClickedListener(int position) {
      this.position = position;
    }

    @Override
    public void onClick(View v) {
      adapterItemClickListener.onFolderClicked(position);

    }

  }

  private final class OnFileClickedListener implements OnClickListener {
    int position;

    private OnFileClickedListener(int position) {
      this.position = position;
    }

    @Override
    public void onClick(View v) {
      adapterItemClickListener.onFileClicked(position);

    }

  }

  @Override
  public List<Integer> getSocialPhotosSelection() {
    final ArrayList<Integer> positions = new ArrayList<Integer>();
    final Iterator<Integer> iterator = tick.keySet().iterator();
    while (iterator.hasNext()) {
      positions.add(iterator.next());
    }
    return positions;
  }

  public static final class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageViewThumb;
    public ImageView imageViewTick;
    public ImageView imageViewVideo;
    public TextView textViewFolderTitle;
    public View viewSelect;

    public ViewHolder(View itemView) {
      super(itemView);
      imageViewThumb = (ImageView) itemView.findViewById(R.id.gcImageViewThumb);
      imageViewTick = (ImageView) itemView.findViewById(R.id.gcImageViewTick);
      imageViewVideo = (ImageView) itemView.findViewById(R.id.gcImageViewVideo);
      textViewFolderTitle = (TextView) itemView.findViewById(R.id.gcTextViewAlbumTitle);
      viewSelect = itemView.findViewById(R.id.gcViewSelect);
    }
  }


}
