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
package com.chute.android.photopickerplus.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chute.android.photopickerplus.R;
import com.chute.android.photopickerplus.callback.ImageDataResponseLoader;
import com.chute.android.photopickerplus.config.PhotoPicker;
import com.chute.android.photopickerplus.loaders.LocalImagesAsyncTaskLoader;
import com.chute.android.photopickerplus.loaders.LocalVideosAsyncTaskLoader;
import com.chute.android.photopickerplus.models.DeliverMediaModel;
import com.chute.android.photopickerplus.models.enums.DisplayType;
import com.chute.android.photopickerplus.models.enums.PhotoFilterType;
import com.chute.android.photopickerplus.ui.adapter.AssetAccountAdapter;
import com.chute.android.photopickerplus.ui.adapter.AssetAccountAdapter.AdapterItemClickListener;
import com.chute.android.photopickerplus.ui.adapter.CursorAdapterImages;
import com.chute.android.photopickerplus.ui.adapter.CursorAdapterVideos;
import com.chute.android.photopickerplus.ui.adapter.MergeAdapter;
import com.chute.android.photopickerplus.ui.listener.ListenerFilesAccount;
import com.chute.android.photopickerplus.ui.listener.ListenerFilesCursor;
import com.chute.android.photopickerplus.ui.listener.ListenerFragmentRoot;
import com.chute.android.photopickerplus.ui.listener.ListenerItemCount;
import com.chute.android.photopickerplus.util.AppUtil;
import com.chute.android.photopickerplus.util.AssetUtil;
import com.chute.android.photopickerplus.util.Constants;
import com.chute.android.photopickerplus.util.FragmentUtil;
import com.chute.android.photopickerplus.util.NotificationUtil;
import com.chute.android.photopickerplus.util.PhotoPickerPreferenceUtil;
import com.chute.android.photopickerplus.util.UIUtil;
import com.chute.sdk.v2.api.accounts.GCAccounts;
import com.chute.sdk.v2.api.authentication.TokenAuthenticationProvider;
import com.chute.sdk.v2.model.AccountAlbumModel;
import com.chute.sdk.v2.model.AccountBaseModel;
import com.chute.sdk.v2.model.AccountMediaModel;
import com.chute.sdk.v2.model.AccountModel;
import com.chute.sdk.v2.model.enums.AccountType;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;

