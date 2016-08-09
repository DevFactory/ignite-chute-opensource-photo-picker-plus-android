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
package com.chute.sdk.v2_1.utils;

import java.io.UnsupportedEncodingException;

import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;

public class TextUtil {

	@SuppressWarnings("unused")
	private static final String TAG = TextUtil.class.getSimpleName();
	public static final String ENCODING_CHARSET = "UTF-8";

	private TextUtil() {
	}

	public static String truncate(String value, int length) {
		if (value != null && value.length() > length) {
			value = value.substring(0, length);
			value += "...";
		}
		return value;
	}

	public static String urlEncode(String string) {
		if (string == null)
			return null;
		try {
			return encode(string, ENCODING_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Platform doesn't support "
					+ ENCODING_CHARSET, e);
		}
	}

	public static String urlDecode(String string) {
		if (string == null)
			return null;
		try {
			return decode(string, ENCODING_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Platform doesn't support "
					+ ENCODING_CHARSET, e);
		}
	}

	/**
	 * Is {@code string} blank (null or only whitespace)?
	 * 
	 * @param string
	 *            The string to check.
	 * @return {@code true} if {@code string} is blank, {@code false} otherwise.
	 */
	public static boolean isBlank(String string) {
		return string == null || "".equals(string.trim());
	}

	/**
	 * Returns a trimmed version of {@code string}, or an empty string if
	 * {@code string} is {@code null} or the trimmed version is a blank string.
	 * 
	 * @param string
	 *            The string to trim.
	 * @return A trimmed version of {@code string}, or an empty string if
	 *         {@code string} is {@code null} or the trimmed version is a blank
	 *         string.
	 */
	public static String trimToEmpty(String string) {
		if (isBlank(string))
			return "";
		return string.trim();
	}

}
