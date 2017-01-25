package jp.honkot.exercize.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import jp.honkot.exercize.calculator.sub.MainService;

public class MainActivity extends AppCompatActivity {

    public static final boolean DEBUG = true;
    private MainService mService;
    private MainService.OnUserInputListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = new MainService(this);
        mListener = mService.getListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO shift makes keycode change https://developer.android.com/training/keyboard-input/commands.html
        android.util.Log.e("test", "code" + keyCode);
        if (mListener != null) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_1: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_2: mListener.onUserInput(MainService.UserInput.Btn2); break;
                case KeyEvent.KEYCODE_3: mListener.onUserInput(MainService.UserInput.Btn3); break;
                case KeyEvent.KEYCODE_4: mListener.onUserInput(MainService.UserInput.Btn4); break;
                case KeyEvent.KEYCODE_5: mListener.onUserInput(MainService.UserInput.Btn5); break;
                case KeyEvent.KEYCODE_6: mListener.onUserInput(MainService.UserInput.Btn6); break;
                case KeyEvent.KEYCODE_7: mListener.onUserInput(MainService.UserInput.Btn7); break;
                case KeyEvent.KEYCODE_8: mListener.onUserInput(MainService.UserInput.Btn8); break;
                case KeyEvent.KEYCODE_9: mListener.onUserInput(MainService.UserInput.Btn9); break;
                case KeyEvent.KEYCODE_0: mListener.onUserInput(MainService.UserInput.Btn0); break;
                case KeyEvent.KEYCODE_ENTER: mListener.onUserInput(MainService.UserInput.Equal); break;
                case KeyEvent.KEYCODE_EQUALS: mListener.onUserInput(MainService.UserInput.Equal); break;
                case KeyEvent.KEYCODE_MINUS: mListener.onUserInput(MainService.UserInput.Subtraction); break;
                case KeyEvent.KEYCODE_PLUS: mListener.onUserInput(MainService.UserInput.Addition); break;
                case KeyEvent.KEYCODE_STAR: mListener.onUserInput(MainService.UserInput.Multiplication); break;
                case KeyEvent.KEYCODE_SLASH: mListener.onUserInput(MainService.UserInput.Division); break;
                case KeyEvent.KEYCODE_PERIOD: mListener.onUserInput(MainService.UserInput.Dot); break;
                case KeyEvent.KEYCODE_DEL: mListener.onUserInput(MainService.UserInput.Clear); break;

                case KeyEvent.KEYCODE_NUMPAD_1: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_2: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_3: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_4: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_5: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_6: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_7: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_8: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_9: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_0: mListener.onUserInput(MainService.UserInput.Btn1); break;
                case KeyEvent.KEYCODE_NUMPAD_COMMA: mListener.onUserInput(MainService.UserInput.Dot); break;
                case KeyEvent.KEYCODE_NUMPAD_ADD: mListener.onUserInput(MainService.UserInput.Addition); break;
                case KeyEvent.KEYCODE_NUMPAD_DOT: mListener.onUserInput(MainService.UserInput.Dot); break;
                case KeyEvent.KEYCODE_NUMPAD_EQUALS: mListener.onUserInput(MainService.UserInput.Equal); break;
                case KeyEvent.KEYCODE_NUMPAD_DIVIDE: mListener.onUserInput(MainService.UserInput.Division); break;
                case KeyEvent.KEYCODE_NUMPAD_MULTIPLY: mListener.onUserInput(MainService.UserInput.Multiplication); break;
                case KeyEvent.KEYCODE_NUMPAD_SUBTRACT: mListener.onUserInput(MainService.UserInput.Subtraction); break;
                case KeyEvent.KEYCODE_NUMPAD_ENTER: mListener.onUserInput(MainService.UserInput.Equal); break;

                // for debug
                case KeyEvent.KEYCODE_F1:
                case KeyEvent.KEYCODE_A:
                    mListener.onUserInput(MainService.UserInput.Addition); break;
                case KeyEvent.KEYCODE_F2:
                case KeyEvent.KEYCODE_S:
                    mListener.onUserInput(MainService.UserInput.Subtraction); break;
                case KeyEvent.KEYCODE_F3:
                case KeyEvent.KEYCODE_M:
                    mListener.onUserInput(MainService.UserInput.Multiplication); break;
                case KeyEvent.KEYCODE_F4:
                case KeyEvent.KEYCODE_D:
                    mListener.onUserInput(MainService.UserInput.Division); break;
                case KeyEvent.KEYCODE_F11:
                case KeyEvent.KEYCODE_O:
                    mListener.onUserInput(MainService.UserInput.Switch); break;
                case KeyEvent.KEYCODE_F12:
                case KeyEvent.KEYCODE_P:
                    mListener.onUserInput(MainService.UserInput.Percentage); break;
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
