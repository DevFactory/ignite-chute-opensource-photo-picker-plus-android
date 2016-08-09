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
package com.getchute.android.photopickerplus.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link MediaModel} class encapsulates data needed for sending POST
 * request to the server.
 *
 * The request should contain {@link OptionsModel} as well as
 * {@link MediaDataModel}. As a result, {@link AssetModel} information is
 * returned which is necessary for delivering a result to the main activity i.e.
 * the activity that started the PhotoPicker.
 */
public class MediaModel {

    private static final String TAG = MediaModel.class.getSimpleName();
    /**
     * {@link OptionsModel} object containing the client ID.
     */
    private OptionsModel options;

    /**
     * List of {@link MediaDataModel} containing asset's image and thumbnail
     * URL.
     */
    private List<MediaDataModel> media = new ArrayList<MediaDataModel>();

    public MediaModel() {
        media = new ArrayList<MediaDataModel>();
    }

    /**
     * Getters and setters
     */
    public OptionsModel getOptions() {
        return options;
    }

    public void setOptions(OptionsModel options) {
        this.options = options;
    }

    public List<MediaDataModel> getMedia() {
        return media;
    }

    public void setMedia(List<MediaDataModel> media) {
        this.media = media;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ImageDataModel [options=");
        builder.append(options);
        builder.append(", media=");
        builder.append(media);
        builder.append("]");
        return builder.toString();
    }
}
