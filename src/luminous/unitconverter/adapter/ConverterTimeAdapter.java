package luminous.unitconverter.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import luminous.unitconverter.R;
import luminous.unitconverter.objects.MyTimeUnitConverter.MyUnit;
import luminous.unitconverter.utils.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConverterTimeAdapter extends ArrayAdapter<MyUnit> {

	private Context mContext;
	private LayoutInflater infalter;

	// private ArrayList<MyUnit> data = new ArrayList<MyUnit>();

	public ConverterTimeAdapter(Context c, int textViewResourceId) {
		super(c, textViewResourceId);
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
	}

	public void addAll(ArrayList<MyUnit> files) {

		try {

			HashMap<String, Integer> unitOrderList = Utils
					.getUnitOrderTime(mContext);

			for (int i = 0; i < files.size(); i++) {
				if (unitOrderList.containsKey(files.get(i).name)) {
					files.get(i).order = unitOrderList.get(files.get(i).name);
				}
			}

			Collections.sort(files, new Comparator<MyUnit>() {

				@Override
				public int compare(MyUnit object1, MyUnit object2) {
					if (object1.order > object2.order) {
						return 1;
					} else if (object1.order == object2.order) {
						return 0;
					} else {
						return -1;
					}
				}
			});

			clear();
			for (int i = 0; i < files.size(); i++) {
				insert(files.get(i), i);
			}

		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		notifyDataSetChanged();
	}

	public ArrayList<MyUnit> getAll() {
		ArrayList<MyUnit> data = new ArrayList<MyUnit>();
		for (int i = 0; i < getCount(); i++) {
			data.add(getItem(i));
		}

		return data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {

			convertView = infalter.inflate(R.layout.unit_converter_item, null);
			holder = new ViewHolder();

			holder.tvUnitName = (TextView) convertView
					.findViewById(R.id.tvUnitName);
			holder.tvUnitName.setTypeface(Utils.getNormal(mContext));

			holder.tvUnitValue = (TextView) convertView
					.findViewById(R.id.tvUnitValue);
			holder.tvUnitValue.setTypeface(Utils.getBold(mContext));

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {

			holder.tvUnitName.setText("" + getItem(position).name);
			holder.tvUnitValue.setText("" + getItem(position).displayVal);

		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		return convertView;
	}

	public class ViewHolder {
		TextView tvUnitName;
		TextView tvUnitValue;
	}

}
