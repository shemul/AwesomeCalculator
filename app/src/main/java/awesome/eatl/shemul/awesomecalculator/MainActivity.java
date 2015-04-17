package awesome.eatl.shemul.awesomecalculator;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;



public class MainActivity extends Activity implements OnClickListener {

    private TextView calculatorDisplay;
    private static final String DIGITS = "0123456789.";

    private Boolean userIsInTheMiddleOfTypingANumber = false;

    DecimalFormat df = new DecimalFormat("@###########");

    CalculatorBrain brain;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        brain = new CalculatorBrain();

        calculatorDisplay = (TextView) findViewById(R.id.textView1);

        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);


        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSubtract).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonToggleSign).setOnClickListener(this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);
        findViewById(R.id.buttonClearMemory).setOnClickListener(this);
        findViewById(R.id.buttonAddToMemory).setOnClickListener(this);
        findViewById(R.id.buttonSubtractFromMemory).setOnClickListener(this);
        findViewById(R.id.buttonRecallMemory).setOnClickListener(this);


    }

    // @Override
    public void onClick(View view) {


        String buttonPressed = ((Button) view).getText().toString();
        // String digits = "0123456789.";

        Animation abc_slide_out_top = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_out_top);
        Animation abc_slide_in_bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_in_bottom);
        Animation btnanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_in_top);

        view.startAnimation(btnanim);


        if (DIGITS.contains(buttonPressed)) {
            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {
                calculatorDisplay.append(buttonPressed);
            } else {
                calculatorDisplay.setText(buttonPressed);
                userIsInTheMiddleOfTypingANumber = true;
            }
        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {

                brain.setOperand(Double.parseDouble(calculatorDisplay.getText().toString()));

                userIsInTheMiddleOfTypingANumber = false;
            }


            calculatorDisplay.startAnimation(abc_slide_out_top);
            brain.performOperation(buttonPressed);
            calculatorDisplay.startAnimation(abc_slide_in_bottom);

            calculatorDisplay.setText(df.format(brain.getResult()));


        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", brain.getResult());
        outState.putDouble("MEMORY", brain.getMemory());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        brain.setOperand(savedInstanceState.getDouble("OPERAND"));
        brain.setMemory(savedInstanceState.getDouble("MEMORY"));
        calculatorDisplay.setText(df.format(brain.getResult()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
   //     getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}