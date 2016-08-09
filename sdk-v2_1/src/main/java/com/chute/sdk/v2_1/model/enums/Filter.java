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
package com.chute.sdk.v2_1.model.enums;

/**
 * The {@link Filter} class is a wrapper around certain parameters needed for
 * filtering and ordering assets.
 * 
 */
public class Filter {

  /**
   * Username of the user whose assets are listed
   */
  private String username;
  /**
   * Name of the service. Supported services: Chute, Twitter, Instagram, Facebook,
   * Flickr, Picasa, Google, GoogleDrive, SkyDrive, Dropbox.
   */
  private AccountType accountType;

  /**
   * Getters and setters
   */
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  /**
   * If the user chooses service listed in {@link AccountType} that is not
   * supported, new Runtime Exception is thrown.
   * 
   * @param accountType
   *          - Name of the service
   */
  public void setAccountType(AccountType accountType) {
        if (accountType.equals(AccountType.FOURSQUARE)) {
      throw new RuntimeException("Account type not supported!");
    } else {
      this.accountType = accountType;
    }
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Filter [username=");
    builder.append(username);
    builder.append(", accountType=");
    builder.append(accountType);
    builder.append("]");
    return builder.toString();
  }

}
