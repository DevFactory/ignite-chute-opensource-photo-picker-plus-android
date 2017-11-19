// Copyright (c) 2011, Chute Corporation. All rights reserved.
// 
//  Redistribution and use in source and binary forms, with or without modification, 
//  are permitted provided that the following conditions are met:
// 
//     * Redistributions of source code must retain the above copyright notice, this 
//       list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright notice,
//       this list of conditions and the following disclaimer in the documentation
//       and/or other materials provided with the distribution.
//     * Neither the name of the  Chute Corporation nor the names
//       of its contributors may be used to endorse or promote products derived from
//       this software without specific prior written permission.
// 
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
//  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//  OF THE POSSIBILITY OF SUCH DAMAGE.
// 
package com.getchute.android.photopickerplus.api.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Utils {

  public static final String TAG = Utils.class.getSimpleName();

  private Utils() {
    super();
  }

  public static Bundle decodeUrl(String s) {
    Bundle params = new Bundle();
    if (s != null) {
      s = s.substring(s.indexOf("?") + 1);
      int anchor = s.indexOf("#");
      if (anchor >= 0) {
        s = s.substring(0, anchor);
      }
      String array[] = s.split("&");
      for (String parameter : array) {
        Log.e(TAG, parameter);
        String v[] = parameter.split("=");
        try {
          params.putString(URLDecoder.decode(v[0], "UTF-8"),
              URLDecoder.decode(v[1], "UTF-8"));
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (UnsupportedEncodingException e) {
        }
      }
    }
    return params;
  }

  /**
   * This method is used to modify the media URL to point to a path for a custom
   * sized photo Create wrapper methods that will generate your predefined photo
   * sizes ex. 100x100, 320x320
   * 
   * @param url
   *          the URL that is sent from chute
   * @param height
   *          the required height
   * @param width
   *          the required width
   * @return a modified String for the custom size photo location
   */
  public static String getCustomSizePhotoURL(String url, int height, int width) {
    StringBuilder sb = new StringBuilder(url);
    sb.append("/" + height + "x" + width);
    return sb.toString();
  }

  public static int pixelsFromDp(Context context, int value) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        value, context.getResources().getDisplayMetrics());
  }

  /**
   * Removes BOM from InputStream
   * 
   * @param inputStream
   * @return
   * @throws IOException
   */
  public static InputStream checkForUtf8BOMAndDiscardIfAny(
      InputStream inputStream) throws IOException {
    PushbackInputStream pushbackInputStream = new PushbackInputStream(
        new BufferedInputStream(inputStream), 3);
    byte[] bom = new byte[3];
    if (pushbackInputStream.read(bom) != -1) {
      if (!(bom[0] == (byte) 0xEF && bom[1] == (byte) 0xBB && bom[2] == (byte) 0xBF)) {
        pushbackInputStream.unread(bom);
      }
    }
    return pushbackInputStream;
  }

}
