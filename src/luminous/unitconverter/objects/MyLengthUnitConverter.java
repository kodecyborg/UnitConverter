package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Length;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Debug;
import luminous.unitconverter.utils.Utils;

import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyLengthUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Length> myUnit = SI.METER;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyLengthUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAl_LENGTH, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_LENGTH, METRE);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_LENGTH, METRE);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitLength(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitLength(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAl_LENGTH, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAl_LENGTH, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_LENGTH, METRE);

		list.clear();

		list.add(new MyUnit(METRE));
		list.add(new MyUnit(KILOMETER));
		list.add(new MyUnit(CENTIMETER));
		list.add(new MyUnit(MILLIMETER));
		list.add(new MyUnit(MILE));
		list.add(new MyUnit(YARD));
		list.add(new MyUnit(FOOT));
		list.add(new MyUnit(INCH));
		list.add(new MyUnit(NAUTICAL_MILE));

		HashMap<String, Double> unitList = Utils.getCustomUnitLength(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitLength(context);

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
		return new String[] { METRE, KILOMETER, CENTIMETER, MILLIMETER, MILE,
				YARD, FOOT, INCH, NAUTICAL_MILE };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Length> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(METRE)) {
			return SI.METRE;
		} else if (unit.equalsIgnoreCase(KILOMETER)) {
			return SI.KILOMETER;
		} else if (unit.equalsIgnoreCase(CENTIMETER)) {
			return SI.CENTIMETER;
		} else if (unit.equalsIgnoreCase(MILLIMETER)) {
			return SI.MILLIMETER;
		} else if (unit.equalsIgnoreCase(MILE)) {
			return NonSI.MILE;
		} else if (unit.equalsIgnoreCase(YARD)) {
			return NonSI.YARD;
		} else if (unit.equalsIgnoreCase(FOOT)) {
			return NonSI.FOOT;
		} else if (unit.equalsIgnoreCase(INCH)) {
			return NonSI.INCH;
		} else if (unit.equalsIgnoreCase(NAUTICAL_MILE)) {
			return NonSI.NAUTICAL_MILE;
		}

		Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String METRE = "Meter";
	public static final String KILOMETER = "Kilometer";
	public static final String CENTIMETER = "Centimeter";
	public static final String MILLIMETER = "Millimeter";
	public static final String MILE = "Mile";
	public static final String YARD = "Yard";
	public static final String FOOT = "Foot";
	public static final String INCH = "Inch";
	public static final String NAUTICAL_MILE = "Nautical Mile";

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
						Unit<Length> myDestUnit = getDefaultUnit(refUnit)
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
