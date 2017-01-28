package jp.honkot.exercize.calculator.sub;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import jp.honkot.exercize.calculator.R;
import jp.honkot.exercize.calculator.utils.Define;

/**
 * Created by hiroki on 2016-11-30.
 */
public class ViewController implements View.OnClickListener {

    MainService.OnUserInputListener mListener;
    TextView mResult, mHistory;

    public ViewController(Activity activity, MainService.OnUserInputListener listener) {
        mListener = listener;
        initLayout(activity);
    }

    protected void initLayout(Activity activity) {
        // get result and history view
        mResult = (TextView)activity.findViewById(R.id.result);
        mHistory = (TextView)activity.findViewById(R.id.history);

        // set listener to each Button
        ViewGroup rootView = (ViewGroup)activity.findViewById(R.id.activity_main);
        searchViewChildAndSetListener(rootView);

        // set font of display
        SharedPreferences pref = activity.getSharedPreferences(Define.PREF_FILE_NAME, Context.MODE_PRIVATE);
        if (!pref.getString(Define.PREF_KEY_DISPLAY_FONT, "NOPE").equals("NOPE")) {
            Typeface tf= Typeface.createFromAsset(activity.getAssets(),
                    "fonts/" + pref.getString(Define.PREF_KEY_DISPLAY_FONT, "NOPE"));
            mResult.setTypeface(tf);
            mHistory.setTypeface(tf);
        } else {
            mResult.setTypeface(Typeface.DEFAULT);
            mHistory.setTypeface(Typeface.DEFAULT);
        }

        // set font of buttons
        String prefButtonsFont = pref.getString(Define.PREF_KEY_BUTTON_FONT, "NOPE");
        Typeface buttonTf = prefButtonsFont.equals("NOPE")
                ? Typeface.DEFAULT
                : Typeface.createFromAsset(activity.getAssets(), "fonts/" + prefButtonsFont);
        searchViewChildAndSetTypeface(rootView, buttonTf);

    }

    private void searchViewChildAndSetListener(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View target = parent.getChildAt(i);
            if (target instanceof LinearLayout) {
                searchViewChildAndSetListener((LinearLayout)target);
            } else if (target instanceof Button) {
                target.setOnClickListener(this);
            }
        }
    }

    private void searchViewChildAndSetTypeface(ViewGroup parent, Typeface tf) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View target = parent.getChildAt(i);
            if (target instanceof LinearLayout) {
                searchViewChildAndSetTypeface((LinearLayout)target, tf);
            } else if (target instanceof Button) {
                ((TextView) target).setTypeface(tf);
            }
        }
    }

    public void displayRawNumber(double number, int pointLevel) {
        if (pointLevel > 0) {
            mResult.setText(String.format("%1$." + pointLevel + "f", number));
        } else {
            display(new BigDecimal(number));
        }
    }

    public void display(BigDecimal number) {
        mResult.setText(number.toPlainString());
//        mResult.setText(NumberFormat.getInstance().format(number));
    }

    public void putHistory(String putString) {
        mHistory.setText(putString);
    }

    public void resetHistory() {
        mHistory.setText("");
    }

    @Override
    public void onClick(View v) {
        MainService.UserInput callback = MainService.UserInput.byViewId(v.getId());
        if (callback != null) {
            mListener.onUserInput(callback);
        }
    }
}
