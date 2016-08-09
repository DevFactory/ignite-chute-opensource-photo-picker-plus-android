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
package com.getchute.android.photopickerplus.ui.listener;

import com.chute.sdk.v2_1.model.AssetModel;
import com.getchute.android.photopickerplus.models.DeliverMediaModel;
import java.util.List;

/**
 * This interface is implemented by {@link com.getchute.android.photopickerplus.ui.activity.AssetActivity} and
 * {@link com.getchute.android.photopickerplus.ui.activity.ServicesActivity}.
 * 
 */
public interface ListenerFilesCursor {

  /**
   * Delivers {@link AssetModel} to the main activity when media item from a local
   * service is selected.
   * 
   * @param assetModel
   *          The {@link AssetModel} delivered to the main activity i.e. the
   *          activity that started the PhotoPicker.
   */
  public void onCursorAssetsSelect(AssetModel assetModel);

  /**
   * Delivers a list of {@link DeliverMediaModel}s to the main activity when media items
   * from a local service are selected.
   * 
   * @param deliverList
   *          List of selected items wrapped in {@link DeliverMediaModel} delivered to the main activity i.e.
   *          the activity that started the PhotoPicker.
   */
  public void onDeliverCursorAssets(List<DeliverMediaModel> deliverList);
  

}
