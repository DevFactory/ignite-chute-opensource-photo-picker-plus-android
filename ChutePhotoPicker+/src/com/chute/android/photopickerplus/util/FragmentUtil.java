package com.chute.android.photopickerplus.util;

import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.chute.android.photopickerplus.R;
import com.chute.android.photopickerplus.models.enums.PhotoFilterType;
import com.chute.android.photopickerplus.ui.fragment.FragmentEmpty;
import com.chute.android.photopickerplus.ui.fragment.FragmentRoot;
import com.chute.android.photopickerplus.ui.fragment.FragmentSingle;
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
