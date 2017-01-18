package jp.honkot.exercize.basic.samplepreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import static jp.honkot.exercize.basic.samplepreference.MainActivity.PREF_BLACK;
import static jp.honkot.exercize.basic.samplepreference.MainActivity.PREF_FILE;

public class ChangeThemeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQ_DONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_theme);

        CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
        cb.setChecked(isBlackTheme());
        findViewById(R.id.button).setOnClickListener(this);
    }

    private boolean isBlackTheme() {
        SharedPreferences sp = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        return sp.getBoolean(PREF_BLACK, false);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sp = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
        sp.edit().putBoolean(PREF_BLACK, cb.isChecked()).commit();

        setResult(REQ_DONE);
        finish();
    }
}
