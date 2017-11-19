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

public class RestConstants {

  public static String BASE_URL = "https://api.getchute.com/v2/";
  public static String BASE_UPLOAD_URL = "https://upload.getchute.com/";
  public static String BASE_AUTH_URL = "https://getchute.com/v2/oauth/%s/authorize/";
  public static final String URL_AUTHENTICATION_TOKEN = BASE_URL + "/oauth/token/";

  // ALBUMS
  public static final String URL_ALBUMS_ALL = BASE_URL + "albums";
  public static final String URL_ALBUMS_GET = BASE_URL + "albums/%s";
  public static final String URL_ALBUMS_CREATE = BASE_URL + "albums";
  public static final String URL_ALBUMS_UPDATE = BASE_URL + "albums/%s";
  public static final String URL_ALBUMS_DELETE = BASE_URL + "albums/%s";
  public static final String URL_ALBUMS_GET_ASSET = BASE_URL
      + "albums/%s/assets/%s";
  public static final String URL_ALBUMS_GET_ALL_ASSETS = BASE_URL
      + "albums/%s/assets";
  public static final String URL_ALBUMS_ADD_ASSETS = BASE_URL
      + "albums/%s/add_assets";
  public static final String URL_ALBUMS_REMOVE_ASSETS = BASE_URL
      + "albums/%s/remove_assets";
  public static final String URL_ALBUMS_LIST_NESTED_ALBUMS = BASE_URL
      + "albums/%s/albums";

  // ASSETS
  public static final String URL_ASSETS_UPLOAD = BASE_UPLOAD_URL + "assets";
  public static final String URL_ASSETS_ALL = BASE_URL + "assets";
  public static final String URL_ASSETS_GET = BASE_URL + "assets/%s";
  public static final String URL_ASSETS_EXIF = BASE_URL + "albums/%s/assets/%s/exif";
  public static final String URL_ASSETS_UPDATE = BASE_URL + "albums/%s/assets/%s";
  public static final String URL_ASSETS_DELETE = BASE_URL
      + "albums/%s/assets/%s";
  public static final String URL_ASSETS_CAPTION_SET = BASE_UPLOAD_URL
      + "albums/%s/assets/%s";
  public static final String URL_ASSETS_GEO_LOCATION = BASE_URL
      + "albums/%s/assets/%s/geo";
  public static final String URL_ASSETS_GET_GEO_LOCATION = BASE_URL
      + "assets/geo/%s,%s/%s";
  public static final String URL_ASSETS_TAGS = BASE_URL
      + "albums/%s/assets/%s/tags";
  public static final String URL_ASSETS_MOVE = BASE_URL + "albums/%s/assets/%s/move/%s";
  public static final String URL_ASSETS_COPY = BASE_URL + "albums/%s/assets/%s/copy/%s";

  // USERS
  public static final String URL_USERS_GET = BASE_URL + "users/%s";
  public static final String URL_USERS_GET_ALBUMS = BASE_URL
      + "users/%s/albums";
  public static final String URL_USERS_GET_CURRENT = BASE_URL + "me";
  public static final String URL_USERS_UPDATE = BASE_URL + "users/%s";

  // COMMENTS
  public static final String URL_COMMENTS_GET = BASE_URL
      + "albums/%s/assets/%s/comments";
  public static final String URL_COMMENTS_CREATE = BASE_URL
      + "albums/%s/assets/%s/comments";
  public static final String URL_COMMENTS_DELETE = BASE_URL + "comments/%s";

  // IMPORT
  public static final String URL_ALBUMS_IMPORT = BASE_URL
      + "albums/%s/assets/import";
  public static final String URL_ASSETS_IMPORT = BASE_URL + "assets/import";

  // SEARCH
  public static final String URL_SEARCH_EXIF = BASE_URL + "search/exif";
  public static final String URL_SEARCH_LOCATION = BASE_URL
      + "search/location";
  public static final String URL_SEARCH_TAGS = BASE_URL + "search/tags";
  public static final String URL_SEARCH_USER = BASE_URL + "search/user";

  // FLAGS
  public static final String URL_FLAGS_COUNT = BASE_URL
      + "albums/%s/assets/%s/flags";
  public static final String URL_FLAGS_CREATE = BASE_URL
      + "albums/%s/assets/%s/flags";
  public static final String URL_FLAGS_REMOVE = BASE_URL
      + "albums/%s/assets/%s/flags";

  // VOTES
  public static final String URL_VOTES = BASE_URL
      + "albums/%s/assets/%s/votes";

  // HEARTS
  public static final String URL_HEARTS_COUNT = BASE_URL
      + "albums/%s/assets/%s/hearts";
  public static final String URL_HEARTS_CREATE = BASE_URL
      + "albums/%s/assets/%s/hearts";
  public static final String URL_HEARTS_REMOVE = BASE_URL
      + "albums/%s/assets/%s/hearts";

  // PARCELS
  public static final String URL_PARCELS_GET = BASE_URL + "parcels/%s";
  public static final String URL_PARCELS_ALBUMS = BASE_URL
      + "albums/%s/parcels";
  public static final String URL_PARCELS_ASSETS = BASE_URL
      + "parcels/%s/assets";

  // BATCH
  public static final String URL_BATCH_GET = BASE_URL + "batch";

  // UPLOAD
  public static final String URL_UPLOAD_TOKEN = BASE_URL + "uploads";
  public static final String URL_UPLOAD_COMPLETE = BASE_URL
      + "uploads/%s/complete";
  public static final String URL_UPLOAD_FILE = BASE_UPLOAD_URL
      + "v2/albums/%s/assets/upload";
  public static final String URL_UPLOAD_QQFILE = BASE_UPLOAD_URL
	      + "v2/upload";

  // ACCOUNTS
  public static final String BASE_ACCOUNT_URL = "https://accounts.getchute.com/";
  public static final String URL_USER_ACCOUNTS = BASE_URL + "me/accounts";
  public static final String URL_ACCOUNTS_MEMBER = BASE_URL + "accounts/%s";
  public static final String URL_UNLINK_ACCOUNTS = BASE_URL + "me/accounts/%s";

  public static final String URL_ACCOUNT_ALBUMS = BASE_ACCOUNT_URL
      + "v2/accounts/%s/albums";
  public static final String URL_ACCOUNT_MEDIA = BASE_ACCOUNT_URL
      + "v2/accounts/%s/albums/%s/media";

  public static final String URL_ACCOUNT_ROOT = BASE_ACCOUNT_URL + "v2/%s/%s/files";
  public static final String URL_ACCOUNT_SINGLE = BASE_ACCOUNT_URL
      + "v2/%s/%s/folders/%s/files";

}
