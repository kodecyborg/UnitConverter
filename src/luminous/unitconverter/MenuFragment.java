package luminous.unitconverter;

import java.util.ArrayList;

import luminous.unitconverter.adapter.MenuAdapter;
import luminous.unitconverter.utils.Utils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class MenuFragment extends Fragment {

	Context mContext;
	MenuAdapter adapter;
	ListView lvMenu;

	public MenuFragment() {
	}

	public MenuFragment(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		LinearLayout gv = null;
		try {
			if (mContext == null) {
				mContext = getActivity();
			}

			gv = (LinearLayout) inflater.inflate(R.layout.menu_list, null);

			initTab(gv);
		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		return gv;
	}

	private void initTab(LinearLayout gv) {

		adapter = new MenuAdapter(getActivity());

		lvMenu = (ListView) gv.findViewById(R.id.lvMenu);
		lvMenu.setOnItemClickListener(mItemClickListener);
		lvMenu.setAdapter(adapter);
		adapter.addAll(getMenuItem());
	}

	public void restartAnimation() {
		// lvMenu.startLayoutAnimation();
	}

	private ArrayList<String> getMenuItem() {
		ArrayList<String> menuList = new ArrayList<String>();
		menuList.add(MenuItem.LENGTH);
		menuList.add(MenuItem.MASS);
		menuList.add(MenuItem.SPEED);
		menuList.add(MenuItem.VOLUME);
		menuList.add(MenuItem.AREA);
		menuList.add(MenuItem.TEMPERATURE);
		menuList.add(MenuItem.PRESSURE);
		menuList.add(MenuItem.TIME);
		menuList.add(MenuItem.DIGITAL_STORAGE);
		menuList.add(MenuItem.FORCE);

		return menuList;
	}

	public class MenuItem {
		public static final String LENGTH = "Length";
		public static final String MASS = "Mass";
		public static final String SPEED = "Speed";
		public static final String VOLUME = "Volume";
		public static final String AREA = "Area";
		public static final String TEMPERATURE = "Temperature";
		public static final String PRESSURE = "Pressure";
		public static final String TIME = "Time";
		public static final String DIGITAL_STORAGE = "Digital Storage";
		public static final String FORCE = "Force";
	}

	AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {

			Intent intent = null;

			if (adapter.getItem(position).equalsIgnoreCase(MenuItem.LENGTH)) {
				intent = new Intent(getActivity(),
						UnitLengthConverterActivity.class);
			}

			if (adapter.getItem(position).equalsIgnoreCase(MenuItem.AREA)) {
				intent = new Intent(getActivity(),
						UnitAreaConverterActivity.class);
			}

			if (adapter.getItem(position).equalsIgnoreCase(MenuItem.SPEED)) {
				intent = new Intent(getActivity(),
						UnitSpeedConverterActivity.class);
			}

			if (adapter.getItem(position).equalsIgnoreCase(MenuItem.MASS)) {
				intent = new Intent(getActivity(),
						UnitMassConverterActivity.class);
			}

			if (adapter.getItem(position)
					.equalsIgnoreCase(MenuItem.TEMPERATURE)) {
				intent = new Intent(getActivity(),
						UnitTempConverterActivity.class);
			}

			if (adapter.getItem(position).equalsIgnoreCase(MenuItem.VOLUME)) {
				intent = new Intent(getActivity(),
						UnitVolumeConverterActivity.class);
			}

			if (adapter.getItem(position).equalsIgnoreCase(MenuItem.PRESSURE)) {
				intent = new Intent(getActivity(),
						UnitPressureConverterActivity.class);
			}

			if (adapter.getItem(position).equalsIgnoreCase(
					MenuItem.DIGITAL_STORAGE)) {
				intent = new Intent(getActivity(),
						UnitStorageConverterActivity.class);
			}

			if (adapter.getItem(position).equalsIgnoreCase(MenuItem.FORCE)) {
				intent = new Intent(getActivity(),
						UnitForceConverterActivity.class);
			}

			if (adapter.getItem(position).equalsIgnoreCase(MenuItem.TIME)) {
				intent = new Intent(getActivity(),
						UnitTimeConverterActivity.class);
			}

			if (intent != null) {
				Utils.startActivity(getActivity(), intent);
				getActivity().finish();
				hideMenu();
			}

		}
	};

	private void hideMenu() {
		((SlidingBaseFragmentActivity) getActivity()).getSlidingMenu()
				.showContent();
	}

}
