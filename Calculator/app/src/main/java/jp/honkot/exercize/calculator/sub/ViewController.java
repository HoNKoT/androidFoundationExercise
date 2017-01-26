package jp.honkot.exercize.calculator.sub;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        // set font
        SharedPreferences pref = activity.getSharedPreferences(Define.PREF_FILE_NAME, Context.MODE_PRIVATE);
        Typeface tf= Typeface.createFromAsset(activity.getAssets(),
                "fonts/" + pref.getString(Define.PREF_KEY_DISPLAY_FONT, "dotty.ttf"));
        mResult.setTypeface(tf);
        mHistory.setTypeface(tf);
    }

    private void searchViewChildAndSetListener(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View target = parent.getChildAt(i);
            if (target instanceof LinearLayout) {
                searchViewChildAndSetListener((LinearLayout)target);
            } else if (target instanceof TextView) {
                target.setOnClickListener(this);
            }
        }
    }

    public void displayRawNumber(double number, int pointLevel) {
        if (pointLevel > 0) {
            mResult.setText(String.format("%1$." + pointLevel + "f", number));
        } else {
            display(number);
        }
    }

    public void display(double number) {
        mResult.setText(Double.toString(number));
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
