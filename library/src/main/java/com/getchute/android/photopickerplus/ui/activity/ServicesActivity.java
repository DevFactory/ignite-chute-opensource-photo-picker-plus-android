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
package com.getchute.android.photopickerplus.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.araneaapps.android.libs.logger.ALog;
import com.chute.sdk.v2.api.accounts.CurrentUserAccountsRequest;
import com.chute.sdk.v2.api.accounts.GCAccounts;
import com.chute.sdk.v2.api.authentication.AuthenticationActivity;
import com.chute.sdk.v2.api.authentication.AuthenticationFactory;
import com.chute.sdk.v2.api.authentication.AuthenticationOptions;
import com.chute.sdk.v2.api.authentication.TokenAuthenticationProvider;
import com.chute.sdk.v2.model.AccountModel;
import com.chute.sdk.v2.model.AssetModel;
import com.chute.sdk.v2.model.enums.AccountType;
import com.chute.sdk.v2.model.response.ListResponseModel;
import com.chute.sdk.v2.utils.PreferenceUtil;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.dg.libs.rest.domain.ResponseStatus;
import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.callback.CustomAuthenticationProvider;
import com.getchute.android.photopickerplus.config.PhotoPicker;
import com.getchute.android.photopickerplus.dao.MediaDAO;
import com.getchute.android.photopickerplus.models.DeliverMediaModel;
import com.getchute.android.photopickerplus.models.enums.MediaType;
import com.getchute.android.photopickerplus.models.enums.PhotoFilterType;
import com.getchute.android.photopickerplus.ui.fragment.FragmentRoot;
import com.getchute.android.photopickerplus.ui.fragment.FragmentServices;
import com.getchute.android.photopickerplus.ui.fragment.FragmentSingle;
import com.getchute.android.photopickerplus.ui.listener.ListenerAccountAssetsSelection;
import com.getchute.android.photopickerplus.ui.listener.ListenerFilesAccount;
import com.getchute.android.photopickerplus.ui.listener.ListenerFilesCursor;
import com.getchute.android.photopickerplus.ui.listener.ListenerFragmentRoot;
import com.getchute.android.photopickerplus.ui.listener.ListenerFragmentSingle;
import com.getchute.android.photopickerplus.ui.listener.ListenerImageSelection;
import com.getchute.android.photopickerplus.ui.listener.ListenerVideoSelection;
import com.getchute.android.photopickerplus.util.AppUtil;
import com.getchute.android.photopickerplus.util.AssetUtil;
import com.getchute.android.photopickerplus.util.Constants;
import com.getchute.android.photopickerplus.util.FragmentUtil;
import com.getchute.android.photopickerplus.util.NotificationUtil;
import com.getchute.android.photopickerplus.util.PhotoPickerPreferenceUtil;
import com.getchute.android.photopickerplus.util.intent.IntentUtil;
import com.getchute.android.photopickerplus.util.intent.PhotosIntentWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying the services.
 * <p/>
 * This activity is used to display both local and remote services in a
 * GridView.
 */
