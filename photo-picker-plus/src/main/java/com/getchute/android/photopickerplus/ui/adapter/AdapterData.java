package com.getchute.android.photopickerplus.ui.adapter;

import com.getchute.android.photopickerplus.config.PhotoPicker;

public class AdapterData {

  private static AdapterData instance;

  private int count = 0;

  public static AdapterData get() {
    if (instance == null) {
      instance = new AdapterData();
    }
    return instance;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  public int decreaseCounter() {
    return count--;
  }

  public int increaseCounter() {
    return count++;
  }

  public boolean checkIfLimitIsReached() {
    if (PhotoPicker.getInstance().hasLimit()) {
      int limit = PhotoPicker.getInstance().getLimit();
      return (count > limit - 1);
    } else {
      return false;
    }
  }
}
