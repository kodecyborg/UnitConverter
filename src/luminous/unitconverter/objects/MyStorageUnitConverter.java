package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.DataAmount;
import javax.measure.unit.NonSI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Debug;
import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyStorageUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<DataAmount> myUnit = NonSI.BYTE;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyStorageUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_STORAGE, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_STORAGE, BYTE);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_STORAGE, BYTE);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitStorage(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitStorage(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAL_STORAGE, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_STORAGE, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_STORAGE, BYTE);

		list.clear();

		list.add(new MyUnit(BYTE));
		list.add(new MyUnit(BIT));
		list.add(new MyUnit(NIBBLE));
		list.add(new MyUnit(KILOBITS));
		list.add(new MyUnit(KILOBYTES));
		list.add(new MyUnit(MEGABITS));
		list.add(new MyUnit(MEGABYTES));
		list.add(new MyUnit(GIGABITS));
		list.add(new MyUnit(GIGABYTES));
		list.add(new MyUnit(GIGABYTES));
		list.add(new MyUnit(TERABITS));
		list.add(new MyUnit(TERABYTES));
		list.add(new MyUnit(PETABITS));
		list.add(new MyUnit(PETABYTES));
		list.add(new MyUnit(EXABITS));
		list.add(new MyUnit(EXABYTES));

		HashMap<String, Double> unitList = Utils.getCustomUnitStorage(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitStorage(context);

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
		return new String[] { BYTE, BIT, NIBBLE, KILOBITS, KILOBYTES, MEGABITS,
				MEGABYTES, GIGABITS, GIGABYTES, TERABITS, TERABYTES, PETABITS,
				PETABYTES, EXABITS, EXABYTES };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<DataAmount> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(BYTE)) {
			return NonSI.BYTE;
		} else if (unit.equalsIgnoreCase(BIT)) {
			return NonSI.BYTE.times(0.125).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(NIBBLE)) {
			return NonSI.BYTE.times(0.5).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(KILOBITS)) {
			return NonSI.BYTE.times(128).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(KILOBYTES)) {
			return NonSI.BYTE.times(1024).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(MEGABITS)) {
			return NonSI.BYTE.times(131072).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(MEGABYTES)) {
			return NonSI.BYTE.times(1048576).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(GIGABITS)) {
			return NonSI.BYTE.times(134217728).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(GIGABYTES)) {
			return NonSI.BYTE.times(1073741824).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(TERABITS)) {
			return NonSI.BYTE.times(137438953472.0).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(TERABYTES)) {
			return NonSI.BYTE.times(1099511627776.0).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(PETABITS)) {
			return NonSI.BYTE.times(140737488355328.0).asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(PETABYTES)) {
			return NonSI.BYTE.times(1125899906842624.0)
					.asType(DataAmount.class);
		} else if (unit.equalsIgnoreCase(EXABITS)) {
			return NonSI.BYTE.times(144115188075855870.0).asType(
					DataAmount.class);
		} else if (unit.equalsIgnoreCase(EXABYTES)) {
			return NonSI.BYTE.times(1152921504606847000.0).asType(
					DataAmount.class);
		}

		Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String BYTE = "Byte";
	public static final String BIT = "Bit";
	public static final String NIBBLE = "Nibble";
	public static final String KILOBITS = "Kilobits";
	public static final String KILOBYTES = "Kilobytes";
	public static final String MEGABITS = "Megabits";
	public static final String MEGABYTES = "Megabytes";
	public static final String GIGABITS = "Gigabits";
	public static final String GIGABYTES = "Gigabytes";
	public static final String TERABITS = "Terabits";
	public static final String TERABYTES = "Terabytes";
	public static final String PETABITS = "Petabits";
	public static final String PETABYTES = "Petabytes";
	public static final String EXABITS = "Exabits";
	public static final String EXABYTES = "Exabytes";

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
						Unit<DataAmount> myDestUnit = getDefaultUnit(refUnit)
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
