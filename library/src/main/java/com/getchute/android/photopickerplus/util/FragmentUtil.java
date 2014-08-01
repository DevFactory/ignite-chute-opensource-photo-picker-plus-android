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
package com.getchute.android.photopickerplus.util;

import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.getchute.android.photopickerplus.R;
import com.getchute.android.photopickerplus.models.enums.PhotoFilterType;
import com.getchute.android.photopickerplus.ui.fragment.FragmentEmpty;
import com.getchute.android.photopickerplus.ui.fragment.FragmentRoot;
import com.getchute.android.photopickerplus.ui.fragment.FragmentSingle;
import com.chute.sdk.v2.model.AccountModel;

public class FragmentUtil {

	public static final String TAG_FRAGMENT_FOLDER = "FolderFrag";
	public static final String TAG_FRAGMENT_FILES = "FilesFrag";
	public static final String TAG_FRAGMENT_EMPTY = "EmptyFrag";

	public static void replaceContentWithSingleFragment(FragmentActivity activity,
			AccountModel account, String folderId,
			List<Integer> selectedItemPositions) {
		FragmentTransaction fragmentTransaction = activity
				.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.gcFragments, FragmentSingle
				.newInstance(account, folderId, selectedItemPositions),
				TAG_FRAGMENT_FILES);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}

	public static void replaceContentWithRootFragment(FragmentActivity activity,
			AccountModel account, PhotoFilterType filterType,
			List<Integer> accountItemPositions,
			List<Integer> imageItemPositions, List<Integer> videoItemPositions) {
		FragmentTransaction fragmentTransaction = activity
				.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.gcFragments, FragmentRoot.newInstance(
      account, filterType, accountItemPositions, imageItemPositions,
      videoItemPositions), TAG_FRAGMENT_FOLDER);
		fragmentTransaction.commit();
	}

	public static void replaceContentWithEmptyFragment(FragmentActivity activity) {
		FragmentTransaction fragmentTransaction = activity
				.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.gcFragments,
				FragmentEmpty.newInstance(), TAG_FRAGMENT_EMPTY);
		fragmentTransaction.commit();
	}

}
