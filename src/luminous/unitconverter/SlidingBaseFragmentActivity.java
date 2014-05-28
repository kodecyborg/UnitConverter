package luminous.unitconverter;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdView;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public abstract class SlidingBaseFragmentActivity extends
		SlidingFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	public void initSlidingMenu() {
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();

		setBehindContentView(R.layout.menu_frame);
		sm.setSlidingEnabled(true);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setMode(SlidingMenu.LEFT);

		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);

		sm.setOnOpenListener(new SlidingMenu.OnOpenListener() {

			@Override
			public void onOpen() {

			}
		});

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment(), "menu").commit();
	}

	private AdView adView;
	private LinearLayout layout;
	boolean enableAd = true;

	/**
	 * make enableAd = true for displaying ad otherwise make this flag false.
	 * 
	 * @param resId
	 */
	public void initAd(int resId) {
		if (enableAd) {
			layout = (LinearLayout) findViewById(resId);
			loadAd();
		}

	}

	private void loadAd() {

		adView = new AdView(getActivity());
		adView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
		//Uncomment to show ads
		//adView.setAdUnitId(Constant.addUnitId);

		com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder()
				.build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);
		layout.addView(adView);

	}

	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		// Destroy the AdView.
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	public Context getContext() {
		return this;
	}

	public SlidingBaseFragmentActivity getActivity() {
		return this;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean handled = super.onKeyDown(keyCode, event);

		// Eat the long press event so the keyboard doesn't come up.
		if (keyCode == KeyEvent.KEYCODE_MENU && event.isLongPress()) {
			return true;
		}

		return handled;
	}

	abstract public void onValueSet(String value);

}
