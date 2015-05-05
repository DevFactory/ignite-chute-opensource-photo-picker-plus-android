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
package com.getchute.android.photopickerplus.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.callback.ImageDataResponseLoader;
import com.getchute.android.photopickerplus.config.PhotoPicker;
import com.getchute.android.photopickerplus.models.enums.DisplayType;
import com.getchute.android.photopickerplus.models.enums.PhotoFilterType;
import com.getchute.android.photopickerplus.ui.activity.AssetActivity;
import com.getchute.android.photopickerplus.ui.activity.ServicesActivity;
import com.getchute.android.photopickerplus.ui.adapter.AssetAccountRecyclerAdapter;
import com.getchute.android.photopickerplus.ui.listener.ListenerFilesAccount;
import com.getchute.android.photopickerplus.ui.listener.ListenerFragmentSingle;
import com.getchute.android.photopickerplus.ui.listener.ListenerItemCount;
import com.getchute.android.photopickerplus.util.AppUtil;
import com.getchute.android.photopickerplus.util.AssetUtil;
import com.getchute.android.photopickerplus.util.FragmentUtil;
import com.getchute.android.photopickerplus.util.NotificationUtil;
import com.getchute.android.photopickerplus.util.PhotoPickerPreferenceUtil;
import com.getchute.android.photopickerplus.util.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentSingle extends ActionBarFragment implements
  AssetAccountRecyclerAdapter.AdapterItemClickListener, ListenerItemCount {

  private static final String TAG = FragmentSingle.class.getSimpleName();
  private ProgressBar progressBar;
  private RecyclerView recyclerView;

  private AccountModel account;
  private AccountType accountType;
  private DisplayType displayType;
  private String folderId;
  private boolean isMultipicker;
  private List<Integer> selectedItemsPositions;

  private AssetAccountRecyclerAdapter accountAssetAdapter;
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

  @SuppressLint("NewApi")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    isMultipicker = PhotoPicker.getInstance().isMultiPicker();
    accountType = PhotoPickerPreferenceUtil.get().getAccountType();
    Map<AccountType, DisplayType> accountMap = PhotoPicker.getInstance()
      .getAccountDisplayType();
    displayType = AppUtil.getDisplayType(accountMap, PhotoPicker
      .getInstance().getDefaultAccountDisplayType(), accountType);

    getActionBarActivity().getSupportActionBar().setTitle(UIUtil.getActionBarTitle(getActivity(), accountType, PhotoFilterType.SOCIAL_MEDIA));

    View view = inflater.inflate(R.layout.gc_fragment_assets,
      container, false);

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerView = (RecyclerView) view.findViewById(R.id.gcRecyclerViewAssets);
    recyclerView.setHasFixedSize(true);
    if (displayType == DisplayType.LIST) {
      final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(linearLayoutManager);
    } else {
      final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns_assets));
      recyclerView.setLayoutManager(gridLayoutManager);
    }

    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    updateFragment(account, folderId, selectedItemsPositions);
  }

  @SuppressLint("NewApi")
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    MenuItem menuItemUse = menu.add(0, AssetActivity.USE_ITEM, 0, R.string.button_use_media);
    menuItemUse.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case ServicesActivity.LOGOUT_ITEM:
        if (getResources().getBoolean(R.bool.has_two_panes)) {
          FragmentUtil
            .replaceContentWithEmptyFragment(getActivity());
        }
        NotificationUtil.makeToast(getActivity().getApplicationContext(),
          R.string.toast_signed_out);
        TokenAuthenticationProvider.getInstance().clearAuth();
        PhotoPickerPreferenceUtil.get().clearAll();
        break;
      case AssetActivity.USE_ITEM:
        if (!accountAssetAdapter.getPhotoCollection().isEmpty()) {
          ImageDataResponseLoader.postImageData(getActivity()
            .getApplicationContext(), accountAssetAdapter
            .getPhotoCollection(), accountListener, account);
        }
        break;
      case android.R.id.home:
        if (getResources().getBoolean(R.bool.has_two_panes)) {
          getActivity().finish();
        } else {
          fragmentSingleListener.onFragmentSingleNavigationBack();
        }
        break;
    }
    return super.onOptionsItemSelected(item);
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
      Log.d(TAG, "Http Error: " + responseStatus.getStatusMessage() + " "
        + responseStatus.getStatusCode());
      progressBar.setVisibility(View.GONE);
      NotificationUtil.makeConnectionProblemToast(getActivity());

    }

    @SuppressLint("NewApi")
    @Override
    public void onSuccess(ResponseModel<AccountBaseModel> responseData, ResponseStatus responseStatus) {
      progressBar.setVisibility(View.GONE);
      boolean supportImages = PhotoPicker.getInstance().supportImages();
      boolean supportVideos = PhotoPicker.getInstance().supportVideos();
      if (responseData.getData() != null && getActivity() != null) {
        accountAssetAdapter = new AssetAccountRecyclerAdapter(getActivity(),
          AssetUtil.filterFiles(responseData.getData(),
            supportImages, supportVideos),
          FragmentSingle.this, displayType, FragmentSingle.this);
        recyclerView.setAdapter(accountAssetAdapter);

        if (selectedItemsPositions != null) {
          for (int position : selectedItemsPositions) {
            accountAssetAdapter.toggleTick(position);
          }
        }

        getActionBarActivity().getSupportActionBar().setTitle(UIUtil.getActionBarTitle(getActivity(), accountType, PhotoFilterType.SOCIAL_MEDIA));
        NotificationUtil.showPhotosAdapterToast(getActivity()
          .getApplicationContext(), accountAssetAdapter
          .getItemCount());

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
        accountListener, account
      );
    }

  }


  @Override
  public void onSelectedImagesCount(int count) {
  }

  @Override
  public void onSelectedVideosCount(int count) {

  }


}