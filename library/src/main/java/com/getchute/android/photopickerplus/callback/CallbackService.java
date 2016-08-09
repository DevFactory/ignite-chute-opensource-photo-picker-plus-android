package com.getchute.android.photopickerplus.callback;

import com.chute.sdk.v2_1.model.response.ResponseModel;
import com.getchute.android.photopickerplus.config.ServiceResponseModel;
import com.getchute.android.photopickerplus.models.MediaModel;
import com.getchute.android.photopickerplus.models.MediaResponseModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

public interface CallbackService {

    @POST("widgets/native") Observable<ResponseModel<MediaResponseModel>> imageData(
            @Body MediaModel mediaModel);

    @GET() Observable<ServiceResponseModel> getConfig(@Url String url);
}
