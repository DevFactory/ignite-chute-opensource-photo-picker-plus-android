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
package com.getchute.android.photopickerplus.callback;

import com.chute.sdk.v2.utils.MediaTypes;
import com.dg.libs.rest.client.RequestMethod;
import com.dg.libs.rest.requests.RestClientRequest;
import com.getchute.android.photopickerplus.models.MediaModel;
import com.getchute.android.photopickerplus.models.MediaResponseModel;
import com.getchute.android.photopickerplus.util.Constants;
import com.chute.sdk.v2.api.parsers.ResponseParser;
import com.chute.sdk.v2.model.response.ResponseModel;
import com.dg.libs.rest.callbacks.HttpCallback;
import com.squareup.okhttp.RequestBody;

/**
 * The {@link ImageDataRequest} class is used for exchanging messages with the server
 * (request-response). It uses the {@link RestClientRequest}
 * implementation.
 * 
 */
public class ImageDataRequest extends
	RestClientRequest<ResponseModel<MediaResponseModel>> {

	public ImageDataRequest(MediaModel imageData,
			HttpCallback<ResponseModel<MediaResponseModel>> callback) {
		if (imageData == null) {
			throw new IllegalArgumentException("Need to provide image data");
		}

		setRequestMethod(RequestMethod.POST, RequestBody.create(MediaTypes.JSON, bodyContents(imageData)));
		setUrl(Constants.SELECTED_IMAGES_URL);
		setCallback(callback);
		setParser(new ResponseParser<MediaResponseModel>(MediaResponseModel.class));
	}

	public String bodyContents(MediaModel imageData) {
		return imageData.serializeImageDataModel();
	}


}
