package com.zhengwenhui.toiletpaper;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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
		mImageButton.setImageResource(mAnimResId[index++]);
		mAnimationDrawable = (AnimationDrawable) mImageButton.getDrawable();  
		if(mAnimationDrawable.isRunning()){
			mAnimationDrawable.stop();
		}
		mAnimationDrawable.start();
		
		if(index>=mAnimResId.length){
			index = 0;
		}
	}
}
