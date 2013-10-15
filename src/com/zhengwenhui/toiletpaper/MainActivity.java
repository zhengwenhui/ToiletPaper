package com.zhengwenhui.toiletpaper;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewLinstener;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
	private SoundPool pool;
	private int sourceid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AdManager.getInstance(this).init("b08105c155a546fd","ec87f4569abdc4c8", false);
		addAdView();

		pool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
		sourceid = pool.load(this, R.raw.shizhi, 0);

		mImageButton = (ImageButton)findViewById(R.id.frame_image);
		onClickButton(mImageButton);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	private void addAdView(){
		//实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		//获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		//将广告条加入到布局中
		adLayout.addView(adView);

		adView.setAdListener(new AdViewLinstener() { 
			@Override
			public void onSwitchedAd(AdView adView) {
				// 切换广告并展示
			}  
			@Override
			public void onReceivedAd(AdView adView) {
				// 请求广告成功
			}
			@Override
			public void onFailedToReceivedAd(AdView adView) {
				// 请求广告失败  
			}
		});
	}

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
		pool.play(sourceid, 1, 1, 0, 0, 1);
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
