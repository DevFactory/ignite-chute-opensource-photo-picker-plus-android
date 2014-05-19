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
package com.chute.android.photopickerplus.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chute.android.photopickerplus.R;
import com.chute.android.photopickerplus.models.enums.PhotoFilterType;
import com.chute.sdk.v2.model.enums.AccountType;

/**
 * Helper class containing methods needed for appropriately displaying views.
 * 
 */
public class UIUtil {

	/**
	 * Sets the services fragment label according to the media types it
	 * displays.
	 * 
	 * @param context
	 *            The Application context.
	 * @param textView
	 *            {@link TextView} showing the text describing the content of
	 *            the fragment.
	 * @param supportImages
	 *            boolean value indicating if the application supports images.
	 * @param supportVideos
	 *            boolean value indicating if the application supports videos.
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

	/**
	 * Creates {@link GridView} that holds media items.
	 * 
	 * @param context
	 *            The Application context.
	 * @return {@link GridView}
	 */
	@SuppressLint("InlinedApi")
	public static GridView initGridView(Context context) {
		GridView grid = new GridView(context);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.BELOW, R.id.gcImageViewDivider);
		grid.setLayoutParams(params);
		grid.setFadingEdgeLength(0);
		grid.setFastScrollEnabled(true);
		grid.setHorizontalSpacing(10);
		grid.setPadding(20, 10, 20, 0);
		grid.setVerticalScrollBarEnabled(false);
		grid.setHorizontalScrollBarEnabled(false);
		grid.setVerticalSpacing(10);
		grid.setNumColumns(context.getResources().getInteger(
				R.integer.grid_columns_assets));
		return grid;
	}

	/**
	 * Creates {@link ListView} that holds media items.
	 * 
	 * @param context
	 *            The application context.
	 * @return {@link ListView}
	 */
	@SuppressLint("InlinedApi")
	public static ListView initListView(Context context) {
		ListView list = new ListView(context);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.BELOW, R.id.gcImageViewDivider);
		list.setLayoutParams(params);
		list.setFadingEdgeLength(0);
		list.setFastScrollEnabled(true);
		list.setVerticalScrollBarEnabled(false);
		list.setHorizontalScrollBarEnabled(false);
		list.setPadding(0, 10, 0, 0);
		return list;
	}

	/**
	 * Sets the label of the fragment displaying media items according to their
	 * type.
	 * 
	 * @param context
	 *            The application context.
	 * @param textView
	 *            textView {@link TextView} showing the text describing the
	 *            content of the fragment.
	 * @param supportImages
	 *            Indicates whether the application supports images.
	 * @param supportVideos
	 *            Indicates whether the application supports videos.
	 * @param isMultipicker
	 *            Indicates whether the application is single or multi picker.
	 */
	public static void setFragmentLabel(Context context, TextView textView,
			AccountType accountType, PhotoFilterType photoFilterType) {
		if (photoFilterType != PhotoFilterType.SOCIAL_MEDIA) {
			textView.setText(photoFilterType.getName());
		} else {
			textView.setText(AppUtil.capitalizeFirstLetter(accountType.name()
					.toLowerCase()));
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

	public static View initActionBar(FragmentActivity activity, int resId) {
		activity.getActionBar().setIcon(
				new ColorDrawable(activity.getResources().getColor(
						android.R.color.transparent)));
		activity.getActionBar().setDisplayShowTitleEnabled(false);
		activity.getActionBar().setDisplayShowCustomEnabled(true);
		activity.getActionBar().setCustomView(resId);

		return activity.getActionBar().getCustomView();
	}

}
