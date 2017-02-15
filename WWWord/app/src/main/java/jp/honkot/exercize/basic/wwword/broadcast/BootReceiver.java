package jp.honkot.exercize.basic.wwword.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import jp.honkot.exercize.basic.wwword.dao.PreferenceDao;
import jp.honkot.exercize.basic.wwword.model.OrmaDatabase;
import jp.honkot.exercize.basic.wwword.model.Preference;
import jp.honkot.exercize.basic.wwword.service.NotificationService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
                || intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
            PreferenceDao dao = new PreferenceDao(OrmaDatabase.builder(context).build());
            Preference pref = dao.findById(1);

            if (pref != null && pref.isNotify()) {
                NotificationService.startService(context);
            }

            return;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
