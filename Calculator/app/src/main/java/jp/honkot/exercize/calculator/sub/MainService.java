package jp.honkot.exercize.calculator.sub;

import android.app.Activity;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

import jp.honkot.exercize.calculator.MainActivity;
import jp.honkot.exercize.calculator.R;

import static jp.honkot.exercize.calculator.sub.MainService.UserInput.Division;
import static jp.honkot.exercize.calculator.sub.MainService.UserInput.Equal;
import static jp.honkot.exercize.calculator.sub.MainService.UserInput.Multiplication;

/**
 * Created by hiroki on 2016-11-30.
 */
public class MainService {

    ViewController mViews;

    private final static double DEFAULT = 0.0d;
    private double inputNumber = DEFAULT;
    private final static int ZERO = 0;
    private int dotInputMode = ZERO;

    private UserInput mLastInput;
    private UserInput mCurrentInput;
    private final String LF = System.getProperty("line.separator");

    private static class HistoryController {
        private static ArrayList<History> histories = new ArrayList<>();

        public static boolean isEmpty() { return histories.size() == 0;}
        public static void add(double number, int pointLevel) {
            int lastIndex = histories.size() - 1;
            if (!isEmpty() && isNumberLast()) {
                histories.remove(lastIndex);
            }
            histories.add(new History(number, pointLevel));
        }
        public static void add(UserInput command) {
            int lastIndex = histories.size() - 1;
            if (!isEmpty() && isCommandLast()) {
                histories.remove(lastIndex);
            }
            histories.add(new History(command));
        }
        public static boolean isCommandLast() {
            return !isEmpty() && !histories.get(histories.size() - 1).isNumber();
        }
        public static boolean isNumberLast() {
            return !isEmpty() && histories.get(histories.size() - 1).isNumber();
        }
        public static void clear() {
            histories = new ArrayList<>();
        }
        public static boolean isEqualLast() {
            return !isEmpty() && histories.get(histories.size() - 1).command.equals(Equal);
        }
        public static String getHistoryString() {
            StringBuffer buf = new StringBuffer();

            for (History history : histories) {
                if (history.isNumber()) {
                    if (history.pointLevel > 0) {
                        buf.append(String.format("%1$." + history.pointLevel + "f", history.number));
                    } else {
                        buf.append(new BigDecimal(history.number).toPlainString());
                    }

                } else {
                    switch (history.command) {
                        case Addition: buf.append(" + "); break;
                        case Subtraction: buf.append(" - "); break;
                        case Multiplication: buf.append(" * "); break;
                        case Division: buf.append(" / "); break;
                        case Equal: buf.append(" = "); break;
                    }
                }
            }
            return buf.toString();
        }

