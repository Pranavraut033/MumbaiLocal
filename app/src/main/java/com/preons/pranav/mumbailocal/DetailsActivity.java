package com.preons.pranav.mumbailocal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.preons.pranav.util.Constants.AMOUNT;
import static com.preons.pranav.util.Constants.FROM;
import static com.preons.pranav.util.Constants.LIST;
import static com.preons.pranav.util.Constants.PASSENGERS;
import static com.preons.pranav.util.Constants.TO;

public class DetailsActivity extends AppCompatActivity {

    int i1;
    int i2;
    String[] strings;
    int Class = 0;
    SeekBar seekBar;
    int multiplier = 1;
    TextView textView;
    TextView textView1;
    int a;
    RadioGroup radioGroup;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strings = bundle.getStringArray(LIST);
            i1 = bundle.getInt(TO, 0);
            i2 = bundle.getInt(FROM, 0);
        }
        radioGroup = (RadioGroup) findViewById(R.id.class_no);
        radioGroup.check(R.id.first);
        seekBar = (SeekBar) findViewById(R.id.no_of_passenger);
        textView = (TextView) findViewById(R.id.progress);
        textView1 = (TextView) findViewById(R.id.amount);
        checkBox = (CheckBox) findViewById(R.id.ret_tic);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(multiplier = 1 + progress));
                checkAmount();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                checkAmount();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkAmount();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAmount();
    }

    private void checkAmount() {
        Class = 1 + radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
        Class = Class == 1 ? 3 : 1;
        int r = checkBox.isChecked()?2:1;
        a = Math.abs(i2 - i1);
        if (a < 4) a = 5 * Class * multiplier * r;
        else if (a >= 4 && a < 8) a = 10 * Class * multiplier * r;
        else if (a >= 8 && a < 12) a = 15 * Class * multiplier * r;
        else if (a >= 12 && a < 16) a = 20 * Class * multiplier * r;
        else if (a >= 16 && a < 20) a = 25 * Class * multiplier * r;
        else if (a >= 20 && a < 24) a = 30 * Class * multiplier * r;
        else if (a >= 24 && a < 36) a = 35 * Class * multiplier * r;
        else if (a >= 36) a = 40 * Class * multiplier * r;
        textView1.setText(String.valueOf(a));
    }

    public void done(View view) {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(FROM, i1);
        intent.putExtra(TO, i2);
        intent.putExtra(LIST, strings);
        intent.putExtra(AMOUNT,a);
        intent.putExtra(PASSENGERS, multiplier);
        startActivity(intent);
    }
}
