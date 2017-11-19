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
package com.getchute.android.photopickerplus.api.model;


import java.util.List;

/**
 * This class represents a wrapper of {@link AccountAlbumModel} and
 * {@link AccountMediaModel}.
 * <p>
 * {@link AccountBaseModel} is returned every time a specific account call is
 * executed.
 */
public class AccountBaseModel {

	/**
	 * List of media items.
	 * <p>
	 * Besides media items this list can also contain albums.
	 */
	private List<AccountMediaModel> files;

	/**
	 * List of albums
	 * <p>
	 * This list can also contain media items.
	 */
	private List<AccountAlbumModel> folders;

	/**
	 * Getters and setters.
	 */
	public List<AccountMediaModel> getFiles() {
		return files;
	}

	public void setFiles(List<AccountMediaModel> files) {
		this.files = files;
	}

	public List<AccountAlbumModel> getFolders() {
		return folders;
	}

	public void setFolders(List<AccountAlbumModel> folders) {
		this.folders = folders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountBaseModel [files=");
		builder.append(files);
		builder.append(", folders=");
		builder.append(folders);
		builder.append("]");
		return builder.toString();
	}

}
