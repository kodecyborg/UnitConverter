package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Mass;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyMassUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Mass> myUnit = SI.KILOGRAM;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyMassUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAl_MASS, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_MASS, KILOGRAM);

		refUnit = Utils
				.getPref(context, Constant.REFERENCE_UNIT_MASS, KILOGRAM);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitMass(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitMass(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAl_MASS, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAl_MASS, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_MASS, KILOGRAM);

		list.clear();

		list.add(new MyUnit(KILOGRAM));
		list.add(new MyUnit(GRAM));
		list.add(new MyUnit(METRIC_TON));
		list.add(new MyUnit(ELECTRON_MASS));
		list.add(new MyUnit(OUNCE));
		list.add(new MyUnit(CENTAL));
		list.add(new MyUnit(POUND));
		list.add(new MyUnit(TON_UK));
		list.add(new MyUnit(TON_US));
		list.add(new MyUnit(ATOMIC_MASS));
		list.add(new MyUnit(MCG));
		list.add(new MyUnit(STONE));
		list.add(new MyUnit(GRAINS));

		HashMap<String, Double> unitList = Utils.getCustomUnitMass(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitMass(context);

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
		return new String[] { KILOGRAM, GRAM, METRIC_TON, ELECTRON_MASS, OUNCE,
				CENTAL, POUND, TON_UK, TON_US, ATOMIC_MASS, MCG, STONE, GRAINS };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Mass> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(KILOGRAM)) {
			return SI.KILOGRAM;
		} else if (unit.equalsIgnoreCase(GRAM)) {
			return SI.GRAM;
		} else if (unit.equalsIgnoreCase(MILLIGRAM)) {
			return SI.MILLI(SI.GRAM);
		} else if (unit.equalsIgnoreCase(METRIC_TON)) {
			return NonSI.METRIC_TON;
		} else if (unit.equalsIgnoreCase(ELECTRON_MASS)) {
			return NonSI.ELECTRON_MASS;
		} else if (unit.equalsIgnoreCase(OUNCE)) {
			return NonSI.OUNCE;
		} else if (unit.equalsIgnoreCase(POUND)) {
			return NonSI.POUND;
		} else if (unit.equalsIgnoreCase(TON_UK)) {
			return NonSI.TON_UK;
		} else if (unit.equalsIgnoreCase(TON_US)) {
			return NonSI.TON_US;
		} else if (unit.equalsIgnoreCase(ATOMIC_MASS)) {
			return NonSI.ATOMIC_MASS;
		} else if (unit.equalsIgnoreCase(MCG)) {
			return SI.KILOGRAM.times(1e-9).asType(Mass.class);
		} else if (unit.equalsIgnoreCase(STONE)) {
			return SI.KILOGRAM.times(6.35029).asType(Mass.class);
		} else if (unit.equalsIgnoreCase(GRAINS)) {
			return SI.KILOGRAM.times(0.000065).asType(Mass.class);
		} else if (unit.equalsIgnoreCase(CENTAL)) {
			return SI.KILOGRAM.times(45.359237).asType(Mass.class);
		}

		// Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String KILOGRAM = "Kilograms";
	public static final String GRAM = "Gram";
	public static final String MILLIGRAM = "Milligram";
	public static final String METRIC_TON = "Metric (Carats)";
	public static final String ELECTRON_MASS = "Electron Mass";
	public static final String OUNCE = "Ounce";
	public static final String CENTAL = "Cental";
	public static final String POUND = "Pound";
	public static final String TON_UK = "Ton (UK)";
	public static final String TON_US = "Ton (US)";
	public static final String ATOMIC_MASS = "Atomaic Mass";
	public static final String MCG = "Mcg";
	public static final String STONE = "Stone";
	public static final String GRAINS = "Grains";

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
						Unit<Mass> myDestUnit = getDefaultUnit(refUnit).divide(
								customUnit.get(name));
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
