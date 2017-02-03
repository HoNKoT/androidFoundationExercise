package jp.honkot.exercize.basic.quiz2;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
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
                Intent broadcastIntent = new Intent(this, MyReceiver.class);
                sendBroadcast(broadcastIntent);
                break;

            case R.id.sendNotificationButton:
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(getString(R.string.notification_title))
                                .setContentText(getString(R.string.notification_summary));
                NotificationManager manager =
                        (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
                break;

            case R.id.showCreditButton:
                break;
        }
    }
}
