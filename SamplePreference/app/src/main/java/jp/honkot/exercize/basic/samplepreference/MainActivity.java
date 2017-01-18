package jp.honkot.exercize.basic.samplepreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_FILE = "jp.honkot.exercize.basic.samplepreference.Sample";
    public static final String PREF_BLACK = "theme_black";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBlackTheme()) {
            setTheme(android.R.style.Theme_Black);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_main);
    }

    private boolean isBlackTheme() {
        SharedPreferences sp = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        return sp.getBoolean(PREF_BLACK, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sample:
                startActivityForResult(new Intent(this, ChangeThemeActivity.class), ChangeThemeActivity.REQ_DONE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ChangeThemeActivity.REQ_DONE) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
