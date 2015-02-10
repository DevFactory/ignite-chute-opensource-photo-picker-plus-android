package com.getchute.android.photopickerplus.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.getchute.android.photopickerplus.R;

/**
 * Created by Olga on 2/9/15.
 */
public abstract class BaseActivity extends ActionBarActivity {

  protected abstract int getLayoutResource();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResource());

    View toolBarLayout = findViewById(R.id.gcToolbarLayout);
    Toolbar toolBar = (Toolbar) toolBarLayout.findViewById(R.id.gcToolBar);
    if (toolBar != null) {
      setSupportActionBar(toolBar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setIcon(new ColorDrawable(android.R.color.transparent));
      getSupportActionBar().setTitle(R.string.choose_service);
    }
  }
}