        public static BigDecimal calculate() {
            BigDecimal ret = new BigDecimal(DEFAULT);

            ArrayList<History> tempHistries = copy();

            // At first, calculate * and /
            for (int i = 0; i < tempHistries.size(); i++) {
                History history = tempHistries.get(i);

                if (history.isNumber()) {
                    int thisIndex = tempHistries.indexOf(history);

                    if (tempHistries.size() >= thisIndex + 3) {
                        // has next (command) and next (number)
                        UserInput command = tempHistries.get(thisIndex + 1).command;

                        if (command.equals(Multiplication) || command.equals(Division)) {
                            History targetNumber = tempHistries.get(thisIndex + 2);
                            BigDecimal targetBigDecimal = targetNumber.numberBigDecimal;

                            // calculate and remove them
                            int newPointLevel;
                            switch (command) {
                                case Multiplication:
                                    history.numberBigDecimal =
                                            history.numberBigDecimal.multiply(targetBigDecimal);

                                    // calculate point level
                                    newPointLevel = history.pointLevel + targetNumber.pointLevel;
                                    if (newPointLevel > history.numberBigDecimal.scale()) {
                                        newPointLevel = history.numberBigDecimal.scale();
                                    }
                                    history.numberBigDecimal = history.numberBigDecimal.setScale(newPointLevel, BigDecimal.ROUND_HALF_UP);

                                    // set new point level
                                    history.pointLevel = newPointLevel;
                                    break;
                                case Division:
                                    if (targetBigDecimal.doubleValue() != DEFAULT) {
                                        history.numberBigDecimal =
                                                history.numberBigDecimal.divide(targetBigDecimal, 20, BigDecimal.ROUND_DOWN);
                                    }

                                    // set new point level
                                    history.pointLevel = history.numberBigDecimal.scale();
                                    break;
                                default:
                                    continue;
                            }
                            tempHistries.remove(thisIndex + 2);
                            tempHistries.remove(thisIndex + 1);

                            // repeat this position
                            i--;
                        }
                    }
                }
            }

            // finally, calculate + and -
            int retPointLevel = 0;
            for (History history : tempHistries) {
                if (history.isNumber()) {
                    // number input
                    if (tempHistries.indexOf(history) == 0) {
                        // initialize as first number
                        ret = history.numberBigDecimal;
                        retPointLevel = history.pointLevel;

                    } else {
                        // get command for calculation and boss.
                        UserInput command =
                                tempHistries.get(tempHistries.indexOf(history) - 1).command;

                        int newPointLevel = 0;
                        switch (command) {
                            case Addition:
                                ret = ret.add(history.numberBigDecimal);
                                break;
                            case Subtraction:
                                ret = ret.subtract(history.numberBigDecimal);
                                break;
                        }

                        // calculate point level
                        newPointLevel = Math.max(history.pointLevel, retPointLevel);
                        if (newPointLevel > ret.scale()) {
                            newPointLevel = ret.scale();
                        }
                        retPointLevel = newPointLevel;
                        ret = ret.setScale(retPointLevel, BigDecimal.ROUND_HALF_UP);
                    }

                }
            }

            return ret;
        }

        private static ArrayList<History> copy() {
            ArrayList<History> ret = new ArrayList<>();
            for (History history : histories) {
                ret.add(history.copy());
            }
            return ret;
        }

        public static class History {
            BigDecimal numberBigDecimal;
            double number = DEFAULT;
            int pointLevel = 0;
            UserInput command;

            History(double number, int pointLevel) {
                this.number = number;
                this.pointLevel = pointLevel;
                numberBigDecimal = new BigDecimal(number);
            }
            History(UserInput command) { this.command = command;}
            public boolean isNumber() { return number != DEFAULT;}

            public History copy() {
                if (isNumber()) {
                    return new History(number, pointLevel);
                } else {
                    return new History(command);
                }
            }

            @Override
            public String toString() {
                final StringBuffer sb = new StringBuffer("History{");
                sb.append("number=").append(number);
                sb.append(", pointLevel=").append(pointLevel);
                sb.append(", command=").append(command);
                sb.append('}');
                return sb.toString();
            }
        }
    }

    public MainService(Activity activity) {
        // initialize views
        mViews = new ViewController(activity, mListener);
    }

    public void initLayout(Activity activity) {
        mViews.initLayout(activity);
        display();
    }

    private void display() {
        if (!HistoryController.isCommandLast()) {
            mViews.displayRawNumber(inputNumber,
                    isDotInputMode() ? dotInputMode - 1 : 0);
        } else {
            if (HistoryController.isEqualLast()) {
                mViews.display(HistoryController.calculate());
            } else {
                mViews.displayRawNumber(inputNumber,
                        isDotInputMode() ? dotInputMode - 1 : 0);
            }
        }
        mViews.putHistory(HistoryController.getHistoryString());
    }

    public enum UserInput {
        Btn1(1),
        Btn2(2),
        Btn3(3),
        Btn4(4),
        Btn5(5),
        Btn6(6),
        Btn7(7),
        Btn8(8),
        Btn9(9),
        Btn0(0),
        Addition,
        Subtraction,
        Multiplication,
        Division,
        Dot,
        Clear,
        Equal,
        Switch,
        Percentage;

