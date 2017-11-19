/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Chute
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.getchute.android.photopickerplus.callback;

import android.content.Context;
import android.util.Log;
import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.api.authentication.AuthenticationFactory;
import com.getchute.android.photopickerplus.api.model.AccountMediaModel;
import com.getchute.android.photopickerplus.api.model.AccountModel;
import com.getchute.android.photopickerplus.api.model.AssetModel;
import com.getchute.android.photopickerplus.api.model.response.ResponseModel;
import com.getchute.android.photopickerplus.models.MediaDataModel;
import com.getchute.android.photopickerplus.models.MediaModel;
import com.getchute.android.photopickerplus.models.MediaResponseModel;
import com.getchute.android.photopickerplus.models.OptionsModel;
import com.getchute.android.photopickerplus.models.enums.MediaType;
import com.getchute.android.photopickerplus.ui.listener.ListenerFilesAccount;
import com.getchute.android.photopickerplus.util.NotificationUtil;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

/**
 * The {@link ImageDataResponseLoader} has the responsibility for uploading the
 * selected media URL(s) and delivering the retrieved data to the main activity
 * i.e. the activity that started the PhotoPicker.
 *
 * This class consists exclusively of static methods and is used only with media
 * from social accounts.
 *
 */
public class ImageDataResponseLoader {

    public static final String TAG = ImageDataResponseLoader.class.getSimpleName();

    public static void postImageData(Context context,
            ArrayList<AccountMediaModel> selectedImages,
            ListenerFilesAccount accountListener, AccountModel accountModel) {

        ArrayList<MediaDataModel> mediaModelList = new ArrayList<MediaDataModel>();
        for (AccountMediaModel accountMediaModel : selectedImages) {
            MediaDataModel mediaModel = new MediaDataModel();
            if (accountMediaModel.getVideoUrl() != null) {
                mediaModel.setFileType(MediaType.VIDEO.name().toLowerCase());
            } else {
                mediaModel.setFileType(MediaType.IMAGE.name().toLowerCase());
            }
            mediaModel.setVideoUrl(accountMediaModel.getVideoUrl());
            mediaModel.setImageUrl(accountMediaModel.getImageUrl());
            mediaModel.setThumbnail(accountMediaModel.getThumbnail());
            mediaModelList.add(mediaModel);
        }

        OptionsModel options = new OptionsModel();
        options.setClientId(AuthenticationFactory.getInstance().getAuthConstants().clientId);
        MediaModel imageDataModel = new MediaModel();
        imageDataModel.setOptions(options);
        imageDataModel.setMedia(mediaModelList);

        getImageData(context, imageDataModel,
                accountListener, accountModel);
    }

    private static void getImageData(final Context context, final MediaModel mediaModel,
            final ListenerFilesAccount listener, final AccountModel accountModel) {
        PhotoPickerPlusService.getCallbackService()
                .imageData(mediaModel)
                .subscribe(new Subscriber<ResponseModel<MediaResponseModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        NotificationUtil.makeToast(context, R.string.general_error);
                        Log.d(TAG, "Http Error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(
                            ResponseModel<MediaResponseModel> mediaResponseModelResponseModel) {
                        if (mediaResponseModelResponseModel.getData() != null) {
                            List<AssetModel> assetList = mediaResponseModelResponseModel.getData()
                                    .getMedia();
                            listener.onDeliverAccountFiles(assetList, accountModel);
                        }
                    }
                });
    }
}
