package luminous.unitconverter.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.measure.quantity.Area;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import luminous.unitconverter.utils.Constant;
import luminous.unitconverter.utils.Utils;

import net.sourceforge.jeval.Evaluator;

import org.jscience.physics.model.RelativisticModel;

import android.content.Context;

public class MyAreaUnitConverter {

	ArrayList<MyUnit> list = new ArrayList<MyUnit>();
	Context context;
	double val;
	String unitSel;
	Unit<Area> myUnit = SI.SQUARE_METRE;
	DecimalFormat df = new DecimalFormat("#.########");
	HashMap<String, Double> customUnit = new HashMap<String, Double>();

	String refUnit;

	public MyAreaUnitConverter(Context context) {
		RelativisticModel.select();
		this.context = context;
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAl_AREA, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_AREA,
				SQUARE_METRE);

		refUnit = Utils.getPref(context, Constant.REFERENCE_UNIT_AREA,
				SQUARE_METRE);

		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitArea(context));
	}

	public void refreshCustomUnit() {
		customUnit.clear();
		customUnit.putAll(Utils.getCustomUnitArea(context));
	}

	public String getSelectedUnit() {
		return unitSel;
	}

	public double getSelectedValue() {
		return val;
	}

	public void setSelectedValue(double val) {
		Utils.setPref(context, Constant.SELECTED_UNIT_VAl_AREA, "" + val);
		this.val = val;
	}

	public ArrayList<MyUnit> getAllUnit() {
		val = Double.valueOf(Utils.getPref(context,
				Constant.SELECTED_UNIT_VAl_AREA, "1"));
		unitSel = Utils.getPref(context, Constant.SELECTED_UNIT_AREA,
				SQUARE_METRE);

		list.clear();

		list.add(new MyUnit(SQUARE_METRE));
		list.add(new MyUnit(ARE));
		list.add(new MyUnit(HECTARE));
		list.add(new MyUnit(SQUARE_FOOT));
		list.add(new MyUnit(SQUARE_KILOMETER));
		list.add(new MyUnit(SQUARE_YARD));
		list.add(new MyUnit(SQUARE_MILE));
		list.add(new MyUnit(SQUARE_INCH));
		list.add(new MyUnit(ACRE));
		list.add(new MyUnit(ARES));
		list.add(new MyUnit(HIDES));
		list.add(new MyUnit(ROODS));
		list.add(new MyUnit(TOWNSHIPS));

		HashMap<String, Double> unitList = Utils.getCustomUnitArea(context);

		if (unitList.size() > 0) {
			for (Entry<String, Double> entry : unitList.entrySet()) {
				list.add(new MyUnit(entry.getKey()));
			}
		}

		return list;
	}

	public ArrayList<MyUnit> getFavUnit() {

		ArrayList<String> blackList = Utils.getBlackListUnitArea(context);

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
		return new String[] { SQUARE_METRE, ARE, HECTARE, SQUARE_FOOT,
				SQUARE_KILOMETER, SQUARE_YARD, SQUARE_MILE, SQUARE_INCH, ACRE,
				ARES, HIDES, ROODS, TOWNSHIPS };
	}

	public String[] getFavUnitNameArray() {
		ArrayList<MyUnit> favList = getFavUnit();
		String[] arr = new String[favList.size()];

		for (int i = 0; i < favList.size(); i++) {
			arr[i] = favList.get(i).name;
		}

		return arr;
	}

	public Unit<Area> getDefaultUnit(String unit) {

		if (unit.equalsIgnoreCase(SQUARE_METRE)) {
			return SI.SQUARE_METRE;
		} else if (unit.equalsIgnoreCase(ARE)) {
			return NonSI.ARE;
		} else if (unit.equalsIgnoreCase(HECTARE)) {
			return NonSI.HECTARE;
		} else if (unit.equalsIgnoreCase(SQUARE_FOOT)) {
			return NonSI.FOOT.pow(2).asType(Area.class);
		} else if (unit.equalsIgnoreCase(SQUARE_KILOMETER)) {
			return SI.KILOMETER.pow(2).asType(Area.class);
		} else if (unit.equalsIgnoreCase(SQUARE_YARD)) {
			return NonSI.YARD.pow(2).asType(Area.class);
		} else if (unit.equalsIgnoreCase(SQUARE_INCH)) {
			return NonSI.INCH.pow(2).asType(Area.class);
		} else if (unit.equalsIgnoreCase(ACRE)) {
			return SI.SQUARE_METRE.times(4046.8227).asType(Area.class);
		} else if (unit.equalsIgnoreCase(ARES)) {
			return NonSI.ARE;
		} else if (unit.equalsIgnoreCase(HIDES)) {
			return SI.SQUARE_METRE.times(485000).asType(Area.class);
		} else if (unit.equalsIgnoreCase(ROODS)) {
			return SI.SQUARE_METRE.times(1011.714106).asType(Area.class);
		} else if (unit.equalsIgnoreCase(TOWNSHIPS)) {
			return SI.SQUARE_METRE.times(93239571.972).asType(Area.class);
		} else if (unit.equalsIgnoreCase(SQUARE_MILE)) {
			return NonSI.MILE.pow(2).asType(Area.class);
		}

		// Debug.e("", "Returning null # " + unit);
		return null;
	}

	public static final String SQUARE_METRE = "Square Meter";
	public static final String ARE = "Are";
	public static final String HECTARE = "Hectare";
	public static final String SQUARE_FOOT = "Square Feet";
	public static final String SQUARE_KILOMETER = "Square Kilometer";
	public static final String SQUARE_YARD = "Square Yard";
	public static final String SQUARE_MILE = "Square Mile";
	public static final String SQUARE_INCH = "Square Inch";
	public static final String ACRE = "Acre";
	public static final String ARES = "Ares";
	public static final String HIDES = "hides";
	public static final String ROODS = "roods";
	public static final String TOWNSHIPS = "townships";

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
						Unit<Area> myDestUnit = getDefaultUnit(refUnit).divide(
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
