package luminous.unitconverter;

import java.text.DecimalFormat;

import luminous.unitconverter.utils.Utils;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class CalcFragment extends DialogFragment {

	TextView tvCalcBtn0;
	TextView tvCalcBtn1;
	TextView tvCalcBtn2;
	TextView tvCalcBtn3;
	TextView tvCalcBtn4;
	TextView tvCalcBtn5;
	TextView tvCalcBtn6;
	TextView tvCalcBtn7;
	TextView tvCalcBtn8;
	TextView tvCalcBtn9;
	TextView tvCalcBtnC;
	ImageView tvCalcBtnDel;
	TextView tvCalcBtnDev;
	TextView tvCalcBtnDot;
	TextView tvCalcBtnEq;
	TextView tvCalcBtnMinus;
	TextView tvCalcBtnMul;
	TextView tvCalcBtnPlus;
	TextView tvCalcBtnRndClose;
	TextView tvCalcBtnRndStart;

	Button btnOk;
	Button btnCancel;

	TextView tvCalcDisplay;
	DecimalFormat format = new DecimalFormat("0.00");

	public CalcFragment() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog a = new Dialog(getActivity());
		Window w = a.getWindow();
		a.requestWindowFeature(Window.FEATURE_NO_TITLE);
		w.setBackgroundDrawableResource(android.R.color.transparent);
		a.setContentView(R.layout.calc_dialog);

		tvCalcDisplay = (TextView) w.findViewById(R.id.tvCalcDisplay);
		tvCalcDisplay.setTypeface(Utils.getBold(getActivity()));
		tvCalcDisplay.setText("");

		tvCalcBtn0 = (TextView) w.findViewById(R.id.tvCalcBtn0);
		tvCalcBtn0.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn0.setOnClickListener(btnClickListener);

		tvCalcBtn1 = (TextView) w.findViewById(R.id.tvCalcBtn1);
		tvCalcBtn1.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn1.setOnClickListener(btnClickListener);

		tvCalcBtn2 = (TextView) w.findViewById(R.id.tvCalcBtn2);
		tvCalcBtn2.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn2.setOnClickListener(btnClickListener);

		tvCalcBtn3 = (TextView) w.findViewById(R.id.tvCalcBtn3);
		tvCalcBtn3.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn3.setOnClickListener(btnClickListener);

		tvCalcBtn4 = (TextView) w.findViewById(R.id.tvCalcBtn4);
		tvCalcBtn4.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn4.setOnClickListener(btnClickListener);

		tvCalcBtn5 = (TextView) w.findViewById(R.id.tvCalcBtn5);
		tvCalcBtn5.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn5.setOnClickListener(btnClickListener);

		tvCalcBtn6 = (TextView) w.findViewById(R.id.tvCalcBtn6);
		tvCalcBtn6.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn6.setOnClickListener(btnClickListener);

		tvCalcBtn7 = (TextView) w.findViewById(R.id.tvCalcBtn7);
		tvCalcBtn7.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn7.setOnClickListener(btnClickListener);

		tvCalcBtn8 = (TextView) w.findViewById(R.id.tvCalcBtn8);
		tvCalcBtn8.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn8.setOnClickListener(btnClickListener);

		tvCalcBtn9 = (TextView) w.findViewById(R.id.tvCalcBtn9);
		tvCalcBtn9.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtn9.setOnClickListener(btnClickListener);

		tvCalcBtnC = (TextView) w.findViewById(R.id.tvCalcBtnC);
		tvCalcBtnC.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnC.setOnClickListener(btnClickListener);

		tvCalcBtnDel = (ImageView) w.findViewById(R.id.tvCalcBtnDel);
		tvCalcBtnDel.setOnClickListener(btnClickListener);

		tvCalcBtnDev = (TextView) w.findViewById(R.id.tvCalcBtnDev);
		tvCalcBtnDev.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnDev.setOnClickListener(btnClickListener);

		tvCalcBtnDot = (TextView) w.findViewById(R.id.tvCalcBtnDot);
		tvCalcBtnDot.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnDot.setOnClickListener(btnClickListener);

		tvCalcBtnEq = (TextView) w.findViewById(R.id.tvCalcBtnEq);
		tvCalcBtnEq.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnEq.setOnClickListener(btnClickListener);

		tvCalcBtnMinus = (TextView) w.findViewById(R.id.tvCalcBtnMinus);
		tvCalcBtnMinus.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnMinus.setOnClickListener(btnClickListener);

		tvCalcBtnMul = (TextView) w.findViewById(R.id.tvCalcBtnMul);
		tvCalcBtnMul.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnMul.setOnClickListener(btnClickListener);

		tvCalcBtnPlus = (TextView) w.findViewById(R.id.tvCalcBtnPlus);
		tvCalcBtnPlus.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnPlus.setOnClickListener(btnClickListener);

		tvCalcBtnRndClose = (TextView) w.findViewById(R.id.tvCalcBtnRndClose);
		tvCalcBtnRndClose.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnRndClose.setOnClickListener(btnClickListener);

		tvCalcBtnRndStart = (TextView) w.findViewById(R.id.tvCalcBtnRndStart);
		tvCalcBtnRndStart.setTypeface(Utils.getBold(getActivity()));
		tvCalcBtnRndStart.setOnClickListener(btnClickListener);

		btnOk = (Button) w.findViewById(R.id.dialog_positive);
		btnOk.setTypeface(Utils.getBold(getActivity()));
		btnOk.setOnClickListener(btnClickListener);

		btnCancel = (Button) w.findViewById(R.id.dialog_negative);
		btnCancel.setTypeface(Utils.getBold(getActivity()));
		btnCancel.setOnClickListener(btnClickListener);

		return a;
	}

	View.OnClickListener btnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == tvCalcBtn0) {
				mathExp = mathExp + "0";
			} else if (v == tvCalcBtn1) {
				mathExp = mathExp + "1";
			} else if (v == tvCalcBtn2) {
				mathExp = mathExp + "2";
			} else if (v == tvCalcBtn3) {
				mathExp = mathExp + "3";
			} else if (v == tvCalcBtn4) {
				mathExp = mathExp + "4";
			} else if (v == tvCalcBtn5) {
				mathExp = mathExp + "5";
			} else if (v == tvCalcBtn6) {
				mathExp = mathExp + "6";
			} else if (v == tvCalcBtn7) {
				mathExp = mathExp + "7";
			} else if (v == tvCalcBtn8) {
				mathExp = mathExp + "8";
			} else if (v == tvCalcBtn9) {
				mathExp = mathExp + "9";
			} else if (v == tvCalcBtnC) {
				mathExp = "";
			} else if (v == tvCalcBtnDel) {
				if (mathExp.length() > 0) {
					mathExp = mathExp.trim();
					mathExp = mathExp.substring(0, mathExp.length() - 1);
					mathExp = mathExp.trim();
				}
			} else if (v == tvCalcBtnDev) {
				mathExp = mathExp + " / ";
			} else if (v == tvCalcBtnDot) {
				mathExp = mathExp + ".";
			} else if (v == tvCalcBtnMinus) {
				mathExp = mathExp + " - ";
			} else if (v == tvCalcBtnMul) {
				mathExp = mathExp + " * ";
			} else if (v == tvCalcBtnPlus) {
				mathExp = mathExp + " + ";
			} else if (v == tvCalcBtnRndClose) {
				mathExp = mathExp + " ) ";
			} else if (v == tvCalcBtnRndStart) {
				mathExp = mathExp + " ( ";
			} else if (v == tvCalcBtnEq) {
				try {
					mathExp = new Evaluator().evaluate(mathExp);
					mathExp = format.format(Double.valueOf(mathExp));
				} catch (EvaluationException e) {
					e.printStackTrace();
				}
			} else if (v == btnOk) {

				try {

					mathExp = new Evaluator().evaluate(mathExp);
					mathExp = format.format(Double.valueOf(mathExp));
					((SlidingBaseFragmentActivity) getActivity())
							.onValueSet(mathExp);
					dismiss();

				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (EvaluationException e) {
					e.printStackTrace();
				}

			} else if (v == btnCancel) {
				dismiss();
			}

			if (!isRemoving()) {
				display();
			}
		}
	};

	String mathExp = "";

	private void display() {
		tvCalcDisplay.setText(mathExp);
	}
}