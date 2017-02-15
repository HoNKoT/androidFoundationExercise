package jp.honkot.exercize.basic.wwword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.CompoundButton;

import javax.inject.Inject;

import jp.honkot.exercize.basic.wwword.dao.PreferenceDao;
import jp.honkot.exercize.basic.wwword.databinding.ActivityPreferenceBinding;
import jp.honkot.exercize.basic.wwword.model.Preference;
import jp.honkot.exercize.basic.wwword.service.NotificationService;

public class PreferenceActivity extends BaseActivity {

    private ActivityPreferenceBinding binding;
    private Preference pref;

    @Inject
    PreferenceDao preferenceDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        pref = preferenceDao.findById(1);
        if (pref != null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_preference);
            binding.checkbox.setChecked(pref.isNotify());
        }

        binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pref.setNotifycationInterval(isChecked ? 10000 : 0);
                preferenceDao.update(pref);

                if (isChecked) NotificationService.startService(getApplicationContext());
                else NotificationService.stopService(getApplicationContext());
            }
        });
    }
}
