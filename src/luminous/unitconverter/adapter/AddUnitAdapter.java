package luminous.unitconverter.adapter;

import java.util.ArrayList;

import luminous.unitconverter.R;
import luminous.unitconverter.objects.CustomUnit;
import luminous.unitconverter.utils.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddUnitAdapter {

	private Context mContext;
	private LayoutInflater infalter;
	private ArrayList<CustomUnit> data = new ArrayList<CustomUnit>();
	View.OnClickListener onClickListener;

	public AddUnitAdapter(Context c, View.OnClickListener onClickListener) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
		this.onClickListener = onClickListener;
	}

	public int getCount() {
		return data.size();
	}

	public CustomUnit getItem(int position) {
		return data.get(position);
	}

	public void addAll(ArrayList<CustomUnit> files) {

		try {
			this.data.clear();
			this.data.addAll(files);

		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

	}

	public void add(CustomUnit unit) {
		this.data.add(unit);
	}

	public void set(int index, CustomUnit unit) {
		this.data.set(index, unit);
	}

	public void removePosition(int position) {
		this.data.remove(position);
	}

	public View getView(int position) {

		ViewHolder holder;

		View convertView = infalter.inflate(R.layout.add_unit_item, null);
		holder = new ViewHolder();
		holder.editAddUnitName = (EditText) convertView
				.findViewById(R.id.editAddUnitName);
		holder.editAddUnitName.setTypeface(Utils.getBold(mContext));

		holder.editAddUnitVal = (EditText) convertView
				.findViewById(R.id.editAddUnitVal);
		holder.editAddUnitVal.setTypeface(Utils.getBold(mContext));

		holder.imgAddUnitDel = (ImageView) convertView
				.findViewById(R.id.imgAddUnitDel);
		holder.imgAddUnitDel.setOnClickListener(onClickListener);

		try {
			holder.imgAddUnitDel.setTag("" + position);

			holder.editAddUnitName.setText("" + data.get(position).unitName);
			holder.editAddUnitVal.setText("" + data.get(position).unitVal);

		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		return convertView;
	}

	public class ViewHolder {
		EditText editAddUnitName;
		EditText editAddUnitVal;
		ImageView imgAddUnitDel;
	}

}
