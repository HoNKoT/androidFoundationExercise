package jp.honkot.exercize.basic.quiz2;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import jp.honkot.exercize.basic.quiz2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.sendBroadcastButton.setOnClickListener(this);
        binding.sendNotificationButton.setOnClickListener(this);
        binding.showCreditButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendBroadcastButton:
                break;
            case R.id.sendNotificationButton:
                break;
            case R.id.showCreditButton:
                break;
        }
    }
}
