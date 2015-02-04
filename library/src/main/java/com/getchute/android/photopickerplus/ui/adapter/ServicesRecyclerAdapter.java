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

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chute.sdk.v2.model.enums.AccountType;
import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.config.PhotoPicker;
import com.getchute.android.photopickerplus.dao.MediaDAO;
import com.getchute.android.photopickerplus.models.enums.LocalServiceType;
import com.getchute.android.photopickerplus.ui.fragment.FragmentServices.ServiceClickedListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServicesRecyclerAdapter extends RecyclerView.Adapter<ServicesRecyclerAdapter.ListItemViewHolder> {

	private static final int VIEW_TYPE_REMOTE_ACCOUNT = 1;
	private static final int VIEW_TYPE_LOCAL_ACCOUNT = 0;

	private final boolean supportsImages;
	private final Activity context;

	private List<AccountType> remoteAccounts = new ArrayList<AccountType>();
	private List<LocalServiceType> localAccounts = new ArrayList<LocalServiceType>();
	private ServiceClickedListener serviceClickedListener;

	public ServicesRecyclerAdapter(final Activity context,
                                 List<AccountType> remoteAccounts,
                                 List<LocalServiceType> localAccounts,
                                 ServiceClickedListener serviceClickedListener) {
		this.context = context;
		this.remoteAccounts = remoteAccounts;
		this.localAccounts = localAccounts;
		this.serviceClickedListener = serviceClickedListener;
		supportsImages = PhotoPicker.getInstance().supportImages();

	}

	@Override
	public int getItemCount() {
		return remoteAccounts.size() + localAccounts.size();
	}

  @Override
	public long getItemId(final int position) {
		return position;
	}

  @Override
  public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gc_adapter_services, parent, false);
    return new ListItemViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ListItemViewHolder holder, int position) {
    if (getItemViewType(position) == VIEW_TYPE_LOCAL_ACCOUNT) {
      holder.textViewServiceTitle.setVisibility(View.VISIBLE);
      setupLocalService(holder, getLocalAccount(position));
    } else {
      setupRemoteService(holder, getRemoteAccount(position));
    }
  }

  @Override
	public int getItemViewType(int position) {
		/* Local services will come first in the adapter. */
		if (position < localAccounts.size()) {
			/* Its type Local Account */
			return VIEW_TYPE_LOCAL_ACCOUNT;
		}
		return VIEW_TYPE_REMOTE_ACCOUNT;
	}

	public LocalServiceType getLocalAccount(int position) {
		return localAccounts.get(position);
	}

	public AccountType getRemoteAccount(int position) {
		return remoteAccounts.get(position - localAccounts.size());
	}


	private void setupLocalService(ListItemViewHolder holder, LocalServiceType type) {
		Uri lastVideoThumbFromAllVideos = MediaDAO
				.getLastVideoThumbnailFromAllVideos(context
          .getApplicationContext());
		Uri lastVideoThumbFromCameraVideos = MediaDAO
				.getLastVideoThumbnailFromCameraVideos(context
						.getApplicationContext());
		Uri lastImageFromAllPhotos = MediaDAO.getLastPhotoFromAllPhotos(context
				.getApplicationContext());
		Uri lastImageFromCameraPhotos = MediaDAO
				.getLastPhotoFromCameraPhotos(context.getApplicationContext());
		switch (type) {
		case TAKE_PHOTO:
			holder.imageViewService.setBackgroundResource(R.drawable.take_photo);
			holder.textViewServiceTitle.setText(R.string.take_photos);
			break;
		case CAMERA_MEDIA:
			Uri uriCameraMedia = null;
			if (supportsImages) {
				uriCameraMedia = lastImageFromCameraPhotos;
			} else {
				uriCameraMedia = lastVideoThumbFromCameraVideos;
			}
      Picasso.with(context).load(uriCameraMedia).fit().centerCrop().into(holder.imageViewService);

			holder.textViewServiceTitle.setText(R.string.camera_media);
			break;
		case LAST_PHOTO_TAKEN:
      Picasso.with(context).load(lastImageFromCameraPhotos).fit().centerCrop().into(holder.imageViewService);
			holder.textViewServiceTitle.setText(context.getResources()
					.getString(R.string.last_photo));
			break;
		case ALL_MEDIA:
			Uri uriAllMedia = null;
			if (supportsImages) {
				uriAllMedia = lastImageFromAllPhotos;
			} else {
				uriAllMedia = lastVideoThumbFromAllVideos;
			}
      Picasso.with(context).load(uriAllMedia).fit().centerCrop().into(holder.imageViewService);
			holder.textViewServiceTitle.setText(context.getResources()
					.getString(R.string.all_media));
			break;
		case LAST_VIDEO_CAPTURED:
			String thumbnail = MediaDAO.getLastVideoThumbnailFromCurosr(context);
			Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(thumbnail,
					MediaStore.Images.Thumbnails.MINI_KIND);
			holder.imageViewService.setImageBitmap(bitmap);
			holder.textViewServiceTitle.setText(context.getResources()
					.getString(R.string.last_video_captured));
			break;
		case RECORD_VIDEO:
			holder.imageViewService.setBackgroundResource(R.drawable.take_photo);
			holder.textViewServiceTitle.setText(R.string.record_video);
			break;
		}

		/* Click listeners */
		switch (type) {
		case ALL_MEDIA:
			holder.imageViewService.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					serviceClickedListener.photoStream();
				}
			});
			break;
		case CAMERA_MEDIA:
			holder.imageViewService.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					serviceClickedListener.cameraRoll();
				}
			});

			break;
		case TAKE_PHOTO:
			holder.imageViewService.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					serviceClickedListener.takePhoto();
				}
			});

			break;
		case LAST_PHOTO_TAKEN:
			holder.imageViewService.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					serviceClickedListener.lastPhoto();
				}
			});
			break;
		case LAST_VIDEO_CAPTURED:
			holder.imageViewService.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					serviceClickedListener.lastVideo();
				}
			});
			break;
		case RECORD_VIDEO:
			holder.imageViewService.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					serviceClickedListener.recordVideo();
				}
			});
			break;
		}

	}

	@SuppressWarnings("deprecation")
	private void setupRemoteService(ListItemViewHolder holder, final AccountType type) {
		holder.textViewServiceTitle.setVisibility(View.GONE);
		holder.imageViewService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				serviceClickedListener.accountLogin(type);
			}
		});
		switch (type) {
		case FACEBOOK:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.facebook));
			break;
		case FLICKR:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.flickr));
			break;
		case INSTAGRAM:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.instagram));
			break;
		case PICASA:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.picassa));
			break;
		case GOOGLE:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.google_plus));
			break;
		case GOOGLEDRIVE:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.google_drive));
			break;
		case SKYDRIVE:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.skydrive));
			break;
		case DROPBOX:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.dropbox));
			break;
		case YOUTUBE:
			holder.imageViewService.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.youtube));
			break;
		default:
			break;
		}
	}

  public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
    ImageView imageViewService;
    TextView textViewServiceTitle;

    public ListItemViewHolder(View itemView) {
      super(itemView);
      imageViewService = (ImageView) itemView.findViewById(R.id.gcImageViewService);
      textViewServiceTitle = (TextView) itemView.findViewById(R.id.gcTextViewServiceTitle);
    }
  }


}
