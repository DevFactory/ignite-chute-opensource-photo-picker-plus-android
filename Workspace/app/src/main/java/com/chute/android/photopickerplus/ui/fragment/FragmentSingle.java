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
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.android.photopickerplus.R;
import com.chute.android.photopickerplus.callback.ImageDataResponseLoader;
import com.chute.android.photopickerplus.config.PhotoPicker;
import com.chute.android.photopickerplus.models.enums.DisplayType;
import com.chute.android.photopickerplus.models.enums.PhotoFilterType;
import com.chute.android.photopickerplus.ui.adapter.AssetAccountAdapter;
import com.chute.android.photopickerplus.ui.adapter.AssetAccountAdapter.AdapterItemClickListener;
import com.chute.android.photopickerplus.ui.listener.ListenerFilesAccount;
import com.chute.android.photopickerplus.ui.listener.ListenerFragmentSingle;
import com.chute.android.photopickerplus.ui.listener.ListenerItemCount;
import com.chute.android.photopickerplus.util.AppUtil;
import com.chute.android.photopickerplus.util.AssetUtil;
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

public class FragmentSingle extends Fragment implements
		AdapterItemClickListener, ListenerItemCount {

	private GridView gridView;
	private ListView listView;
	private TextView textViewServiceTitle;
	private TextView textViewUseMedia;
	private TextView textViewBack;
	private TextView textViewClose;
	private TextView textViewLogout;
	private ProgressBar progressBar;
	private RelativeLayout relativeLayoutRoot;

	private AccountModel account;
	private AccountType accountType;
	private DisplayType displayType;
	private String folderId;
	private boolean isMultipicker;
	private boolean dualPanes;
	private List<Integer> selectedItemsPositions;

	private AssetAccountAdapter accountAssetAdapter;
	private ListenerFilesAccount accountListener;
	private ListenerFragmentSingle fragmentSingleListener;

	public static FragmentSingle newInstance(AccountModel account,
			String folderId, List<Integer> selectedItemPositions) {
		FragmentSingle frag = new FragmentSingle();
		frag.account = account;
		frag.folderId = folderId;
		frag.selectedItemsPositions = selectedItemPositions;
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		accountListener = (ListenerFilesAccount) activity;
		fragmentSingleListener = (ListenerFragmentSingle) activity;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
		this.setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		isMultipicker = PhotoPicker.getInstance().isMultiPicker();
		dualPanes = getResources().getBoolean(R.bool.has_two_panes);
		accountType = PhotoPickerPreferenceUtil.get().getAccountType();
		Map<AccountType, DisplayType> accountMap = PhotoPicker.getInstance()
				.getAccountDisplayType();
		displayType = AppUtil.getDisplayType(accountMap, PhotoPicker
				.getInstance().getDefaultAccountDisplayType(), accountType);

		View view = inflater.inflate(R.layout.gc_fragment_assets_grid,
				container, false);
		relativeLayoutRoot = (RelativeLayout) view
				.findViewById(R.id.gcRelativeLayoutRoot);
		if (displayType == DisplayType.LIST) {
			listView = UIUtil.initListView(getActivity());
			relativeLayoutRoot.addView(listView);
		} else {
			gridView = UIUtil.initGridView(getActivity());
			relativeLayoutRoot.addView(gridView);
		}

		initActionBar();
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		updateFragment(account, folderId, selectedItemsPositions);

		return view;
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
		
	}

	public void updateFragment(AccountModel account, String folderId,
			List<Integer> selectedItemsPositions) {

		this.account = account;
		this.selectedItemsPositions = selectedItemsPositions;
		this.folderId = folderId;

		String encodedId = Uri.encode(folderId);
		if (getActivity() != null) {
			GCAccounts.accountSingle(
					PhotoPickerPreferenceUtil.get().getAccountType().name()
							.toLowerCase(), account.getShortcut(), encodedId,
					new AccountSingleCallback()).executeAsync();
		}

	}

	private final class AccountSingleCallback implements
			HttpCallback<ResponseModel<AccountBaseModel>> {

		@Override
		public void onHttpError(ResponseStatus responseStatus) {
			ALog.d("Http Error: " + responseStatus.getStatusMessage() + " "
					+ responseStatus.getStatusCode());
			progressBar.setVisibility(View.GONE);
			NotificationUtil.makeConnectionProblemToast(getActivity());

		}

		@Override
		public void onSuccess(ResponseModel<AccountBaseModel> responseData,ResponseStatus responseStatus) {
			progressBar.setVisibility(View.GONE);
			boolean supportImages = PhotoPicker.getInstance().supportImages();
			boolean supportVideos = PhotoPicker.getInstance().supportVideos();
			if (responseData.getData() != null && getActivity() != null) {
				accountAssetAdapter = new AssetAccountAdapter(getActivity(),
						AssetUtil.filterFiles(responseData.getData(),
								supportImages, supportVideos),
						FragmentSingle.this, displayType, FragmentSingle.this);
				if (displayType == DisplayType.LIST) {
					listView.setAdapter(accountAssetAdapter);
				} else {
					gridView.setAdapter(accountAssetAdapter);
				}

				if (selectedItemsPositions != null) {
					for (int position : selectedItemsPositions) {
						accountAssetAdapter.toggleTick(position);
					}
				}

				UIUtil.setFragmentLabel(getActivity(), textViewServiceTitle,
						accountType, PhotoFilterType.SOCIAL_MEDIA);
				NotificationUtil.showPhotosAdapterToast(getActivity()
						.getApplicationContext(), accountAssetAdapter
						.getCount());

			}

		}

	}

	@Override
	public void onFolderClicked(int position) {
		AccountAlbumModel album = (AccountAlbumModel) accountAssetAdapter
				.getItem(position);
		accountListener.onAccountFolderSelect(account, album.getId());

	}

	@Override
	public void onFileClicked(int position) {
		if (isMultipicker) {
			accountAssetAdapter.toggleTick(position);
		} else {
			ArrayList<AccountMediaModel> accountMediaModelList = new ArrayList<AccountMediaModel>();
			accountMediaModelList.add((AccountMediaModel) accountAssetAdapter
					.getItem(position));
			ImageDataResponseLoader.postImageData(getActivity()
					.getApplicationContext(), accountMediaModelList,
					accountListener, account);
		}

	}

	private final class OkClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (!accountAssetAdapter.getPhotoCollection().isEmpty()) {
				ImageDataResponseLoader.postImageData(getActivity()
						.getApplicationContext(), accountAssetAdapter
						.getPhotoCollection(), accountListener, account);
			}
		}
	}

	@Override
	public void onSelectedImagesCount(int count) {
		UIUtil.setSelectedItemsCount(getActivity(), textViewUseMedia, count);
	}

	@Override
	public void onSelectedVideosCount(int count) {

	}

	private final class OnBackClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			fragmentSingleListener.onFragmentSingleNavigationBack();

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