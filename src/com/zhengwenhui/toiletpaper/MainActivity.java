package com.zhengwenhui.toiletpaper;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private ImageButton mImageButton;
	private AnimationDrawable mAnimationDrawable;
	private int[] mAnimResId={
			R.anim.tp1,
			R.anim.tp2,
			R.anim.tp3,
	};
	private int index =0;
	private Handler finishHandler;
	private boolean mRunAnim = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImageButton = (ImageButton)findViewById(R.id.frame_image);
		onClickButton(mImageButton);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	public void onClickButton(View view){
		if(mRunAnim){
			return;
		}
		mImageButton.setImageResource(mAnimResId[index++]);
		mAnimationDrawable = (AnimationDrawable) mImageButton.getDrawable();  

		if(mAnimationDrawable.isRunning()){
			mAnimationDrawable.stop();
		}
		mAnimationDrawable.start();
		mRunAnim = true;
		finishHandler = new Handler();
		finishHandler.postDelayed(
				new Runnable() {
					public void run() {
						mRunAnim = false;
					}
				}, getTotalDuration(mAnimationDrawable));

		if(index>=mAnimResId.length){
			index = 0;
		}
	}

	public int getTotalDuration(AnimationDrawable ad) {
		int durationTime = 0;
		for (int i = 0; i < ad.getNumberOfFrames(); i++) {
			durationTime += ad.getDuration(i);
		}
		return durationTime;
	}
}
