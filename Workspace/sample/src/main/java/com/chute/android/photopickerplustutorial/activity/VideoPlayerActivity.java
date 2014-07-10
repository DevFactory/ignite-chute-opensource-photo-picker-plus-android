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

import com.chute.android.photopickerplustutorial.R;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends FragmentActivity {

	private VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gc_activity_video_player);

		String videoUrl = getIntent().getExtras().getString(
				PhotoGridActivity.KEY_VIDEO_PATH);
		initVideo(videoUrl);

	}

	private void initVideo(String url) {
		videoView = (VideoView) findViewById(R.id.videoView);
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);
		Uri uri = Uri.parse(url);
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(uri);
		videoView.requestFocus();
		videoView.start();
	}

}