public class FragmentRoot extends Fragment implements AdapterItemClickListener,
		ListenerItemCount {

	private GridView gridView;
	private ListView listView;
	private CursorAdapterImages adapterImages;
	private CursorAdapterVideos adapterVideos;
	private AssetAccountAdapter adapterAccounts;
	private MergeAdapter adapterMerge;
	private TextView textViewServiceTitle;
	private TextView textViewBack;
	private TextView textViewUseMedia;
	private TextView textViewClose;
	private TextView textViewLogout;
	private ProgressBar progressBar;
	private RelativeLayout relativeLayoutRoot;

	private boolean supportVideos;
	private boolean supportImages;
	private boolean isMultipicker;
	private boolean dualPanes;
	private List<Integer> selectedAccountsPositions;
	private List<Integer> selectedImagePositions;
	private List<Integer> selectedVideoPositions;
	private AccountModel account;
	private PhotoFilterType filterType;
	private AccountType accountType;
	private Map<AccountType, DisplayType> accountMap;
	private DisplayType displayType;
	private ListenerFilesCursor cursorListener;
	private ListenerFilesAccount accountListener;
	private ListenerFragmentRoot fragmetRootListener;
	private int imagesCount = 0;
	private int videosCount = 0;

	public static FragmentRoot newInstance(AccountModel account,
			PhotoFilterType filterType,
			List<Integer> selectedAccountsPositions,
			List<Integer> selectedImagePositions,
			List<Integer> selectedVideoPositions) {
		FragmentRoot frag = new FragmentRoot();
		frag.account = account;
		frag.filterType = filterType;
		frag.selectedAccountsPositions = selectedAccountsPositions;
		frag.selectedImagePositions = selectedImagePositions;
		frag.selectedVideoPositions = selectedVideoPositions;
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		cursorListener = (ListenerFilesCursor) activity;
		accountListener = (ListenerFilesAccount) activity;
		fragmetRootListener = (ListenerFragmentRoot) activity;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		isMultipicker = PhotoPicker.getInstance().isMultiPicker();
		supportVideos = PhotoPicker.getInstance().supportVideos();
		supportImages = PhotoPicker.getInstance().supportImages();
		accountType = PhotoPickerPreferenceUtil.get().getAccountType();
		accountMap = PhotoPicker.getInstance().getAccountDisplayType();
		displayType = AppUtil.getDisplayType(accountMap, PhotoPicker
				.getInstance().getDefaultAccountDisplayType(), accountType);
		dualPanes = getResources().getBoolean(R.bool.has_two_panes);

		View view = inflater.inflate(R.layout.gc_fragment_assets_grid,
				container, false);
		initView(view);

		if (savedInstanceState == null) {
			updateFragment(account, filterType, selectedAccountsPositions,
					selectedImagePositions, selectedVideoPositions);
		}
		return view;
	}

	private void initView(View view) {
		relativeLayoutRoot = (RelativeLayout) view
				.findViewById(R.id.gcRelativeLayoutRoot);
		if (displayType == DisplayType.LIST
				&& filterType == PhotoFilterType.SOCIAL_MEDIA) {
			listView = UIUtil.initListView(getActivity());
			relativeLayoutRoot.addView(listView);
		} else {
			gridView = UIUtil.initGridView(getActivity());
			relativeLayoutRoot.addView(gridView);
		}

		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		initActionBar();

	}
	
	private void initActionBar() {
		boolean dualPanes = getActivity().getResources().getBoolean(
				R.bool.has_two_panes);
		View titleView = getActivity().getActionBar().getCustomView();
		textViewUseMedia = (TextView) titleView
				.findViewById(R.id.gcTextViewUse);
		textViewUseMedia.setOnClickListener(new OkClickListener());
		if (dualPanes) {
			textViewUseMedia.setVisibility(View.VISIBLE);
			textViewServiceTitle = (TextView) titleView
					.findViewById(R.id.gcTextViewLabelChooseService);
			textViewClose = (TextView) titleView
					.findViewById(R.id.gcTextViewClose);
			textViewLogout = (TextView) titleView
					.findViewById(R.id.gcTextViewLogout);
			textViewLogout.setOnClickListener(new LogoutListener());
			textViewClose.setOnClickListener(new CloseListener());
		} else {
			textViewServiceTitle = (TextView) titleView
					.findViewById(R.id.gcTextViewLabelAccount);
			textViewBack = (TextView) titleView
					.findViewById(R.id.gcTextViewBack);
			textViewBack.setOnClickListener(new OnBackClickListener());
		}
		
		UIUtil.setFragmentLabel(getActivity(), textViewServiceTitle,
				accountType, filterType);

	}

	public void updateFragment(AccountModel account,
			PhotoFilterType filterType,
			List<Integer> selectedAccountsPositions,
			List<Integer> selectedImagePositions,
			List<Integer> selectedVideoPositions) {

		this.filterType = filterType;
		this.selectedAccountsPositions = selectedAccountsPositions;
		this.account = account;

		if ((filterType == PhotoFilterType.ALL_MEDIA)
				|| (filterType == PhotoFilterType.CAMERA_ROLL)) {
			adapterMerge = new MergeAdapter();
			adapterImages = new CursorAdapterImages(getActivity(), null,
					cursorListener, this);
			adapterVideos = new CursorAdapterVideos(getActivity(), null,
					cursorListener, this);
			adapterMerge.addAdapter(adapterVideos);
			adapterMerge.addAdapter(adapterImages);
			gridView.setAdapter(adapterMerge);
			if (supportImages) {
				getActivity().getSupportLoaderManager().initLoader(1, null,
						new ImagesLoaderCallback(selectedImagePositions));
			}
			if (supportVideos) {
				getActivity().getSupportLoaderManager().initLoader(2, null,
						new VideosLoaderCallback(selectedVideoPositions));
			}
		} else if (filterType == PhotoFilterType.SOCIAL_MEDIA
				&& getActivity() != null) {
			if (!supportVideos && accountType.equals(AccountType.YOUTUBE)) {
				progressBar.setVisibility(View.GONE);
			} else {
				GCAccounts.accountRoot(getActivity().getApplicationContext(),
						accountType.name().toLowerCase(),
						account.getShortcut(), new RootCallback())
						.executeAsync();
			}
		}

	}

	private final class RootCallback implements
			HttpCallback<ResponseModel<AccountBaseModel>> {

		@Override
		public void onHttpError(ResponseStatus responseStatus) {
			if (getActivity() != null) {
				progressBar.setVisibility(View.GONE);
				if (responseStatus.getStatusCode() == Constants.HTTP_ERROR_CODE_UNAUTHORIZED) {
					NotificationUtil
							.makeExpiredSessionLogginInAgainToast(getActivity()
									.getApplicationContext());
					accountListener.onSessionExpired(accountType);
				} else {
					NotificationUtil.makeConnectionProblemToast(getActivity()
							.getApplicationContext());
				}
			}

		}

		@Override
		public void onSuccess(ResponseModel<AccountBaseModel> responseData) {
			progressBar.setVisibility(View.GONE);
			if (responseData != null && getActivity() != null) {
				adapterAccounts = new AssetAccountAdapter(getActivity(),
						AssetUtil.filterFiles(responseData.getData(),
								supportImages, supportVideos),
						FragmentRoot.this, displayType, FragmentRoot.this);
				if (displayType == DisplayType.LIST) {
					listView.setAdapter(adapterAccounts);
				} else {
					gridView.setAdapter(adapterAccounts);
				}

				if (selectedAccountsPositions != null) {
					for (int position : selectedAccountsPositions) {
						adapterAccounts.toggleTick(position);
					}
				}

				NotificationUtil.showPhotosAdapterToast(getActivity()
						.getApplicationContext(), adapterAccounts.getCount());
			}
			UIUtil.setFragmentLabel(getActivity(), textViewServiceTitle,
					accountType, filterType);
		}

	}

	/*
	 * DEVICE IMAGES LOADER
	 */
	private final class ImagesLoaderCallback implements LoaderCallbacks<Cursor> {

		private List<Integer> imagePositions;

		private ImagesLoaderCallback(List<Integer> imagePositions) {
			this.imagePositions = imagePositions;
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
			return new LocalImagesAsyncTaskLoader(getActivity()
					.getApplicationContext(), filterType);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			if (cursor == null) {
				return;
			}

			progressBar.setVisibility(View.GONE);
			adapterImages.changeCursor(cursor);

			if (imagePositions != null) {
				for (int selectedPosition : imagePositions) {
					adapterImages.toggleTick(selectedPosition);
				}
			}

			NotificationUtil.showPhotosAdapterToast(getActivity()
					.getApplicationContext(), adapterImages.getCount());
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			// TODO Auto-generated method stub

		}

	}

	/*
	 * DEVICE VIDEOS LOADER
	 */
	private final class VideosLoaderCallback implements LoaderCallbacks<Cursor> {

		private List<Integer> videoPositions;

		private VideosLoaderCallback(List<Integer> videoPositions) {
			this.videoPositions = videoPositions;
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
			return new LocalVideosAsyncTaskLoader(getActivity()
					.getApplicationContext(), filterType);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			if (cursor == null) {
				return;
			}

			progressBar.setVisibility(View.GONE);
			adapterVideos.changeCursor(cursor);

			if (videoPositions != null) {
				for (int selectedPoosition : videoPositions) {
					adapterVideos.toggleTick(selectedPoosition);
				}
			}

			// NotificationUtil.showPhotosAdapterToast(getActivity()
			// .getApplicationContext(), adapterImages.getCount());

		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			// TODO Auto-generated method stub

		}

	}

	private final class OkClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (filterType == PhotoFilterType.SOCIAL_MEDIA) {
				if (!adapterAccounts.getPhotoCollection().isEmpty()) {
					ImageDataResponseLoader.postImageData(getActivity()
							.getApplicationContext(), adapterAccounts
							.getPhotoCollection(), accountListener, account);
				}
			} else if ((filterType == PhotoFilterType.ALL_MEDIA)
					|| (filterType == PhotoFilterType.CAMERA_ROLL)) {
				List<DeliverMediaModel> deliverList = new ArrayList<DeliverMediaModel>();
				if (!adapterImages.getSelectedFilePaths().isEmpty()) {
					deliverList.addAll(adapterImages.getSelectedFilePaths());
				}
				if (!adapterVideos.getSelectedFilePaths().isEmpty()) {
					deliverList.addAll(adapterVideos.getSelectedFilePaths());
				}
				cursorListener.onDeliverCursorAssets(deliverList);
			}
		}
	}

	@Override
	public void onFolderClicked(int position) {
		AccountAlbumModel album = (AccountAlbumModel) adapterAccounts
				.getItem(position);
		accountListener.onAccountFolderSelect(account, album.getId());

	}

	@Override
	public void onFileClicked(int position) {
		if (isMultipicker) {
			adapterAccounts.toggleTick(position);
		} else {
			ArrayList<AccountMediaModel> accountMediaModelList = new ArrayList<AccountMediaModel>();
			accountMediaModelList.add((AccountMediaModel) adapterAccounts
					.getItem(position));
			ImageDataResponseLoader.postImageData(getActivity()
					.getApplicationContext(), accountMediaModelList,
					accountListener, account);
		}

	}

	@Override
	public void onSelectedImagesCount(int count) {
		imagesCount = count;
		int itemCount = imagesCount + videosCount;
		UIUtil.setSelectedItemsCount(getActivity(), textViewUseMedia, itemCount);

	}

	@Override
	public void onSelectedVideosCount(int count) {
		videosCount = count;
		int itemCount = imagesCount + videosCount;
		UIUtil.setSelectedItemsCount(getActivity(), textViewUseMedia, itemCount);
	}

	private final class OnBackClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			fragmetRootListener.onFragmentRootNavigationBack();
		}

	}

	private final class LogoutListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (dualPanes) {
				FragmentUtil.replaceContentWithEmptyFragment(getActivity());
			}
			NotificationUtil.makeToast(getActivity().getApplicationContext(),
					R.string.toast_signed_out);
			TokenAuthenticationProvider.getInstance().clearAuth();
			PhotoPickerPreferenceUtil.get().clearAll();

		}

	}

	private final class CloseListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			getActivity().finish();

		}

	}

}