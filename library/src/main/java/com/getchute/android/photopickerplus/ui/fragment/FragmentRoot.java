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
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.getchute.android.photopickerplus.loaders.LocalImagesAsyncTaskLoader;
import com.getchute.android.photopickerplus.loaders.LocalVideosAsyncTaskLoader;
import com.getchute.android.photopickerplus.models.DeliverMediaModel;
import com.getchute.android.photopickerplus.models.enums.DisplayType;
import com.getchute.android.photopickerplus.models.enums.PhotoFilterType;
import com.getchute.android.photopickerplus.ui.activity.AssetActivity;
import com.getchute.android.photopickerplus.ui.activity.ServicesActivity;
import com.getchute.android.photopickerplus.ui.adapter.AssetAccountRecyclerAdapter;
import com.getchute.android.photopickerplus.ui.adapter.CursorAdapterImages;
import com.getchute.android.photopickerplus.ui.adapter.CursorAdapterVideos;
import com.getchute.android.photopickerplus.ui.adapter.MergeRecyclerAdapter;
import com.getchute.android.photopickerplus.ui.listener.ListenerFilesAccount;
import com.getchute.android.photopickerplus.ui.listener.ListenerFilesCursor;
import com.getchute.android.photopickerplus.ui.listener.ListenerFragmentRoot;
import com.getchute.android.photopickerplus.ui.listener.ListenerItemCount;
import com.getchute.android.photopickerplus.util.AppUtil;
import com.getchute.android.photopickerplus.util.AssetUtil;
import com.getchute.android.photopickerplus.util.Constants;
import com.getchute.android.photopickerplus.util.FragmentUtil;
import com.getchute.android.photopickerplus.util.NotificationUtil;
import com.getchute.android.photopickerplus.util.PhotoPickerPreferenceUtil;
import com.getchute.android.photopickerplus.util.UIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentRoot extends Fragment implements AssetAccountRecyclerAdapter.AdapterItemClickListener,
  ListenerItemCount {

  private CursorAdapterImages adapterImages;
  private CursorAdapterVideos adapterVideos;
  private AssetAccountRecyclerAdapter adapterAccounts;
  private MergeRecyclerAdapter adapterMerge;
  private ProgressBar progressBar;
  private RecyclerView recyclerView;

  private boolean supportVideos;
  private boolean supportImages;
  private boolean isMultipicker;
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

  @SuppressLint("NewApi")
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

    View view = inflater.inflate(R.layout.gc_fragment_assets,
      container, false);

    getActivity().getActionBar().setTitle(UIUtil.getActionBarTitle(getActivity(), accountType, filterType));

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView(view);

    if (savedInstanceState == null) {
      updateFragment(account, filterType, selectedAccountsPositions,
        selectedImagePositions, selectedVideoPositions);
    }
  }

  private void initView(View view) {
    recyclerView = (RecyclerView) view.findViewById(R.id.gcRecyclerViewAssets);
    recyclerView.setHasFixedSize(true);
    if (displayType == DisplayType.LIST
      && filterType == PhotoFilterType.SOCIAL_MEDIA) {
      final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(linearLayoutManager);
    } else {
      final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_columns_assets));
      recyclerView.setLayoutManager(gridLayoutManager);
    }
    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
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
        useMedia();
        break;
      case android.R.id.home:
        if (getResources().getBoolean(R.bool.has_two_panes)) {
          getActivity().finish();
        } else {
          fragmetRootListener.onFragmentRootNavigationBack();
        }
        break;
    }
    return super.onOptionsItemSelected(item);
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
      adapterMerge = new MergeRecyclerAdapter();
      adapterImages = new CursorAdapterImages(getActivity(), null,
        cursorListener, this);
      adapterVideos = new CursorAdapterVideos(getActivity(), null,
        cursorListener, this);
      adapterMerge.addAdapter(adapterVideos);
      adapterMerge.addAdapter(adapterImages);
      recyclerView.setAdapter(adapterMerge);
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
        GCAccounts.accountRoot(accountType.name().toLowerCase(),
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
    public void onSuccess(ResponseModel<AccountBaseModel> responseData, ResponseStatus responseStatus) {
      progressBar.setVisibility(View.GONE);
      if (responseData != null && getActivity() != null) {
        adapterAccounts = new AssetAccountRecyclerAdapter(getActivity(),
          AssetUtil.filterFiles(responseData.getData(),
            supportImages, supportVideos),
          FragmentRoot.this, displayType, FragmentRoot.this);
        recyclerView.setAdapter(adapterAccounts);

        if (selectedAccountsPositions != null) {
          for (int position : selectedAccountsPositions) {
            adapterAccounts.toggleTick(position);
          }
        }

        NotificationUtil.showPhotosAdapterToast(getActivity()
          .getApplicationContext(), adapterAccounts.getItemCount());
      }

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
        .getApplicationContext(), adapterImages.getItemCount());
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

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
      // TODO Auto-generated method stub

    }

  }


  public void useMedia() {
    if (filterType == PhotoFilterType.SOCIAL_MEDIA) {
      if (!adapterAccounts.getPhotoCollection().isEmpty()) {
        ImageDataResponseLoader.postImageData(getActivity()
          .getApplicationContext(), adapterAccounts
          .getPhotoCollection(), accountListener, account);
      }
    } else if ((filterType == PhotoFilterType.ALL_MEDIA)
      || (filterType == PhotoFilterType.CAMERA_ROLL)) {
      List<DeliverMediaModel> deliverList = new ArrayList<>();
      if (!adapterImages.getSelectedFilePaths().isEmpty()) {
        deliverList.addAll(adapterImages.getSelectedFilePaths());
      }
      if (!adapterVideos.getSelectedFilePaths().isEmpty()) {
        deliverList.addAll(adapterVideos.getSelectedFilePaths());
      }
      cursorListener.onDeliverCursorAssets(deliverList);
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
      ArrayList<AccountMediaModel> accountMediaModelList = new ArrayList<>();
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

  }

  @Override
  public void onSelectedVideosCount(int count) {
    videosCount = count;
  }


}