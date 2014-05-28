package luminous.unitconverter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdView;

public class BaseActivity extends Activity {

	private AdView adView;
	private LinearLayout layout;
	Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	public BaseActivity getActivity() {
		return this;
	}

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

	public Context getContext() {
		return getActivity().getApplicationContext();
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

}
