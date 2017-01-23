package jp.honkot.exercize.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jp.honkot.exercize.calculator.sub.MainService;

public class MainActivity extends AppCompatActivity {

    public static final boolean DEBUG = true;
    private MainService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = new MainService(this);
    }
}
