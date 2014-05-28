package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Pressure;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Debug;
import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyPressureUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Pressure> myUnit = SI.PASCAL;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyPressureUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_PRESSURE, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_PRESSURE,
				PASCAL);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_PRESSURE,
				PASCAL);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitPressure(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitPressure(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAL_PRESSURE, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_PRESSURE, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_PRESSURE,
				PASCAL);

		list.clear();

		list.add(new MyUnit(PASCAL));
		list.add(new MyUnit(ATMOSPHERE));
		list.add(new MyUnit(INCH_OF_MERCURY));
		list.add(new MyUnit(METERS_OF_WATER));
		list.add(new MyUnit(MILLIBARS));
		list.add(new MyUnit(BARS));
		list.add(new MyUnit(TONS_FC_SQFOOT_UK));
		list.add(new MyUnit(TONS_FC_SQINCH_UK));
		list.add(new MyUnit(TONS_FC_SQFOOT_US));
		list.add(new MyUnit(TONS_FC_SQINCH_US));

		HashMap<String, Double> unitList = Utils.getCustomUnitPressure(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitPressure(context);

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
		return new String[] { PASCAL, ATMOSPHERE, INCH_OF_MERCURY,
				METERS_OF_WATER, MILLIBARS, BARS, TONS_FC_SQFOOT_UK,
				TONS_FC_SQINCH_UK, TONS_FC_SQFOOT_US, TONS_FC_SQINCH_US };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Pressure> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(PASCAL)) {
			return SI.PASCAL;
		} else if (unit.equalsIgnoreCase(ATMOSPHERE)) {
			return NonSI.ATMOSPHERE;
		} else if (unit.equalsIgnoreCase(INCH_OF_MERCURY)) {
			return NonSI.INCH_OF_MERCURY;
		} else if (unit.equalsIgnoreCase(METERS_OF_WATER)) {
			return SI.PASCAL.times(9806.65).asType(Pressure.class);
		} else if (unit.equalsIgnoreCase(MILLIBARS)) {
			return SI.PASCAL.times(100).asType(Pressure.class);
		} else if (unit.equalsIgnoreCase(BARS)) {
			return SI.PASCAL.times(100000).asType(Pressure.class);
		} else if (unit.equalsIgnoreCase(TONS_FC_SQFOOT_UK)) {
			return SI.PASCAL.times(107251.78011595).asType(Pressure.class);
		} else if (unit.equalsIgnoreCase(TONS_FC_SQINCH_UK)) {
			return SI.PASCAL.times(15444256.336697).asType(Pressure.class);
		} else if (unit.equalsIgnoreCase(TONS_FC_SQFOOT_US)) {
			return SI.PASCAL.times(95760.517960678).asType(Pressure.class);
		} else if (unit.equalsIgnoreCase(TONS_FC_SQINCH_US)) {
			return SI.PASCAL.times(13789514.586338).asType(Pressure.class);
		}

		Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String PASCAL = "Pascal";
	public static final String ATMOSPHERE = "Atmosphere";
	public static final String INCH_OF_MERCURY = "Inch of Mercury";
	public static final String METERS_OF_WATER = "Meters of Water";
	public static final String MILLIBARS = "Millibars";
	public static final String BARS = "Bars";
	public static final String TONS_FC_SQFOOT_UK = "Tons UK (Force/Sq Foot)";
	public static final String TONS_FC_SQINCH_UK = "Tons UK (Force/Sq Inch)";
	public static final String TONS_FC_SQFOOT_US = "Tons US (Force/Sq Foot)";
	public static final String TONS_FC_SQINCH_US = "Tons US (Force/Sq Inch)";

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
						Unit<Pressure> myDestUnit = getDefaultUnit(refUnit)
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
