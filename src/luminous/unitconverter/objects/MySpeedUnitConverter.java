package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Velocity;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MySpeedUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Velocity> myUnit = SI.METRES_PER_SECOND;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MySpeedUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_SPEED, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_SPEED,
				METRE_PER_SEC);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_SPEED,
				METRE_PER_SEC);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitSpeed(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitSpeed(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAL_SPEED, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_SPEED, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_SPEED,
				METRE_PER_SEC);

		list.clear();

		list.add(new MyUnit(METRE_PER_SEC));
		list.add(new MyUnit(METERS_PER_HOUR));
		list.add(new MyUnit(METERS_PER_MINUTE));
		list.add(new MyUnit(KNOT));
		list.add(new MyUnit(MACH));
		list.add(new MyUnit(KILOMETERS_PER_HOUR));
		list.add(new MyUnit(KILOMETERS_PER_SECOND));
		list.add(new MyUnit(MILES_PER_HOUR));
		list.add(new MyUnit(MILE_PER_MINUTE));
		list.add(new MyUnit(MILE_PER_SECOND));
		list.add(new MyUnit(FOOT_PER_HOUR));
		list.add(new MyUnit(FOOT_PER_MINUTE));
		list.add(new MyUnit(FOOT_PER_SECOND));

		HashMap<String, Double> unitList = Utils.getCustomUnitSpeed(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitSpeed(context);

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
		return new String[] { METRE_PER_SEC, METERS_PER_HOUR,
				METERS_PER_MINUTE, KNOT, KILOMETERS_PER_HOUR,
				KILOMETERS_PER_SECOND, MACH, MILES_PER_HOUR, MILE_PER_MINUTE,
				MILE_PER_SECOND, FOOT_PER_HOUR, FOOT_PER_MINUTE,
				FOOT_PER_SECOND };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Velocity> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(METRE_PER_SEC)) {
			return SI.METERS_PER_SECOND;
		} else if (unit.equalsIgnoreCase(KNOT)) {
			return NonSI.KNOT;
		} else if (unit.equalsIgnoreCase(KILOMETERS_PER_HOUR)) {
			return NonSI.KILOMETERS_PER_HOUR;
		} else if (unit.equalsIgnoreCase(KILOMETERS_PER_SECOND)) {
			return SI.KILOMETER.divide(SI.SECOND).asType(Velocity.class);
		} else if (unit.equalsIgnoreCase(MACH)) {
			return NonSI.MACH;
		} else if (unit.equalsIgnoreCase(METERS_PER_HOUR)) {
			return SI.METER.divide(NonSI.HOUR).asType(Velocity.class);
		} else if (unit.equalsIgnoreCase(METERS_PER_MINUTE)) {
			return SI.METER.divide(NonSI.MINUTE).asType(Velocity.class);
		} else if (unit.equalsIgnoreCase(FOOT_PER_HOUR)) {
			return NonSI.FOOT.divide(NonSI.HOUR).asType(Velocity.class);
		} else if (unit.equalsIgnoreCase(FOOT_PER_MINUTE)) {
			return NonSI.FOOT.divide(NonSI.MINUTE).asType(Velocity.class);
		} else if (unit.equalsIgnoreCase(FOOT_PER_SECOND)) {
			return NonSI.FOOT.divide(SI.SECOND).asType(Velocity.class);
		} else if (unit.equalsIgnoreCase(MILES_PER_HOUR)) {
			return NonSI.MILES_PER_HOUR;
		} else if (unit.equalsIgnoreCase(MILE_PER_MINUTE)) {
			return NonSI.MILE.divide(NonSI.MINUTE).asType(Velocity.class);
		} else if (unit.equalsIgnoreCase(MILE_PER_SECOND)) {
			return NonSI.MILE.divide(SI.SECOND).asType(Velocity.class);
		}

		// Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String METRE_PER_SEC = "Meters/Second";
	public static final String METERS_PER_HOUR = "Meters/Hour";
	public static final String METERS_PER_MINUTE = "Meters/Minute";
	public static final String KILOMETERS_PER_HOUR = "Kilometer/Hour";
	public static final String KILOMETERS_PER_SECOND = "Kilometer/Second";
	public static final String KNOT = "Knot";
	public static final String MACH = "Mach";
	public static final String FOOT_PER_HOUR = "Feet/Hour";
	public static final String FOOT_PER_MINUTE = "Feet/Minute";
	public static final String FOOT_PER_SECOND = "Feet/Second";
	public static final String MILES_PER_HOUR = "Miles/Hour";
	public static final String MILE_PER_MINUTE = "Mile/Minute";
	public static final String MILE_PER_SECOND = "Mile/Second";

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
						Unit<Velocity> myDestUnit = getDefaultUnit(refUnit)
								.divide(customUnit.get(name));
						tmp = myUnit.getConverterTo(myDestUnit).convert(val);
					} catch (Exception e) {
						tmp = customUnit.get(name);
					}

				}
			}

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
