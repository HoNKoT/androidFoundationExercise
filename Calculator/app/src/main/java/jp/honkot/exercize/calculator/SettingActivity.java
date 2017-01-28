package jp.honkot.exercize.calculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import jp.honkot.exercize.calculator.utils.Define;

public class SettingActivity extends AppCompatActivity {

    MyPreferenceFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = new MyPreferenceFragment();
        getFragmentManager().beginTransaction().replace(
                android.R.id.content, fragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set Click Listener
        fragment.findPreference(getString(R.string.pref_display_font))
                .setOnPreferenceClickListener(mDisplayFontClickListener);

        // Set Click Listener
        fragment.findPreference(getString(R.string.pref_button_font))
                .setOnPreferenceClickListener(mButtonFontClickListener);
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            // Display App version.
            Preference about = findPreference(getString(R.string.pref_about));
            about.setTitle(R.string.app_name);
            about.setSummary(" version " + BuildConfig.VERSION_NAME);

            SharedPreferences pref = getActivity().getSharedPreferences(Define.PREF_FILE_NAME, Context.MODE_PRIVATE);
            Preference fontDisplay = findPreference(getString(R.string.pref_display_font));
            fontDisplay.setSummary(pref.getString(Define.PREF_KEY_DISPLAY_FONT, "NOPE"));

            Preference fontButton = findPreference(getString(R.string.pref_button_font));
            fontButton.setSummary(pref.getString(Define.PREF_KEY_BUTTON_FONT, "NOPE"));
        }
    }

    private Preference.OnPreferenceClickListener mDisplayFontClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            final String[] items = {"NOPE", "IndieFlower.ttf", "VT323-Regular.ttf"};
            new AlertDialog.Builder(SettingActivity.this)
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences pref = getSharedPreferences(Define.PREF_FILE_NAME, Context.MODE_PRIVATE);
                            pref.edit().putString(Define.PREF_KEY_DISPLAY_FONT, items[which]).commit();

                            Preference fontDisplay = fragment.findPreference(getString(R.string.pref_display_font));
                            fontDisplay.setSummary(items[which]);
                        }
                    }).show();
            return false;
        }
    };

    private Preference.OnPreferenceClickListener mButtonFontClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            final String[] items = {"NOPE", "IndieFlower.ttf", "VT323-Regular.ttf"};
            new AlertDialog.Builder(SettingActivity.this)
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences pref = getSharedPreferences(Define.PREF_FILE_NAME, Context.MODE_PRIVATE);
                            pref.edit().putString(Define.PREF_KEY_BUTTON_FONT, items[which]).commit();

                            Preference fontButton = fragment.findPreference(getString(R.string.pref_button_font));
                            fontButton.setSummary(items[which]);
                        }
                    }).show();
            return false;
        }
    };
}
