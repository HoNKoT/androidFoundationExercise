package jp.honkot.exercize.basic.quiz2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Just send intent to move to SecondActivity
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent moveToNext = new Intent(context, SecondActivity.class);
        moveToNext.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(moveToNext);
    }
}