        private int number = -1;

        UserInput() {}

        UserInput(int number) {
            this.number = number;
        }

        public boolean isNumber() {
            return number != -1;
        }

        public static UserInput byNumber(int number) {
            for (UserInput ret : values()) {
                if (ret.number == number) {
                    return ret;
                }
            }
            return null;
        }

        public static UserInput byViewId(int viewId) {
            switch (viewId) {
                case R.id.button_0: return Btn0;
                case R.id.button_1: return Btn1;
                case R.id.button_2: return Btn2;
                case R.id.button_3: return Btn3;
                case R.id.button_4: return Btn4;
                case R.id.button_5: return Btn5;
                case R.id.button_6: return Btn6;
                case R.id.button_7: return Btn7;
                case R.id.button_8: return Btn8;
                case R.id.button_9: return Btn9;
                case R.id.button_plus: return Addition;
                case R.id.button_minus: return Subtraction;
                case R.id.button_multi: return Multiplication;
                case R.id.button_divide: return Division;
                case R.id.button_clear: return Clear;
                case R.id.button_equals: return Equal;
                case R.id.button_switch: return Switch;
                case R.id.button_percent: return Percentage;
                case R.id.button_dot: return Dot;
            }
            return null;
        }
    }

    public interface OnUserInputListener {
        void onUserInput(MainService.UserInput input);
    }

    protected OnUserInputListener mListener = new OnUserInputListener() {
        @Override
        public void onUserInput(UserInput input) {
            execute(input);
        }
    };

    public OnUserInputListener getListener() {
        return mListener;
    }

    // region execute start --------------------------------------

    private void execute(UserInput input) {
        mLastInput = mCurrentInput;
        mCurrentInput = input;
        if (MainActivity.DEBUG) {
            Log.d(getClass().getSimpleName(), ">>>>> execute start " + input);
            dump();
        }

        switch (input) {
            // numbers
            case Btn1:
            case Btn2:
            case Btn3:
            case Btn4:
            case Btn5:
            case Btn6:
            case Btn7:
            case Btn8:
            case Btn9:
            case Btn0:
                if (HistoryController.isEqualLast()) {
                    // after equal, clear first!
                    HistoryController.clear();
                }
                funcNumber(input.number);
                break;

            // commands for calculation
            case Equal:
                if (!HistoryController.isEmpty()) {
                    if (HistoryController.isCommandLast() && inputNumber != DEFAULT) {
                        HistoryController.add(inputNumber,
                                isDotInputMode() ? dotInputMode - 1 : 0);

                    }
                    HistoryController.add(input);
                    inputNumber = DEFAULT;
                    stopDotInputMpde();
                }
                break;
            case Division:
                if (inputNumber == DEFAULT) {
                    // ignore error case
                    if (HistoryController.isCommandLast()) {
                        // just change command to last one
                        HistoryController.add(input);
                    }
                } else {
                    HistoryController.add(inputNumber,
                            isDotInputMode() ? dotInputMode - 1 : 0);
                    HistoryController.add(input);
                    inputNumber = DEFAULT;
                }
                stopDotInputMpde();
                break;
            case Addition:
            case Subtraction:
            case Multiplication:
                if (HistoryController.isEmpty()
                        || (HistoryController.isEqualLast() && inputNumber != DEFAULT)
                        || (HistoryController.isCommandLast() && inputNumber != DEFAULT)) {
                    HistoryController.add(inputNumber,
                            isDotInputMode() ? dotInputMode - 1 : 0);
                    inputNumber = DEFAULT;
                }
                HistoryController.add(input);
                stopDotInputMpde();
                break;

            // action immediately
            case Clear:
                funcClear();
                break;
            case Dot:
                if (!HistoryController.isEqualLast()) {
                    startDotInputMpde();
                }
                break;
            case Switch:
                if (!HistoryController.isEqualLast()) {
                    funcSwitch();
                }
                break;
            case Percentage:
                if (!HistoryController.isEqualLast()) {
                    funcPercentage();
                }
                break;
        }
        display();
        if (MainActivity.DEBUG) {
            dump();
            Log.d(getClass().getSimpleName(), "<<<<< execute end " + input);
        }
    }

