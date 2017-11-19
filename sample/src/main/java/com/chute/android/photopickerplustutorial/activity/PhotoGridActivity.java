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
package com.chute.android.photopickerplustutorial.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.chute.android.photopickerplustutorial.R;
import com.chute.android.photopickerplustutorial.adapter.GridAdapter;
import com.getchute.android.photopickerplus.api.model.AssetModel;
import java.util.ArrayList;

public class PhotoGridActivity extends FragmentActivity {

  public static final String KEY_VIDEO_PATH = "keyVideoPath";
  private RecyclerView recyclerView;
  private GridAdapter adapter;
  private ArrayList<AssetModel> accountMediaList;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gc_activity_grid);

    recyclerView = (RecyclerView) findViewById(R.id.gcRecyclerViewGrid);
    recyclerView.setHasFixedSize(true);
    accountMediaList = (ArrayList<AssetModel>) getIntent().getExtras().get(PhotoPickerPlusTutorialActivity.KEY_MEDIA_LSIT);
    adapter = new GridAdapter(PhotoGridActivity.this, accountMediaList);
    final GridLayoutManager gridLayoutManager = new GridLayoutManager(PhotoGridActivity.this, getResources().getInteger(com.getchute.android.photopickerplus.R.integer.grid_columns_assets));
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setAdapter(adapter);

  }


}
