package jp.honkot.exercize.basic.quiz2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.backButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
