package com.getchute.android.photopickerplus.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Olga on 2/9/15.
 */
public class ActionBarFragment extends Fragment {

  private ActionBarActivity actionBarActivity;

  public ActionBarActivity getActionBarActivity() {
    return actionBarActivity;
  }

  @Override
  public void onAttach(Activity activity) {
    if (!(activity instanceof ActionBarActivity)) {
      throw new IllegalArgumentException(getClass().getSimpleName() + "must be attached to an ActionBarActivity");
    }
    actionBarActivity = (ActionBarActivity) activity;
    super.onAttach(activity);
  }

  @Override
  public void onDetach() {
    actionBarActivity = null;
    super.onDetach();
  }
}
