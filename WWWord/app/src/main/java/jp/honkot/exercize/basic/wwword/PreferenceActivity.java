package jp.honkot.exercize.basic.wwword;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import javax.inject.Inject;

import jp.honkot.exercize.basic.wwword.dao.PreferenceDao;
import jp.honkot.exercize.basic.wwword.model.Preference;
import jp.honkot.exercize.basic.wwword.service.NotificationService;

public class PreferenceActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(
                android.R.id.content, new MySettingFragment()).commit();
    }

    public static class MySettingFragment extends PreferenceFragment {

        @Inject
        PreferenceDao preferenceDao;

        Preference mPref;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ((BaseActivity) getActivity()).getComponent().inject(this);

            addPreferencesFromResource(R.xml.preference);

            // switch for debug flag
            SwitchPreference notifyPref = (SwitchPreference)findPreference(getString(R.string.activity_preference_switch_notification_title));

            mPref = preferenceDao.findById(1);
            notifyPref.setChecked(mPref.isNotify());
            notifyPref.setSummary((mPref.getNotificationInterval() / (60 * 1000)) + " minutes interval");
            notifyPref.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(final android.preference.Preference preference, Object newValue) {
                    boolean checked = ((boolean)newValue);
                    if (checked) {
                        final EditText edit = new EditText(getActivity());
                        edit.setText(Long.toString(Preference.DEFAULT_INTERVAL / (60 * 1000)));
                        edit.setInputType(InputType.TYPE_CLASS_NUMBER);

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Notification interval")
                                .setMessage("Type notification interval minutes")
                                .setView(edit)
                                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        long temp = Long.parseLong(edit.getText().toString());
                                        mPref.setNotificationInterval(temp * 60 * 1000);
                                        preferenceDao.updateNotificatonInterval(mPref);

                                        NotificationService.startService(getActivity());
                                        preference.setSummary((mPref.getNotificationInterval() / (60 * 1000)) + " minutes interval");
                                    }
                                })
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialogInterface) {
                                        ((SwitchPreference)preference).setChecked(false);
                                    }
                                })
                                .show();
                    } else {
                        mPref.setNotificationInterval(0);
                        preferenceDao.updateNotificatonInterval(mPref);
                        NotificationService.stopService(getActivity());
                    }
                    return true;
                }
            });
        }
    }
}
