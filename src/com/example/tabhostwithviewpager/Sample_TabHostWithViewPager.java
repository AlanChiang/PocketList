package com.example.tabhostwithviewpager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.example.tabsviewpagerfragmentactivity.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

public class Sample_TabHostWithViewPager extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	private TabHost myTabHost;
	private ViewPager myViewPager;
	private PagerAdapter myPagerAdapter;
	private HashMap<String, TabInfo> myHashMapTabInfo = new HashMap<String, Sample_TabHostWithViewPager.TabInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragmentactivity);

		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			myTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		this.intialiseViewPager();
	}

	private void intialiseViewPager() {

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, Fragment1.class.getName()));
		fragments.add(Fragment.instantiate(this, Fragment2.class.getName()));
		fragments.add(Fragment.instantiate(this, Fragment3.class.getName()));
		fragments.add(Fragment.instantiate(this, Fragment4.class.getName()));
		fragments.add(Fragment.instantiate(this, Fragment5.class.getName()));
		this.myPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
		this.myViewPager = (ViewPager) super.findViewById(R.id.viewpager);
		this.myViewPager.setAdapter(this.myPagerAdapter);
		this.myViewPager.setOnPageChangeListener(this);
	}

	private void initialiseTabHost(Bundle args) {
		myTabHost = (TabHost) findViewById(android.R.id.tabhost);
		myTabHost.setup();
		TabInfo tabInfo = null;

		ImageView fm_imageView1 = (ImageView) findViewById(R.id.fm_imageView1);

		Sample_TabHostWithViewPager.AddTab(this, this.myTabHost, this.myTabHost.newTabSpec("Tab1").setIndicator("Tab1"),
				(tabInfo = new TabInfo("Tab1", Fragment1.class, args)));
		this.myHashMapTabInfo.put(tabInfo.tag, tabInfo);
		Sample_TabHostWithViewPager.AddTab(this, this.myTabHost, this.myTabHost.newTabSpec("Tab2").setIndicator("Tab2"),
				(tabInfo = new TabInfo("Tab2", Fragment2.class, args)));
		this.myHashMapTabInfo.put(tabInfo.tag, tabInfo);
		Sample_TabHostWithViewPager.AddTab(this, this.myTabHost, this.myTabHost.newTabSpec("Tab3").setIndicator("Tab3"),
				(tabInfo = new TabInfo("Tab3", Fragment3.class, args)));
		this.myHashMapTabInfo.put(tabInfo.tag, tabInfo);
		Sample_TabHostWithViewPager.AddTab(this, this.myTabHost, this.myTabHost.newTabSpec("Tab4").setIndicator("Tab4"),
				(tabInfo = new TabInfo("Tab4", Fragment4.class, args)));
		this.myHashMapTabInfo.put(tabInfo.tag, tabInfo);
		Sample_TabHostWithViewPager.AddTab(this, this.myTabHost, this.myTabHost.newTabSpec("Tab5").setIndicator("Tab5"),
				(tabInfo = new TabInfo("Tab5", Fragment5.class, args)));
		this.myHashMapTabInfo.put(tabInfo.tag, tabInfo);
		myTabHost.setCurrentTab(0);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm); // 先取得螢幕解析度
		int screenWidth = dm.widthPixels; // 取得螢幕的寬

		TabWidget tabWidget = myTabHost.getTabWidget(); // 取得tab的物件
		int count = tabWidget.getChildCount(); // 取得tab的分頁有幾個
		if (count > 4) { // 如果超過三個就來處理滑動
			for (int i = 0; i < count; i++) {
				tabWidget.getChildTabViewAt(i).setMinimumWidth((screenWidth) / 4);// 設定每一個分頁最小的寬度
			}
		}

		//將Tab Text置中
		fm_imageView1.getLayoutParams().height = (this.findViewById(android.R.id.tabs).getLayoutParams().height);
		for (int i = 0; i < count; i++) {
			final View view = myTabHost.getTabWidget().getChildTabViewAt(i);
			if (view != null) {
				view.getLayoutParams().height *= 0.66;

				final View textView = view.findViewById(android.R.id.title);
				if (textView instanceof TextView) {
					((TextView) textView).setGravity(Gravity.CENTER);
					((TextView) textView).setSingleLine(false);
					textView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
				}
			}
		}

		myTabHost.setOnTabChangedListener(this);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		this.myTabHost.setCurrentTab(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onTabChanged(String arg0) {
		int pos = this.myTabHost.getCurrentTab();
		this.myViewPager.setCurrentItem(pos);

	}

	private static void AddTab(Sample_TabHostWithViewPager activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Bundle args;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz, Bundle args) {
			this.tag = tag;
			this.clss = clazz;
			this.args = args;
		}
	}

	class TabFactory implements TabContentFactory {

		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}
	
}