public class ServicesActivity extends FragmentActivity implements
        ListenerFilesAccount, ListenerFilesCursor, FragmentServices.ServiceClickedListener,
        ListenerFragmentRoot, ListenerFragmentSingle {

    public static final int LOGOUT_ITEM = 1;

    private static FragmentManager fragmentManager;
    private AccountType accountType;
    private boolean dualPanes;
    private List<Integer> accountItemPositions;
    private List<Integer> imageItemPositions;
    private List<Integer> videoItemPositions;
    private String folderId;
    private AccountModel account;
    private ListenerAccountAssetsSelection listenerAssetsSelection;
    private ListenerImageSelection listenerImagesSelection;
    private ListenerVideoSelection listenerVideosSelection;
    private FragmentSingle fragmentSingle;
    private FragmentRoot fragmentRoot;
    private int photoFilterType;

    public void setAssetsSelectListener(
            ListenerAccountAssetsSelection adapterListener) {
        this.listenerAssetsSelection = adapterListener;
    }

    public void setImagesSelectListener(ListenerImageSelection adapterListener) {
        this.listenerImagesSelection = adapterListener;
    }

    public void setVideosSelectListener(ListenerVideoSelection adapterListener) {
        this.listenerVideosSelection = adapterListener;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.main_layout);

        getActionBar().setTitle(R.string.choose_service);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));


        dualPanes = getResources().getBoolean(R.bool.has_two_panes);

        retrieveValuesFromBundle(savedInstanceState);

        if (dualPanes
                && savedInstanceState == null
                && getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            FragmentUtil.replaceContentWithEmptyFragment(ServicesActivity.this);
        }

    }

    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (PhotoPicker.getInstance().hasLogoutOption()) {
            MenuItem item = menu.add(0, LOGOUT_ITEM, 0,
                    R.string.button_navigation_services_logout);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;

}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == LOGOUT_ITEM) {
            if (getResources().getBoolean(R.bool.has_two_panes)) {
                FragmentUtil
                        .replaceContentWithEmptyFragment(ServicesActivity.this);
            }
            NotificationUtil.makeToast(getApplicationContext(),
                    R.string.toast_signed_out);
            TokenAuthenticationProvider.getInstance().clearAuth();
            PhotoPickerPreferenceUtil.get().clearAll();
        }
        if (item.getItemId() == android.R.id.home) {
            ServicesActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void lastVideo() {
        Uri videoUrl = MediaDAO
                .getLastVideoFromCameraVideos(getApplicationContext());
        String videoThumbnail = MediaDAO
                .getLastVideoThumbnail(getApplicationContext());

        if (videoUrl.toString().equals("")) {
            NotificationUtil.makeToast(getApplicationContext(), getResources()
                    .getString(R.string.no_camera_photos));
        } else {
            final AssetModel model = new AssetModel();
            model.setThumbnail(Uri.fromFile(new File(videoThumbnail))
                    .toString());
            model.setUrl(Uri.fromFile(new File(videoThumbnail)).toString());
            model.setVideoUrl(videoUrl.toString());
            model.setType(MediaType.VIDEO.name().toLowerCase());
            IntentUtil.deliverDataToInitialActivity(ServicesActivity.this,
                    model, null);
        }
    }

    @Override
    public void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Uri uri = AppUtil.getTempVideoFile();
        if (uri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startTheCamera(intent, Constants.CAMERA_VIDEO_REQUEST);

    }

    @Override
    public void takePhoto() {
        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            NotificationUtil.makeToast(getApplicationContext(),
                    R.string.toast_feature_camera);
            return;
        }
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (AppUtil.hasImageCaptureBug() == false) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(AppUtil
                    .getTempImageFile(ServicesActivity.this)));
        } else {
            intent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startTheCamera(intent, Constants.CAMERA_PIC_REQUEST);
    }

    @Override
    public void lastPhoto() {
        Uri uri = MediaDAO
                .getLastPhotoFromCameraPhotos(getApplicationContext());
        if (uri.toString().equals("")) {
            NotificationUtil.makeToast(getApplicationContext(), getResources()
                    .getString(R.string.no_camera_photos));
        } else {
            final AssetModel model = new AssetModel();
            //model.setId(MediaDAO.getLastPhotoContentUri(getApplicationContext()).toString());
            model.setThumbnail(uri.toString());
            model.setUrl(uri.toString());
            model.setType(MediaType.IMAGE.name().toLowerCase());
            IntentUtil.deliverDataToInitialActivity(ServicesActivity.this,
                    model, null);
        }

    }

    @Override
    public void photoStream() {
        photoFilterType = PhotoFilterType.ALL_MEDIA.ordinal();
        accountItemPositions = null;
        imageItemPositions = null;
        videoItemPositions = null;
        if (!dualPanes) {
            final PhotosIntentWrapper wrapper = new PhotosIntentWrapper(
                    ServicesActivity.this);
            wrapper.setFilterType(PhotoFilterType.ALL_MEDIA);
            wrapper.startActivityForResult(ServicesActivity.this,
                    PhotosIntentWrapper.ACTIVITY_FOR_RESULT_STREAM_KEY);
        } else {
            FragmentUtil.replaceContentWithRootFragment(ServicesActivity.this,
                    null, PhotoFilterType.ALL_MEDIA, accountItemPositions,
                    imageItemPositions, videoItemPositions);
        }

    }

    @Override
    public void cameraRoll() {
        photoFilterType = PhotoFilterType.CAMERA_ROLL.ordinal();
        accountItemPositions = null;
        imageItemPositions = null;
        videoItemPositions = null;
        if (!dualPanes) {
            final PhotosIntentWrapper wrapper = new PhotosIntentWrapper(
                    ServicesActivity.this);
            wrapper.setFilterType(PhotoFilterType.CAMERA_ROLL);
            wrapper.startActivityForResult(ServicesActivity.this,
                    PhotosIntentWrapper.ACTIVITY_FOR_RESULT_STREAM_KEY);
        } else {
            FragmentUtil.replaceContentWithRootFragment(ServicesActivity.this,
                    null, PhotoFilterType.CAMERA_ROLL, accountItemPositions,
                    imageItemPositions, videoItemPositions);
        }

    }

    public void accountClicked(AccountModel account, AccountType accountType) {
        PhotoPickerPreferenceUtil.get().setAccountType(accountType);
        photoFilterType = PhotoFilterType.SOCIAL_MEDIA.ordinal();
        accountItemPositions = null;
        imageItemPositions = null;
        videoItemPositions = null;
        this.account = account;
        if (!dualPanes) {
            final PhotosIntentWrapper wrapper = new PhotosIntentWrapper(
                    ServicesActivity.this);
            wrapper.setFilterType(PhotoFilterType.SOCIAL_MEDIA);
            wrapper.setAccount(account);
            wrapper.startActivityForResult(ServicesActivity.this,
                    PhotosIntentWrapper.ACTIVITY_FOR_RESULT_STREAM_KEY);
        } else {
            FragmentUtil.replaceContentWithRootFragment(ServicesActivity.this,
                    account, PhotoFilterType.SOCIAL_MEDIA,
                    accountItemPositions, imageItemPositions,
                    videoItemPositions);
        }

    }

    @Override
    public void accountLogin(AccountType type) {
        accountType = type;
        PhotoPickerPreferenceUtil.get().setAccountType(accountType);
        if (PreferenceUtil.get().hasAccount(type.getLoginMethod())) {
            AccountModel account = PreferenceUtil.get().getAccount(
                    type.getLoginMethod());
            accountClicked(account, accountType);
        } else {
            AuthenticationFactory.getInstance().startAuthenticationActivity(
                    ServicesActivity.this,
                    accountType,
                    new AuthenticationOptions.Builder()
                            .setClearCookiesForAccount(false)
                            .setShouldRetainSession(false).build()
            );
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK
                && resultCode != AuthenticationActivity.RESULT_DIFFERENT_CHUTE_USER_AUTHENTICATED) {
            return;
        }
        if (requestCode == AuthenticationFactory.AUTHENTICATION_REQUEST_CODE) {
            if (data != null) {
                String newSessionToken = data
                        .getExtras()
                        .getString(
                                AuthenticationActivity.INTENT_DIFFERENT_CHUTE_USER_TOKEN);
                String previousSessionToken = TokenAuthenticationProvider
                        .getInstance().getToken();
                if (!newSessionToken.equals(previousSessionToken)) {
                    CurrentUserAccountsRequest request = new CurrentUserAccountsRequest(new AccountsCallback());
                    request.getClient().setAuthentication(
                            new CustomAuthenticationProvider(newSessionToken));
                    request.executeAsync();
                }
            } else {
                GCAccounts.allUserAccounts(new AccountsCallback()).executeAsync();
            }
            return;
        }

        if (requestCode == PhotosIntentWrapper.ACTIVITY_FOR_RESULT_STREAM_KEY) {
            finish();
            return;
        }
        if (requestCode == Constants.CAMERA_PIC_REQUEST) {
            String path = "";
            Uri uri = null;
            File tempFile = AppUtil.getTempImageFile(getApplicationContext());
            if (AppUtil.hasImageCaptureBug() == false && tempFile.length() > 0) {
                try {
                    uri = Uri.parse(MediaStore.Images.Media.insertImage(
                            getContentResolver(), tempFile.getAbsolutePath(),
                            null, null));
                    tempFile.delete();

                    path = MediaDAO.getLastPhotoFromCameraPhotos(
                            getApplicationContext()).toString();
                } catch (FileNotFoundException e) {
                    ALog.d("", e);
                }
            } else {
                uri = data.getData();
                ALog.e("Bug " + data.getData().getPath());
                path = Uri.fromFile(
                        new File(AppUtil.getPath(getApplicationContext(),
                                data.getData()))
                ).toString();
            }
            final AssetModel model = new AssetModel();
            if(uri!=null ) {
              model.setId(uri.toString());
            }
            model.setThumbnail(path);
            model.setUrl(path);
            model.setType(MediaType.IMAGE.name().toLowerCase());
            IntentUtil.deliverDataToInitialActivity(ServicesActivity.this,
                    model, null);
        }
        if (requestCode == Constants.CAMERA_VIDEO_REQUEST) {
            Uri uriVideo = data.getData();
            File file = new File(uriVideo.getPath());
            MediaDAO.insertVideoInMediaStore(getApplicationContext(),
                    file.getAbsolutePath());

            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(
                    file.getAbsolutePath(),
                    MediaStore.Images.Thumbnails.MINI_KIND);

            final AssetModel model = new AssetModel();
            model.setThumbnail(AppUtil.getImagePath(getApplicationContext(),
                    thumbnail));
            model.setVideoUrl(uriVideo.toString());
            model.setUrl(AppUtil.getImagePath(getApplicationContext(),
                    thumbnail));
            model.setType(MediaType.VIDEO.name().toLowerCase());
            IntentUtil.deliverDataToInitialActivity(ServicesActivity.this,
                    model, null);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setResult(Activity.RESULT_OK,
                new Intent().putExtras(intent.getExtras()));
        ServicesActivity.this.finish();
    }

    @Override
    public void onDeliverAccountFiles(ArrayList<AssetModel> assetList,
                                      AccountModel accountModel) {
        IntentUtil.deliverDataToInitialActivity(ServicesActivity.this,
                assetList, accountModel);

    }

    @Override
    public void onDeliverCursorAssets(List<DeliverMediaModel> deliverList) {
        IntentUtil.deliverDataToInitialActivity(ServicesActivity.this,
                AssetUtil.getPhotoCollection(deliverList), null);

    }

    @Override
    public void onAccountFilesSelect(AssetModel assetModel,
                                     AccountModel accountModel) {
        IntentUtil.deliverDataToInitialActivity(ServicesActivity.this,
                assetModel, accountModel);
    }

    @Override
    public void onCursorAssetsSelect(AssetModel assetModel) {
        IntentUtil.deliverDataToInitialActivity(ServicesActivity.this,
                assetModel, null);
    }

    @Override
    public void onAccountFolderSelect(AccountModel account, String folderId) {
        accountItemPositions = null;
        imageItemPositions = null;
        videoItemPositions = null;
        photoFilterType = PhotoFilterType.SOCIAL_MEDIA.ordinal();
        this.folderId = folderId;
        this.account = account;
        FragmentUtil.replaceContentWithSingleFragment(ServicesActivity.this,
                account, folderId, accountItemPositions);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.KEY_FOLDER_ID, folderId);
        outState.putParcelable(Constants.KEY_ACCOUNT, account);
        outState.putInt(Constants.KEY_PHOTO_FILTER_TYPE, photoFilterType);
        List<Integer> accountPositions = new ArrayList<Integer>();
        List<Integer> imagePaths = new ArrayList<Integer>();
        List<Integer> videoPaths = new ArrayList<Integer>();
        if (listenerAssetsSelection != null
                && listenerAssetsSelection.getSocialPhotosSelection() != null) {
            accountPositions.addAll(listenerAssetsSelection
                    .getSocialPhotosSelection());
            outState.putIntegerArrayList(Constants.KEY_SELECTED_ACCOUNTS_ITEMS,
                    (ArrayList<Integer>) accountPositions);
        }
        if (listenerImagesSelection != null
                && listenerImagesSelection.getCursorImagesSelection() != null) {
            imagePaths.addAll(listenerImagesSelection
                    .getCursorImagesSelection());
            outState.putIntegerArrayList(Constants.KEY_SELECTED_IMAGES_ITEMS,
                    (ArrayList<Integer>) imagePaths);
        }
        if (listenerVideosSelection != null
                && listenerVideosSelection.getCursorVideosSelection() != null) {
            videoPaths.addAll(listenerVideosSelection
                    .getCursorVideosSelection());
            outState.putIntegerArrayList(Constants.KEY_SELECTED_VIDEOS_ITEMS,
                    (ArrayList<Integer>) videoPaths);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragmentSingle = (FragmentSingle) getSupportFragmentManager()
                .findFragmentByTag(FragmentUtil.TAG_FRAGMENT_FILES);
        fragmentRoot = (FragmentRoot) getSupportFragmentManager()
                .findFragmentByTag(FragmentUtil.TAG_FRAGMENT_FOLDER);
        if (fragmentSingle != null
                && photoFilterType == PhotoFilterType.SOCIAL_MEDIA.ordinal()) {
            fragmentSingle.updateFragment(account, folderId,
                    accountItemPositions);
        }
        if (fragmentRoot != null) {
            fragmentRoot.updateFragment(account,
                    PhotoFilterType.values()[photoFilterType],
                    accountItemPositions, imageItemPositions,
                    videoItemPositions);
        }
    }

    private void retrieveValuesFromBundle(Bundle savedInstanceState) {
        accountItemPositions = savedInstanceState != null ? savedInstanceState
                .getIntegerArrayList(Constants.KEY_SELECTED_ACCOUNTS_ITEMS)
                : null;

        imageItemPositions = savedInstanceState != null ? savedInstanceState
                .getIntegerArrayList(Constants.KEY_SELECTED_IMAGES_ITEMS)
                : null;

        videoItemPositions = savedInstanceState != null ? savedInstanceState
                .getIntegerArrayList(Constants.KEY_SELECTED_VIDEOS_ITEMS)
                : null;

        folderId = savedInstanceState != null ? savedInstanceState
                .getString(Constants.KEY_FOLDER_ID) : null;

        account = (AccountModel) (savedInstanceState != null ? savedInstanceState
                .getParcelable(Constants.KEY_ACCOUNT) : null);

        photoFilterType = savedInstanceState != null ? savedInstanceState
                .getInt(Constants.KEY_PHOTO_FILTER_TYPE) : 0;

    }

    @Override
    public void onDestroy() {
        Fragment fragmentFolder = fragmentManager
                .findFragmentByTag(FragmentUtil.TAG_FRAGMENT_FOLDER);
        Fragment fragmentFiles = fragmentManager
                .findFragmentByTag(FragmentUtil.TAG_FRAGMENT_FILES);
        if (fragmentFolder != null && fragmentFolder.isResumed()) {
            fragmentManager.beginTransaction().remove(fragmentFolder).commit();
        }
        if (fragmentFiles != null && fragmentFiles.isResumed()) {
            fragmentManager.beginTransaction().remove(fragmentFiles).commit();
        }
        super.onDestroy();
    }

    @Override
    public void onSessionExpired(AccountType accountType) {
        PhotoPickerPreferenceUtil.get().setAccountType(accountType);
        AuthenticationFactory.getInstance().startAuthenticationActivity(
                ServicesActivity.this,
                accountType,
                new AuthenticationOptions.Builder()
                        .setShouldRetainSession(true).build()
        );
    }

    @Override
    public void onBackPressed() {
        fragmentRoot = (FragmentRoot) getSupportFragmentManager()
                .findFragmentByTag(FragmentUtil.TAG_FRAGMENT_FOLDER);
        fragmentSingle = (FragmentSingle) getSupportFragmentManager()
                .findFragmentByTag(FragmentUtil.TAG_FRAGMENT_FILES);
        if (fragmentRoot != null && fragmentRoot.isVisible()) {
            this.finish();
        } else {
            super.onBackPressed();
        }

    }

    private final class AccountsCallback implements
            HttpCallback<ListResponseModel<AccountModel>> {

        @Override
        public void onSuccess(ListResponseModel<AccountModel> responseData, ResponseStatus status) {
            if (accountType == null) {
                accountType = PhotoPickerPreferenceUtil.get().getAccountType();
            }
            if (responseData.getData().size() == 0) {
                NotificationUtil.makeToast(getApplicationContext(),
                        R.string.no_albums_found);
                return;
            }
            for (AccountModel accountModel : responseData.getData()) {
                if (accountModel.getType().equals(accountType.getLoginMethod())) {
                    PreferenceUtil.get().saveAccount(accountModel);
                    accountClicked(accountModel, accountType);
                }
            }

        }

        @Override
        public void onHttpError(ResponseStatus responseStatus) {
            ALog.d("Http Error: " + responseStatus.getStatusCode() + " "
                    + responseStatus.getStatusMessage());
        }

    }


    private void startTheCamera(Intent intent, int requestCode) {
        try {
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            ALog.w("Could not start the camera. Memory is full.");
            NotificationUtil.makeToast(getApplicationContext(),
                    R.string.toast_memory_full);
        }
    }

    @Override
    public void onFragmentRootNavigationBack() {
        FragmentUtil.replaceContentWithEmptyFragment(ServicesActivity.this);
    }

    @Override
    public void onFragmentSingleNavigationBack() {
        FragmentUtil.replaceContentWithRootFragment(ServicesActivity.this,
                account, PhotoFilterType.SOCIAL_MEDIA, accountItemPositions,
                imageItemPositions, videoItemPositions);

    }

}