    private boolean isDotInputMode() {
        return dotInputMode > ZERO;
    }

    private void startDotInputMpde() {
        if (!isDotInputMode()) {
            dotInputMode = 1;
        }
    }

    private void stopDotInputMpde() {
        dotInputMode = ZERO;
    }

    private void funcNumber(int number) {
        BigDecimal bigInputNumber = new BigDecimal(inputNumber);
        BigDecimal bigNumber = new BigDecimal(getExactDoubleNumber(number));
        if (isDotInputMode()) {
            // inputNumber = inputNumber + number / temp;
            inputNumber = bigInputNumber.add(bigNumber.movePointLeft(dotInputMode))
                    .setScale(dotInputMode, BigDecimal.ROUND_DOWN).doubleValue();
            dotInputMode++;
        } else {
            BigDecimal inputtedNumber = bigInputNumber.multiply(new BigDecimal(10.0d));
            if (inputNumber >= 0) {
                // inputNumber = inputNumber * 10.0d + number;
                inputNumber = inputtedNumber.add(bigNumber).doubleValue();
            } else {
                // inputNumber = inputNumber * 10.0d - number;
                inputNumber = inputtedNumber.subtract(bigNumber).doubleValue();
            }
        }
    }

    private double getExactDoubleNumber(int number) {
        switch (number) {
            case 0: return 0.0d;
            case 1: return 1.0d;
            case 2: return 2.0d;
            case 3: return 3.0d;
            case 4: return 4.0d;
            case 5: return 5.0d;
            case 6: return 6.0d;
            case 7: return 7.0d;
            case 8: return 8.0d;
            case 9: return 9.0d;
            default: return (double)number;
        }
    }

    private void funcClear() {
        inputNumber = DEFAULT;
        stopDotInputMpde();
        HistoryController.clear();
    }

    private void funcSwitch() {
        inputNumber *= -1;
    }

    private void funcPercentage() {
        HistoryController.clear();
        BigDecimal bigDecimal = new BigDecimal(inputNumber);
        BigDecimal newInput = bigDecimal.divide(new BigDecimal(100.0d));

        if (!isDotInputMode()) {
            dotInputMode = 3;
        } else {
            dotInputMode += 2;
        }
        inputNumber = newInput.setScale(dotInputMode, BigDecimal.ROUND_DOWN).doubleValue();
    }

    // region execute end --------------------------------------

    private void dump() {
        // history
        StringBuffer buf = new StringBuffer();
        buf.append("====== Print Information ======").append(LF);
        buf.append("isEmpty       = ").append(HistoryController.isEmpty()).append(LF);
        buf.append("isEqualLast   = ").append(HistoryController.isEqualLast()).append(LF);
        buf.append("isCommandLast = ").append(HistoryController.isCommandLast()).append(LF);
        buf.append("isNumberLast  = ").append(HistoryController.isNumberLast()).append(LF);
        for (HistoryController.History history : HistoryController.histories) {
            buf.append(history).append(", isNumber=").append(history.isNumber()).append(LF);
        }

        // current Numbers
        buf.append(LF);
        buf.append("inputNumber   = ").append(inputNumber).append(LF);
        buf.append("dotInputMode  = ").append(dotInputMode).append(LF);
        buf.append("mLastInput    = ").append(mLastInput).append(LF);
        buf.append("mCurrentInput = ").append(mCurrentInput).append(LF);

        Log.d(getClass().getSimpleName(), buf.toString());
    }

    @Override
    public String toString() {
        return "MainService{" +
                " inputNumber=" + inputNumber +
                ", dotInputMode=" + dotInputMode +
                ", mLastInput=" + mLastInput +
                ", mCurrentInput=" + mCurrentInput +
                ", mListener=" + mListener +
                '}';
    }
}
