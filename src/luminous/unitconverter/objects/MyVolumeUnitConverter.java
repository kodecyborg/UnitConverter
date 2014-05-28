package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Volume;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyVolumeUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Volume> myUnit = SI.CUBIC_METRE;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyVolumeUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAl_AREA, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_VOLUME,
				CUBIC_METRE);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_VOLUME,
				CUBIC_METRE);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitVol(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitVol(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAL_VOLUME, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_VOLUME, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_VOLUME,
				CUBIC_METRE);

		list.clear();

		list.add(new MyUnit(CUBIC_METRE));
		list.add(new MyUnit(CUBIC_INCH));
		list.add(new MyUnit(GALLON_DRY_US));
		list.add(new MyUnit(GALLON_LIQUID_US));
		list.add(new MyUnit(GALLON_UK));
		list.add(new MyUnit(LITER));
		list.add(new MyUnit(MILLILITER));
		list.add(new MyUnit(OUNCE_LIQUID_UK));
		list.add(new MyUnit(OUNCE_LIQUID_US));
		list.add(new MyUnit(PINTS_UK));
		list.add(new MyUnit(PINTS_DRY_US));
		list.add(new MyUnit(PINTS_LIQUID_US));
		list.add(new MyUnit(QUARTS_UK));
		list.add(new MyUnit(QUARTS_DRY_US));
		list.add(new MyUnit(QUARTS_LIQUID_US));
		list.add(new MyUnit(DRAM));

		HashMap<String, Double> unitList = Utils.getCustomUnitVol(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitVol(context);

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
		return new String[] { CUBIC_METRE, CUBIC_INCH, GALLON_DRY_US,
				GALLON_LIQUID_US, GALLON_UK, LITER, MILLILITER,
				OUNCE_LIQUID_UK, OUNCE_LIQUID_US, PINTS_UK, PINTS_DRY_US,
				PINTS_LIQUID_US, QUARTS_UK, QUARTS_DRY_US, QUARTS_LIQUID_US,
				DRAM };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Volume> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(CUBIC_METRE)) {
			return SI.CUBIC_METRE;
		} else if (unit.equalsIgnoreCase(CUBIC_INCH)) {
			return NonSI.CUBIC_INCH;
		} else if (unit.equalsIgnoreCase(GALLON_DRY_US)) {
			return NonSI.GALLON_DRY_US;
		} else if (unit.equalsIgnoreCase(GALLON_LIQUID_US)) {
			return NonSI.GALLON_LIQUID_US;
		} else if (unit.equalsIgnoreCase(GALLON_UK)) {
			return NonSI.GALLON_UK;
		} else if (unit.equalsIgnoreCase(LITER)) {
			return NonSI.LITER;
		} else if (unit.equalsIgnoreCase(MILLILITER)) {
			return SI.MILLI(NonSI.LITER);
		} else if (unit.equalsIgnoreCase(OUNCE_LIQUID_UK)) {
			return NonSI.OUNCE_LIQUID_UK;
		} else if (unit.equalsIgnoreCase(OUNCE_LIQUID_US)) {
			return NonSI.OUNCE_LIQUID_US;
		} else if (unit.equalsIgnoreCase(PINTS_UK)) {
			return SI.CUBIC_METRE.times(0.000568).asType(Volume.class);
		} else if (unit.equalsIgnoreCase(PINTS_DRY_US)) {
			return SI.CUBIC_METRE.times(0.00055).asType(Volume.class);
		} else if (unit.equalsIgnoreCase(PINTS_LIQUID_US)) {
			return SI.CUBIC_METRE.times(0.000473).asType(Volume.class);
		} else if (unit.equalsIgnoreCase(QUARTS_UK)) {
			return SI.CUBIC_METRE.times(0.001137).asType(Volume.class);
		} else if (unit.equalsIgnoreCase(QUARTS_DRY_US)) {
			return SI.CUBIC_METRE.times(0.001101).asType(Volume.class);
		} else if (unit.equalsIgnoreCase(QUARTS_LIQUID_US)) {
			return SI.CUBIC_METRE.times(0.000946).asType(Volume.class);
		} else if (unit.equalsIgnoreCase(DRAM)) {
			return SI.CUBIC_METRE.times(0.000003697).asType(Volume.class);
		}

		// Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String CUBIC_METRE = "Cubic Meter";
	public static final String CUBIC_INCH = "Cubic Inch";
	public static final String GALLON_DRY_US = "Gallon Dry US";
	public static final String GALLON_LIQUID_US = "Gallon Liquid US";
	public static final String GALLON_UK = "Gallon UK";
	public static final String LITER = "Liter";
	public static final String MILLILITER = "Milliliter";
	public static final String OUNCE_LIQUID_UK = "Ounce liquid UK";
	public static final String OUNCE_LIQUID_US = "Ounce liquid US";
	public static final String PINTS_UK = "Pints UK";
	public static final String PINTS_DRY_US = "Pints dry US";
	public static final String PINTS_LIQUID_US = "Pints Liquid US";
	public static final String QUARTS_UK = "Quarts UK";
	public static final String QUARTS_DRY_US = "Quarts dry US";
	public static final String QUARTS_LIQUID_US = "Quarts liquid US";
	public static final String DRAM = "Dram";

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
						Unit<Volume> myDestUnit = getDefaultUnit(refUnit)
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
