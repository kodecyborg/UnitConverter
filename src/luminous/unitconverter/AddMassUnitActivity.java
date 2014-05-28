package luminous.unitconverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import luminous.unitconverter.adapter.AddUnitAdapter;
import luminous.unitconverter.objects.CustomUnit;
import luminous.unitconverter.objects.MyMassUnitConverter;
import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Utils;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AddMassUnitActivity extends BaseActivity {

	LinearLayout llAddUnit;
	AddUnitAdapter mAdapter;

	TextView tvAddUnitRefVal;
	TextView tvUnitValueVal;

	MyMassUnitConverter converter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_unit);

		init();
		//Uncomment to show ads
				//initAd(R.id.adLayout);
	}

	private void init() {
		converter = new MyMassUnitConverter(getActivity());

		TextView tvTitleText = (TextView) findViewById(R.id.tvTitleText);
		tvTitleText.setTypeface(Utils.getBold(getActivity()));
		tvTitleText.setText(R.string.title_add_unit);

		TextView tvAddUnitRef = (TextView) findViewById(R.id.tvAddUnitRef);
		tvAddUnitRef.setTypeface(Utils.getBold(getActivity()));

		TextView tvAddUnitSymbol = (TextView) findViewById(R.id.tvAddUnitSymbol);
		tvAddUnitSymbol.setTypeface(Utils.getBold(getActivity()));
		TextView tvAddUnitMulRatio = (TextView) findViewById(R.id.tvAddUnitMulRatio);
		tvAddUnitMulRatio.setTypeface(Utils.getBold(getActivity()));

		tvAddUnitRefVal = (TextView) findViewById(R.id.tvAddUnitRefVal);
		tvAddUnitRefVal.setTypeface(Utils.getBold(getActivity()));
		tvAddUnitRefVal.setText(Utils.getPref(getActivity(),
				Constant.REFERENCE_UNIT_MASS, MyMassUnitConverter.KILOGRAM));
		tvAddUnitRefVal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showUnitSpinner(converter.getUnitNameArray());
			}
		});

		mAdapter = new AddUnitAdapter(getActivity(), delUnitClickListener);
		llAddUnit = (LinearLayout) findViewById(R.id.llAddUnit);
		HashMap<String, Double> unitList = Utils
				.getCustomUnitMass(getActivity());

		if (unitList.size() > 0) {
			int i = 0;
			for (Entry<String, Double> entry : unitList.entrySet()) {
				mAdapter.add(new CustomUnit(entry.getKey(), ""
						+ entry.getValue()));
				llAddUnit.addView(mAdapter.getView(i));
				i++;
			}
		} else {
			mAdapter.add(new CustomUnit("", ""));
			llAddUnit.addView(mAdapter.getView(mAdapter.getCount() - 1));
		}

		ImageView imgAddUnit = (ImageView) findViewById(R.id.imgAddUnit);
		imgAddUnit.setOnClickListener(addUnitClickListener);

		Button btnSaveCustomUnit = (Button) findViewById(R.id.btnSaveCustomUnit);
		btnSaveCustomUnit.setTypeface(Utils.getBold(getActivity()));
		btnSaveCustomUnit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Utils.setPref(getActivity(), Constant.REFERENCE_UNIT_MASS,
						Utils.getPref(getActivity(),
								Constant.REFERENCE_UNIT_TMP_MASS,
								MyMassUnitConverter.KILOGRAM));

				ArrayList<CustomUnit> list = new ArrayList<CustomUnit>();

				for (int i = 0; i < mAdapter.getCount(); i++) {
					View view = llAddUnit.getChildAt(i);
					EditText editAddUnitName = (EditText) view
							.findViewById(R.id.editAddUnitName);
					EditText editAddUnitVal = (EditText) view
							.findViewById(R.id.editAddUnitVal);

					if (editAddUnitName.getText().toString().trim().length() > 0
							&& editAddUnitVal.getText().toString().trim()
									.length() > 0) {

						list.add(new CustomUnit(editAddUnitName.getText()
								.toString().trim(), editAddUnitVal.getText()
								.toString().trim()));
					}
				}

				HashMap<String, Double> unitMap = new HashMap<String, Double>();
				for (CustomUnit customUnit : list) {
					try {
						unitMap.put(customUnit.unitName,
								Double.valueOf(customUnit.unitVal));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				Utils.saveCustomUnitMass(getActivity(), unitMap);
				setResult(RESULT_OK);
				finish();
			}
		});

	}

	View.OnClickListener delUnitClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				String position = (String) v.getTag();
				if (position != null && position.length() > 0) {
					mAdapter.removePosition(Integer.valueOf(position));
					llAddUnit.removeAllViews();
					for (int i = 0; i < mAdapter.getCount(); i++) {
						llAddUnit.addView(mAdapter.getView(i));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	View.OnClickListener addUnitClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			mAdapter.add(new CustomUnit("", ""));
			llAddUnit.addView(mAdapter.getView(mAdapter.getCount() - 1));
		}
	};

	private void showUnitSpinner(final String[] data) {

		final Dialog a = new Dialog(this);
		Window w = a.getWindow();
		a.requestWindowFeature(Window.FEATURE_NO_TITLE);
		a.setContentView(R.layout.spinner);
		w.setBackgroundDrawableResource(android.R.color.transparent);

		TextView lblselect = (TextView) w.findViewById(R.id.dialogtitle);
		lblselect.setTypeface(Utils.getBold(getActivity()));
		lblselect.setText(getString(R.string.reference_unit).toUpperCase(
				Locale.getDefault()));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item, data);
		ListView lv = (ListView) w.findViewById(R.id.lvSpinner);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view,
					int position, long l) {

				a.dismiss();

				refresh(Utils.getPref(getActivity(),
						Constant.REFERENCE_UNIT_TMP_MASS,
						MyMassUnitConverter.KILOGRAM), data[position]);

				Utils.setPref(getActivity(), Constant.REFERENCE_UNIT_TMP_MASS,
						data[position]);
				tvAddUnitRefVal.setText(data[position]);

			}
		});

		a.show();
	}

	public void refresh(String oldUnit, String newUnit) {
		MyMassUnitConverter converter = new MyMassUnitConverter(getActivity());

		for (int i = 0; i < mAdapter.getCount(); i++) {
			View view = llAddUnit.getChildAt(i);

			EditText editAddUnitVal = (EditText) view
					.findViewById(R.id.editAddUnitVal);

			if (editAddUnitVal.getText().toString().trim().length() > 0) {
				double val = converter
						.getDefaultUnit(oldUnit)
						.getConverterTo(converter.getDefaultUnit(newUnit))
						.convert(
								Double.valueOf(editAddUnitVal.getText()
										.toString().trim()));

				((EditText) (llAddUnit.getChildAt(i)
						.findViewById(R.id.editAddUnitVal))).setText("" + val);

				CustomUnit customUnit = mAdapter.getItem(i);
				customUnit.unitVal = "" + val;
				mAdapter.set(i, customUnit);
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

}
