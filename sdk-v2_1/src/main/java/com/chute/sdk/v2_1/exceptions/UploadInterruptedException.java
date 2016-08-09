package com.chute.sdk.v2_1.exceptions;

import java.io.IOException;

public class UploadInterruptedException extends IOException {

  /**
   * 
   */
  private static final long serialVersionUID = -8126304315307705919L;

  public UploadInterruptedException() {
    super();
  }

  public UploadInterruptedException(String message, Throwable cause) {
    super(message, cause);
  }

  public UploadInterruptedException(String message) {
    super(message);
  }

  public UploadInterruptedException(Throwable cause) {
    super(cause);
  }

  
}
