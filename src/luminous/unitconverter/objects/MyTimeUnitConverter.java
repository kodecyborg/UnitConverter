package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Duration;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Debug;
import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyTimeUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Duration> myUnit = SI.SECOND;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyTimeUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_TIME, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_TIME, SECOND);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_TIME, SECOND);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitTime(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitTime(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAL_TIME, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_TIME, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_TIME, SECOND);

		list.clear();

		list.add(new MyUnit(SECOND));
		list.add(new MyUnit(DAY));
		list.add(new MyUnit(DAY_SIDEREAL));
		list.add(new MyUnit(HOUR));
		list.add(new MyUnit(MINUTE));
		list.add(new MyUnit(MONTH));
		list.add(new MyUnit(WEEK));
		list.add(new MyUnit(YEAR));
		list.add(new MyUnit(YEAR_CALENDAR));
		list.add(new MyUnit(YEAR_SIDEREAL));

		HashMap<String, Double> unitList = Utils.getCustomUnitTime(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitTime(context);

		getAllUnit();
		ArrayList<MyUnit> listFav = new ArrayList<MyUnit>();
		for (MyUnit myUnit : list) {
			if (!blackList.contains(myUnit.name)) {
				listFav.add(myUnit);
			}
		}

		return listFav;

	}

	public ArrayList<String> getUnitList() {
		ArrayList<String> unitList = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			unitList.add(list.get(i).name);
		}

		return unitList;
	}

	public ArrayList<String> getFavUnitName() {
		ArrayList<MyUnit> favList = getFavUnit();
		ArrayList<String> unitList = new ArrayList<String>();

		for (int i = 0; i < favList.size(); i++) {
			unitList.add(favList.get(i).name);
		}

		return unitList;
	}

	public String[] getUnitNameArray() {
		return new String[] { SECOND, DAY, DAY_SIDEREAL, HOUR, MINUTE, MONTH,
				WEEK, YEAR, YEAR_CALENDAR, YEAR_SIDEREAL };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Duration> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(SECOND)) {
			return SI.SECOND;
		} else if (unit.equalsIgnoreCase(DAY)) {
			return NonSI.DAY;
		} else if (unit.equalsIgnoreCase(DAY_SIDEREAL)) {
			return NonSI.DAY_SIDEREAL;
		} else if (unit.equalsIgnoreCase(HOUR)) {
			return NonSI.HOUR;
		} else if (unit.equalsIgnoreCase(MINUTE)) {
			return NonSI.MINUTE;
		} else if (unit.equalsIgnoreCase(MONTH)) {
			return NonSI.MONTH;
		} else if (unit.equalsIgnoreCase(WEEK)) {
			return NonSI.WEEK;
		} else if (unit.equalsIgnoreCase(YEAR)) {
			return NonSI.YEAR;
		} else if (unit.equalsIgnoreCase(YEAR_CALENDAR)) {
			return NonSI.YEAR_CALENDAR;
		} else if (unit.equalsIgnoreCase(YEAR_SIDEREAL)) {
			return NonSI.YEAR_SIDEREAL;
		}

		Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String SECOND = "Second";
	public static final String DAY = "Day";
	public static final String DAY_SIDEREAL = "Day Sidereal";
	public static final String HOUR = "Hour";
	public static final String MINUTE = "Minute";
	public static final String MONTH = "Month";
	public static final String WEEK = "Week";
	public static final String YEAR = "Year";
	public static final String YEAR_CALENDAR = "Year Calendar";
	public static final String YEAR_SIDEREAL = "Year Sidereal";

	public class MyUnit {

		public MyUnit(String name) {
			double tmp = 0;
			this.name = name;

			if (getDefaultUnit(unitSel) != null) {
				myUnit = getDefaultUnit(unitSel);
			} else {
				if (customUnit.containsKey(unitSel)) {

					try {
						myUnit = getDefaultUnit(refUnit).divide(
								customUnit.get(unitSel));
					} catch (Exception e) {
						myUnit = getDefaultUnit(refUnit);
					}

				}
			}

			if (!isCustomUnit(name)) {
				tmp = myUnit.getConverterTo(getDefaultUnit(name)).convert(val);
			} else {
				if (customUnit.containsKey(name)) {

					try {
						Unit<Duration> myDestUnit = getDefaultUnit(refUnit)
								.divide(customUnit.get(name));
						tmp = myUnit.getConverterTo(myDestUnit).convert(val);
					} catch (Exception e) {
						tmp = customUnit.get(name);
					}

				}
			}

			Debug.e("", name + " # " + displayVal);

			this.value = tmp;
			try {
				this.displayVal = ""
						+ new Evaluator().getNumberResult("" + df.format(tmp));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public String name;
		public double value;
		public String displayVal = "";
		public int order;
	}

	private boolean isCustomUnit(String unit) {
		String[] str = getUnitNameArray();
		for (int i = 0; i < str.length; i++) {
			if (unit.equals(str[i])) {
				return false;
			}
		}

		return true;
	}

}
