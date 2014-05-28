package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Force;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Debug;
import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyForceUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Force> myUnit = SI.NEWTON;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyForceUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_FORCE, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_FORCE, NEWTON);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_FORCE, NEWTON);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitForce(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitForce(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAL_FORCE, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_FORCE, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_FORCE, NEWTON);

		list.clear();

		list.add(new MyUnit(NEWTON));
		list.add(new MyUnit(KILOGRAM_FORCE));
		list.add(new MyUnit(POUND_FORCE));
		list.add(new MyUnit(DYNES));
		list.add(new MyUnit(KILONEWTON));
		list.add(new MyUnit(KIPS));
		list.add(new MyUnit(POUNDALS));
		list.add(new MyUnit(STHENES));
		list.add(new MyUnit(TONNES_FORCE));

		HashMap<String, Double> unitList = Utils.getCustomUnitForce(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitForce(context);

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
		return new String[] { NEWTON, KILOGRAM_FORCE, POUND_FORCE, DYNES,
				KILONEWTON, KIPS, POUNDALS, STHENES, TONNES_FORCE };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Force> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(NEWTON)) {
			return SI.NEWTON;
		} else if (unit.equalsIgnoreCase(KILOGRAM_FORCE)) {
			return NonSI.KILOGRAM_FORCE;
		} else if (unit.equalsIgnoreCase(POUND_FORCE)) {
			return NonSI.POUND_FORCE;
		} else if (unit.equalsIgnoreCase(DYNES)) {
			return SI.NEWTON.times(0.00001).asType(Force.class);
		} else if (unit.equalsIgnoreCase(KILONEWTON)) {
			return SI.NEWTON.times(1000).asType(Force.class);
		} else if (unit.equalsIgnoreCase(KIPS)) {
			return SI.NEWTON.times(4448.222).asType(Force.class);
		} else if (unit.equalsIgnoreCase(POUNDALS)) {
			return SI.NEWTON.times(0.138255).asType(Force.class);
		} else if (unit.equalsIgnoreCase(STHENES)) {
			return SI.NEWTON.times(1000).asType(Force.class);
		} else if (unit.equalsIgnoreCase(TONNES_FORCE)) {
			return SI.NEWTON.times(9806.65).asType(Force.class);
		} else if (unit.equalsIgnoreCase(TONS_FORCE_UK)) {
			return SI.NEWTON.times(9964.016418).asType(Force.class);
		} else if (unit.equalsIgnoreCase(TONS_FORCE_US)) {
			return SI.NEWTON.times(8896.443231).asType(Force.class);
		}

		Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String NEWTON = "Newton";
	public static final String KILOGRAM_FORCE = "Kilogram Force";
	public static final String POUND_FORCE = "Pound Force";
	public static final String DYNES = "Dynes";
	public static final String KILONEWTON = "Kilonewton";
	public static final String KIPS = "Kips";
	public static final String POUNDALS = "Poundals";
	public static final String STHENES = "sthenes (=kN)";
	public static final String TONNES_FORCE = "Tonnes Force";
	public static final String TONS_FORCE_UK = "Tons Force (UK)";
	public static final String TONS_FORCE_US = "Tons Force (US)";

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
						Unit<Force> myDestUnit = getDefaultUnit(refUnit)
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
