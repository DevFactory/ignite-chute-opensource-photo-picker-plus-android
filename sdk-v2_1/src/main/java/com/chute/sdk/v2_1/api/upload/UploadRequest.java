package com.chute.sdk.v2_1.api.upload;

import android.util.Log;
import com.chute.sdk.v2_1.api.Chute;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UploadRequest implements CountingFileRequestBody.ProgressListener {

    private static final String MULTIPART_ENTITY_KEY_FILEDATA = "filedata";
    private static final String MULTIPART_ENTITY_KEY_QQFILE = "qqfile";
    private static final String THUMBNAIL_SIZE = "200x200";

    private final UploadProgressListener uploadListener;
    private final File file;
    private final CountingFileRequestBody body;
    private final String albumId;
    private final RequestBody description;
    private final MultipartBody.Part requestBody;
    private final String clientId;

    public UploadRequest(String clientId, String albumId, String filePath,
            final UploadProgressListener uploadListener) {
        this.uploadListener = uploadListener;
        this.file = new File(filePath);
        this.albumId = albumId;
        this.clientId = clientId;

        this.body = new CountingFileRequestBody(file, MediaType.parse("application/octet-stream"),
                this);
        this.requestBody =
                MultipartBody.Part.createFormData(MULTIPART_ENTITY_KEY_QQFILE, file.getName(),
                        body);

        String descriptionString = "hello, this is description speaking";
        this.description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        if (uploadListener != null) {
            uploadListener.onUploadStarted(file);
        }
    }

    public void executeRequest() {
        Call<ResponseBody> call = Chute.getUploadService().uploadAsset(description, requestBody, clientId, THUMBNAIL_SIZE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                    retrofit2.Response<ResponseBody> response) {
                Log.v("Upload", "success: " + response.message());
                if (uploadListener != null) {
                    uploadListener.onUploadFinished(file);
                }
            }

            @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                if (uploadListener != null) {
                    uploadListener.onUploadFinished(file);
                }
            }
        });
    }

    public void cancel() {
        body.cancel();
    }

    @Override
    public void transferred(long num) {
        uploadListener.onProgress(file.length(), num);
    }
}
