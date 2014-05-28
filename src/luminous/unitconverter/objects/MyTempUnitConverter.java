package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Temperature;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyTempUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Temperature> myUnit = SI.CELSIUS;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyTempUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_TEMPERATURE, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_TEMPERATURE,
				CELSIUS);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_TEMPERATURE,
				CELSIUS);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitTemp(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitTemp(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAL_TEMPERATURE, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAL_TEMPERATURE, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_TEMPERATURE,
				CELSIUS);

		list.clear();

		list.add(new MyUnit(CELSIUS));
		list.add(new MyUnit(FAHRENHEIT));
		list.add(new MyUnit(RANKINE));
		list.add(new MyUnit(KELVIN));

		HashMap<String, Double> unitList = Utils.getCustomUnitTemp(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitTemp(context);

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
		return new String[] { CELSIUS, FAHRENHEIT, RANKINE, KELVIN };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Temperature> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(CELSIUS)) {
			return SI.CELSIUS;
		} else if (unit.equalsIgnoreCase(FAHRENHEIT)) {
			return NonSI.FAHRENHEIT;
		} else if (unit.equalsIgnoreCase(RANKINE)) {
			return NonSI.RANKINE;
		} else if (unit.equalsIgnoreCase(KELVIN)) {
			return SI.CELSIUS.plus(-274.15).asType(Temperature.class);
		}

		// Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String CELSIUS = "Celsius";
	public static final String FAHRENHEIT = "Fahrenheit";
	public static final String RANKINE = "Rankine";
	public static final String KELVIN = "Kelvin";

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
						Unit<Temperature> myDestUnit = getDefaultUnit(refUnit)
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
