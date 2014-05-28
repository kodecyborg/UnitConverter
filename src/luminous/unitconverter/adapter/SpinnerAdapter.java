package luminous.unitconverter.adapter;

import java.util.ArrayList;

import luminous.unitconverter.R;
import luminous.unitconverter.objects.Spinner;
import luminous.unitconverter.utils.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater infalter;
	private ArrayList<Spinner> data = new ArrayList<Spinner>();

	public SpinnerAdapter(Context c) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Spinner getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addAll(ArrayList<Spinner> files) {

		try {

			this.data.clear();
			this.data.addAll(files);

		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = infalter.inflate(R.layout.spinner_item, null);
			holder.tvMenuTitle = (TextView) convertView;
			holder.tvMenuTitle.setTypeface(Utils.getBold(mContext));

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.tvMenuTitle.setText(data.get(position).title);
		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		return convertView;
	}

	public class ViewHolder {
		TextView tvMenuTitle;
	}

}
