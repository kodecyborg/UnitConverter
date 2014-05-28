package luminous.unitconverter;

import java.util.ArrayList;

import luminous.unitconverter.MenuFragment.MenuItem;
import luminous.unitconverter.adapter.FavConverterAdapter;
import luminous.unitconverter.utils.Utils;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FavUnitActivity extends BaseActivity {

	ListView lvUnitConverter;
	FavConverterAdapter mAdapter;
	ArrayList<FavUnit> unitList = new ArrayList<FavUnit>();
	ImageView chkSelectAllFav;

	String unit_type;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.unit_fav);

		if (initIntentParams()) {
			init();
			//Uncomment to show ads
			//initAd(R.id.adLayout);	
		}
	}

	private boolean initIntentParams() {
		if (getIntent().getExtras() != null) {

			if (getIntent().getExtras().containsKey("unit_type")) {
				unit_type = getIntent().getStringExtra("unit_type");
			} else {
				return false;
			}

			if (getIntent().getExtras().containsKey("units")) {

				String units = getIntent().getStringExtra("units");
				ArrayList<String> unitL = new Gson().fromJson(units,
						new TypeToken<ArrayList<String>>() {
						}.getType());

				if (unitL != null && !unitL.isEmpty()) {
					ArrayList<String> blackList = null;

					if (unit_type.equalsIgnoreCase(MenuItem.AREA)) {
						blackList = Utils.getBlackListUnitArea(getActivity());
					} else if (unit_type.equalsIgnoreCase(MenuItem.LENGTH)) {
						blackList = Utils.getBlackListUnitLength(getActivity());
					} else if (unit_type.equalsIgnoreCase(MenuItem.VOLUME)) {
						blackList = Utils.getBlackListUnitVol(getActivity());
					} else if (unit_type.equalsIgnoreCase(MenuItem.MASS)) {
						blackList = Utils.getBlackListUnitMass(getActivity());
					} else if (unit_type.equalsIgnoreCase(MenuItem.TEMPERATURE)) {
						blackList = Utils.getBlackListUnitTemp(getActivity());
					} else if (unit_type.equalsIgnoreCase(MenuItem.PRESSURE)) {
						blackList = Utils
								.getBlackListUnitPressure(getActivity());
					} else if (unit_type
							.equalsIgnoreCase(MenuItem.DIGITAL_STORAGE)) {
						blackList = Utils
								.getBlackListUnitStorage(getActivity());
					} else if (unit_type.equalsIgnoreCase(MenuItem.FORCE)) {
						blackList = Utils.getBlackListUnitForce(getActivity());
					} else if (unit_type.equalsIgnoreCase(MenuItem.TIME)) {
						blackList = Utils.getBlackListUnitTime(getActivity());
					} else if (unit_type.equalsIgnoreCase(MenuItem.SPEED)) {
						blackList = Utils.getBlackListUnitSpeed(getActivity());
					}

					for (String string : unitL) {

						unitList.add(new FavUnit(string, !blackList
								.contains(string)));
					}

				}
			} else {
				return false;
			}

		}

		return true;
	}

	private void init() {

		TextView tvTitleText = (TextView) findViewById(R.id.tvTitleText);
		tvTitleText.setTypeface(Utils.getBold(getActivity()));
		tvTitleText.setText(R.string.title_unit);

		Button btnSaveFav = (Button) findViewById(R.id.btnSaveFav);
		btnSaveFav.setTypeface(Utils.getBold(getActivity()));
		btnSaveFav.setOnClickListener(saveFavClickListener);

		TextView tvSelectAllFavTitle = (TextView) findViewById(R.id.tvSelectAllFavTitle);
		tvSelectAllFavTitle.setTypeface(Utils.getBold(getActivity()));

		chkSelectAllFav = (ImageView) findViewById(R.id.chkSelectAllFav);

		LinearLayout llSelectAll = (LinearLayout) findViewById(R.id.llSelectAll);
		llSelectAll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				chkSelectAllFav.setSelected(!chkSelectAllFav.isSelected());

				if (chkSelectAllFav.isSelected()) {
					mAdapter.selectAll(true);
				} else {
					mAdapter.selectAll(false);
				}

			}
		});

		mAdapter = new FavConverterAdapter(getActivity());
		lvUnitConverter = (ListView) findViewById(R.id.lvFavUnit);
		lvUnitConverter.setAdapter(mAdapter);
		lvUnitConverter.setOnItemClickListener(mItemClickListener);

		mAdapter.addAll(unitList);
		chkSelectAllFav.setSelected(mAdapter.isSelectedAll());
	}

	public class FavUnit {
		public FavUnit(String name, boolean isChecked) {
			this.name = name;
			this.isChecked = isChecked;
		}

		public String name;
		public boolean isChecked;
	}

	AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			mAdapter.changeSelection(position);
			chkSelectAllFav.setSelected(mAdapter.isSelectedAll());
		}
	};

	View.OnClickListener saveFavClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			mAdapter.saveBlackList(unit_type);
			setResult(RESULT_OK);
			finish();
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

}
