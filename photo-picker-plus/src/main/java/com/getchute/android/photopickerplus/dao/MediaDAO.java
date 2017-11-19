/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2013 Chute
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.getchute.android.photopickerplus.dao;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.getchute.android.photopickerplus.util.AppUtil;

import java.io.File;

/**
 * The definition of the Database Access Objects that handles the reading and
 * writing a class from the database.
 */
public class MediaDAO {

  private static final String TAG = MediaDAO.class.getSimpleName();

  private MediaDAO() {
  }

	/* CAMERA */

  /**
   * Request a specific record in {@link android.provider.MediaStore.Images.Media} database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to camera photos on the
   * device.
   */
  public static Cursor getCameraPhotos(final Context context) {
    final String[] projection = new String[]{MediaStore.Images.Media._ID,
      MediaStore.Images.Media.DATA};
    final Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    final String query = MediaStore.Images.Media.DATA + " LIKE \"%DCIM%\"";
    return context.getContentResolver().query(images, projection, query,
      null, MediaStore.Images.Media.DATE_ADDED + " DESC");
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Images.Thumbnails}
   * database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to camera photos on the
   * device.
   */
  public static Cursor getCameraThumbnails(final Context context) {
    final String[] projection = new String[]{
      MediaStore.Images.Thumbnails._ID,
      MediaStore.Images.Thumbnails.IMAGE_ID,
      MediaStore.Images.Thumbnails.DATA};
    final Uri images = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
    final String query = MediaStore.Images.Thumbnails.DATA
      + " LIKE \"%DCIM%\"";
    return context.getContentResolver().query(images, projection, query,
      null, MediaStore.Images.Thumbnails._ID + " DESC");
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Video.Thumbnails}
   * database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to camera video
   * thumbnails on the device.
   */
  public static Cursor getCameraVideosThumbnails(final Context context) {
    final String[] projection = new String[]{
      MediaStore.Video.Thumbnails._ID,
      MediaStore.Video.Thumbnails.DATA};
    final Uri videos = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
    final String query = MediaStore.Video.Thumbnails.DATA
      + " LIKE \"%DCIM%\"";
    return context.getContentResolver().query(videos, projection, query,
      null, MediaStore.Video.Thumbnails.DEFAULT_SORT_ORDER);
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Video.Media} database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to camera videos on the
   * device.
   */
  public static Cursor getCameraVideos(final Context context) {
    final String[] projection = new String[]{MediaStore.Video.Media._ID,
      MediaStore.Video.Media.DATA};
    final Uri videos = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    final String query = MediaStore.Video.Media.DATA + " LIKE \"%DCIM%\"";
    return context.getContentResolver().query(videos, projection, query,
      null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
  }

	/* ALL MEDIA */

  /**
   * Request a specific record in {@link android.provider.MediaStore.Images.Media} database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to all photos on the
   * device.
   */
  public static Cursor getAllMediaPhotos(final Context context) {
    final String[] projection = new String[]{MediaStore.Images.Media._ID,
      MediaStore.Images.Media.DATA};
    final Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    return context.getContentResolver().query(images, projection, null,
      null, MediaStore.Images.Media.DATE_ADDED + " DESC");
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Images.Thumbnails}
   * database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to all photos on the
   * device.
   */
  public static Cursor getAllMediaThumbnails(final Context context) {
    final String[] projection = new String[]{
      MediaStore.Images.Thumbnails._ID,
      MediaStore.Images.Thumbnails.IMAGE_ID,
      MediaStore.Images.Thumbnails.DATA};
    final Uri images = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
    return context.getContentResolver().query(images, projection, null,
      null, MediaStore.Images.Thumbnails._ID + " DESC");
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Video.Thumbnails}
   * database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to all video thumbnails
   * on the device.
   */
  // TODO
  public static Cursor getAllMediaVideosThumbnails(final Context context) {
    final String[] projection = new String[]{
      MediaStore.Video.Thumbnails._ID,
      MediaStore.Video.Thumbnails.DATA};
    final Uri videos = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
    return context.getContentResolver().query(videos, projection, null,
      null, MediaStore.Video.Thumbnails.DEFAULT_SORT_ORDER);
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Video.Media} database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to all videos on the
   * device.
   */
  public static Cursor getAllMediaVideos(final Context context) {
    final String[] projection = new String[]{MediaStore.Video.Media._ID,
      MediaStore.Video.Media.DATA};
    final Uri videos = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    return context.getContentResolver().query(videos, projection, null,
      null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
  }

	/* LAST MEDIA */

  /**
   * Returns the last photo URI from all photos on the device.
   *
   * @param context The application context.
   * @return The URI for the requested query.
   */
  public static Uri getLastPhotoFromAllPhotos(final Context context) {
    Cursor allMediaPhotos = getAllMediaPhotos(context);
    Uri uri = getFirstImageItemUri(allMediaPhotos);
    safelyCloseCursor(allMediaPhotos);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  /**
   * Returns the last photo thumbnail URI from all photos on the device.
   *
   * @param context The application context.
   * @return The URI for the requested query.
   */
  public static Uri getLastPhotoThumbnailFromAllPhotos(final Context context) {
    Cursor allMediaPhotos = getAllMediaThumbnails(context);
    Uri uri = getFirstImageThumbnailUri(allMediaPhotos);
    safelyCloseCursor(allMediaPhotos);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  /**
   * Returns the last photo URI from the camera photos on the device.
   *
   * @param context The application context.
   * @return The URI for the requested query.
   */
  public static Uri getLastPhotoFromCameraPhotos(final Context context) {
    Cursor cameraPhotos = getCameraPhotos(context);
    Uri uri = getFirstImageItemUri(cameraPhotos);
    safelyCloseCursor(cameraPhotos);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  public static Uri getLastPhotoContentUri(Context context) {
    Cursor cursor = getCameraPhotos(context);
    Uri uri = null;
    if (cursor != null && cursor.moveToFirst()) {
      uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(cursor
        .getColumnIndex(MediaStore.Images.Media._ID)));
    }
    safelyCloseCursor(cursor);
    return uri;
  }

  /**
   * Returns the last photo thumbnail URI from the camera photos on the
   * device.
   *
   * @param context The application context.
   * @return The URI for the requested query.
   */
  public static Uri getLastThumbnailFromCameraPhotos(final Context context) {
    Cursor cameraPhotos = getCameraThumbnails(context);
    Uri uri = getFirstImageThumbnailUri(cameraPhotos);
    safelyCloseCursor(cameraPhotos);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  /**
   * Returns the last video thumbnail URI from all videos on the device.
   *
   * @param context The application context.
   * @return The URI for the requested query.
   */
  public static Uri getLastVideoThumbnailFromAllVideos(final Context context) {
    Cursor allMediaVideos = getAllMediaVideosThumbnails(context);
    Uri uri = getFirstVideoThumbnailUri(allMediaVideos);
    safelyCloseCursor(allMediaVideos);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  /**
   * Returns the last video thumbnail URI from the camera videos on the
   * device.
   *
   * @param context The application context.
   * @return The URI for the requested query.
   */
  public static Uri getLastVideoThumbnailFromCameraVideos(
    final Context context) {
    Cursor cameraVideos = getCameraVideosThumbnails(context);
    Uri uri = getFirstVideoThumbnailUri(cameraVideos);
    safelyCloseCursor(cameraVideos);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  /**
   * Returns the last video URI from the camera videos on the device.
   *
   * @param context The application context.
   * @return The URI for the requested query.
   */
  public static Uri getLastVideoFromCameraVideos(final Context context) {
    Uri uri = null;
    Cursor cursor = getLastVideoCursor(context);
    if (cursor != null && cursor.moveToLast()) {
      uri = Uri.fromFile(new File(cursor.getString(cursor
        .getColumnIndex(MediaStore.Video.Media.DATA))));
    }
    safelyCloseCursor(cursor);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  /**
   * Gets the last video thumbnail path.
   *
   * @param context The application context.
   * @return Last video thumbnail path.
   */
  public static String getLastVideoThumbnailFromCurosr(final Context context) {
    String thumbnail = "";
    Cursor cursor = getLastVideoCursor(context);
    if (cursor != null && cursor.moveToLast()) {
      thumbnail = cursor.getString(cursor
        .getColumnIndex(MediaStore.Video.Media.DATA));
    }
    safelyCloseCursor(cursor);
    return thumbnail;
  }

  //TODO
  public static Uri getLastVideoFromCurosr(final Context context) {
    Uri lastVideo = null;
    Cursor cursor = getLastVideoCursor(context);
    if (cursor != null && cursor.moveToLast()) {
      lastVideo = Uri.fromFile(new File(cursor.getString(cursor
        .getColumnIndex(MediaStore.Video.Media.DATA))));
    }
    safelyCloseCursor(cursor);
    return lastVideo;
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Video.Media} database.
   *
   * @param context The application context.
   * @return Cursor object enabling read-write access to all videos on the
   * device.
   */
  public static Cursor getLastVideoCursor(final Context context) {
    final String[] projection = new String[]{MediaStore.Video.Media._ID,
      MediaStore.Video.Media.DATA};
    final Uri videos = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    final String query = MediaStore.Video.Media.DATA + " LIKE \"%DCIM%\"";
    return context.getContentResolver().query(videos, projection, query,
      null, MediaStore.Video.Media.DATE_TAKEN);
  }

  /**
   * Returns the last video URI from all videos on the device.
   *
   * @param context The application context.
   * @return The URI for the requested query.
   */
  public static Uri getLastVideoFromAllVideos(final Context context) {
    Cursor allVideos = getAllMediaVideos(context);
    Uri uri = getLastVideoItemUri(allVideos);
    safelyCloseCursor(allVideos);
    if (uri == null) {
      return Uri.parse("");
    }
    return uri;
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Video.Thumbnails}
   * database.
   *
   * @param context    The application context.
   * @param dataCursor Cursor object enabling read-write access to videos on the
   *                   device.
   * @param position   Cursor position.
   * @return Path of the video thumbnail.
   */
  public static String getVideoThumbnailFromCursor(final Context context,
                                                   final Cursor dataCursor, int position) {
    String thumbPath = null;
    String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
      MediaStore.Video.Thumbnails.VIDEO_ID};
    if (dataCursor.moveToPosition(position)) {
      int id = dataCursor.getInt(dataCursor
        .getColumnIndex(MediaStore.Video.Media._ID));
      Cursor thumbCursor = context.getContentResolver()
        .query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
          thumbColumns,
          MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id,
          null, null);
      if (thumbCursor.moveToFirst()) {
        thumbPath = thumbCursor.getString(thumbCursor
          .getColumnIndex(MediaStore.Video.Thumbnails.DATA));
      }
      safelyCloseCursor(thumbCursor);
    }
    return thumbPath;
  }

  /**
   * Request a specific record in {@link android.provider.MediaStore.Images.Thumbnails}
   * database.
   *
   * @param context    The application context.
   * @param dataCursor Cursor object enabling read-write access to videos on the
   *                   device.
   * @param position   Cursor position.
   * @return Path of the image associated with the corresponding thumbnail.
   */
  public static String getImagePathFromCursor(final Context context,
                                              final Cursor dataCursor, int position) {
    String imagePath = null;
    if (dataCursor.moveToPosition(position)) {
      int id = dataCursor.getInt(dataCursor.getColumnIndex(MediaStore.Images.Media._ID));
      Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

      Cursor imageCursor = context.getContentResolver().query(uri, null, null, null, null);
      if (imageCursor.moveToFirst()) {
        imagePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
      }
      safelyCloseCursor(imageCursor);
    }
    return imagePath;
  }

  /**
   * Returns the URI of the first item from all photos on the device.
   *
   * @param cursor Cursor object enabling read-write access to all photos on the
   *               device.
   * @return The URI for the requested query.
   */
  private static Uri getFirstImageItemUri(Cursor cursor) {
    if (cursor != null && cursor.moveToFirst()) {
      return Uri.fromFile(new File(cursor.getString(cursor
        .getColumnIndex(MediaStore.Images.Media.DATA))));
    }
    return null;
  }

  /**
   * Returns the URI of the first item from all thumbnails on the device.
   *
   * @param cursor Cursor object enabling read-write access to all photos on the
   *               device.
   * @return The URI for the requested query.
   */
  private static Uri getFirstImageThumbnailUri(Cursor cursor) {
    if (cursor != null && cursor.moveToFirst()) {
      return Uri.fromFile(new File(cursor.getString(cursor
        .getColumnIndex(MediaStore.Images.Thumbnails.DATA))));
    }
    return null;
  }

  /**
   * Returns the URI of the first item from all videos on the device.
   *
   * @param cursor Cursor object enabling read-write access to all videos on the
   *               device.
   * @return The URI for the requested query.
   */
  private static Uri getLastVideoItemUri(Cursor cursor) {
    if (cursor != null && cursor.moveToLast()) {
      return Uri.fromFile(new File(cursor.getString(cursor
        .getColumnIndex(MediaStore.Video.Media.DATA))));
    }
    return null;
  }

  /**
   * Returns the URI of the first item from all video thumbnails on the
   * device.
   *
   * @param cursor Cursor object enabling read-write access to all video
   *               thumbnails on the device.
   * @return The URI for the requested query.
   */
  private static Uri getFirstVideoThumbnailUri(Cursor cursor) {
    if (cursor != null && cursor.moveToFirst()) {
      return Uri.fromFile(new File(cursor.getString(cursor
        .getColumnIndex(MediaStore.Video.Thumbnails.DATA))));
    }
    return null;
  }

  /**
   * Inserts a row into {@link android.provider.MediaStore.Video.Media} table.
   *
   * @param context   The application context.
   * @param videoPath Path of the video file inserted into
   *                  {@link android.provider.MediaStore.Video.Media.Data}.
   * @return The URL of the newly inserted row.
   */
  public static Uri insertVideoInMediaStore(Context context, String videoPath) {
    ContentValues values = new ContentValues();
    String mimeType = "video/".concat(MimeTypeMap
      .getFileExtensionFromUrl(videoPath));
    values.put(MediaStore.Video.Media.MIME_TYPE, mimeType);
    values.put(MediaStore.Video.Media.DATA, videoPath);
    values.put(MediaStore.Video.Media.DATE_TAKEN,
      System.currentTimeMillis());
    return context.getContentResolver().insert(
      MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
  }

  /**
   * Created a thumbnail of the specified image ID.
   *
   * @param context The application context
   * @param cursor  Cursor object enabling read-write access to all videos on the
   *                device used for retrieving the original image ID associated
   *                with the video thumbnail.
   * @return A Bitmap instance. It could be null if the original image
   * associated with origId doesn't exist or memory is not enough.
   */
  public static Bitmap getVideoThumbnail(Context context, Cursor cursor) {
    int id = cursor.getInt(cursor
      .getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID));
    return MediaStore.Video.Thumbnails.getThumbnail(
      context.getContentResolver(), id,
      MediaStore.Video.Thumbnails.MICRO_KIND,
      (BitmapFactory.Options) null);
  }

  /**
   * Gets the image path from the indicated content Uri.
   *
   * @param context    The application context.
   * @param contentUri Image content Uri.
   * @return The value of the requested column as a String.
   */
  public static String getPathFromContentURI(Context context, Uri contentUri) {
    String path = "";
    String[] proj = {MediaStore.Images.Media.DATA};
    Cursor cursor = context.getContentResolver().query(contentUri, proj,
      null, null, null);
    if (cursor != null && cursor.moveToFirst()) {
      path = cursor.getString(cursor
        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
    }

    return path;
  }

  /**
   * Gets the path of the last video thumbnail.
   *
   * @param context The application context.
   * @return The path of the last video thumbnail.
   */
  public static String getLastVideoThumbnail(Context context) {
    String thumbnail = getLastVideoThumbnailFromCurosr(context);
    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(thumbnail,
      MediaStore.Images.Thumbnails.MINI_KIND);
    String bitmapPath = AppUtil.getImagePath(context, bitmap);
    return getPathFromContentURI(context, Uri.parse(bitmapPath));
  }

  /**
   * Closes the Cursor, releasing all of its resources and making it
   * completely invalid.
   *
   * @param c Cursor object enabling random read-write access to the result
   *          set returned by a database query.
   */
  public static void safelyCloseCursor(final Cursor c) {
    try {
      if (c != null) {
        c.close();
      }
    } catch (Exception e) {
      Log.d(TAG, "", e);
    }
  }


}
