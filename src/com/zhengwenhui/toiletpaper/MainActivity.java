package com.zhengwenhui.toiletpaper;

import java.util.ArrayList;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewLinstener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private AnimationDrawable mAnimationDrawable;
	private int[][] mAnimResId={
			{
				R.anim.tp1,
				R.anim.tp2,
				R.anim.tp3,
			},
			{
				R.anim.tp1,
				R.anim.tp2,
				R.anim.tp3,
			},
	};
	private int []index ={0,0};

	private Handler finishHandler;
	private boolean mRunAnim = false;
	private SoundPool pool;
	private int sourceid;

	private ViewPager mPager;  
	private ArrayList<View> mTabsView = null;
	private String[] mTitles = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		AdManager.getInstance(this).init("b08105c155a546fd","ec87f4569abdc4c8", false);

		pool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
		sourceid = pool.load(this, R.raw.shizhi, 0);

		mTitles = getResources().getStringArray(R.array.title);

		mPager = (ViewPager)findViewById(R.id.view_pager);
		LayoutInflater layFlater = LayoutInflater.from(this);
		View paperBase = layFlater.inflate(R.layout.layout_base, null);
		addAdView(paperBase);
		View paperObama = layFlater.inflate(R.layout.layout_obama, null);
		addAdView(paperBase);

		mTabsView = new ArrayList<View>();
		mTabsView.add(paperBase);
		mTabsView.add(paperObama);
		mPager.setAdapter(mPagerAdapter);

		mPager.setCurrentItem(0);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toiletpaper02);
		Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.toiletpaper12);
		ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
		bitmaps.add(bitmap2);
		bitmaps.add(bitmap);
		
		
		String path = Environment.getExternalStorageDirectory().getPath()+"/test.gif";
		Log.i("instantiateItem", "path:"+path);
		AnimatedGifEncoder.saveGIF(path, bitmaps);
	}

	/**
	 * 填充ViewPager的数据适配器
	 */
	private final PagerAdapter mPagerAdapter = new PagerAdapter() {  
		public int getCount() {
			return mTabsView.size();
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(mTabsView.get(position));
		}  

		public CharSequence getPageTitle(int position) {
			//重要，用于让PagerTitleStrip显示相关标题
			return mTitles[position];
		}

		@Override  
		public Object instantiateItem(View container, int position) {  
			((ViewPager)container).addView(mTabsView.get(position));
			View view = mTabsView.get(position).findViewById(R.id.base_image);
			onClickButton(view);
			Log.i("instantiateItem", "position:"+position);
			return mTabsView.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	};  

	private void addAdView(View view){
		//实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		//获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)view.findViewById(R.id.adLayout);
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

		int currentItem = mPager.getCurrentItem();
		Log.i("instantiateItem", "currentItem:"+currentItem);

		ImageButton mImageButton = (ImageButton)view;
		mImageButton.setImageResource(mAnimResId[currentItem][index[currentItem]++]);
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

		if(index[currentItem]>=mAnimResId.length){
			index[currentItem] = 0;
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
