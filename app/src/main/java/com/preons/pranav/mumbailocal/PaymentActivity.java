package com.preons.pranav.mumbailocal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.preons.pranav.util.DBHelper;
import com.preons.pranav.util.DBHelper2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.preons.pranav.util.Constants.ADMIN;
import static com.preons.pranav.util.Constants.AMOUNT;
import static com.preons.pranav.util.Constants.FROM;
import static com.preons.pranav.util.Constants.IS_PLATFORM;
import static com.preons.pranav.util.Constants.LIST;
import static com.preons.pranav.util.Constants.PASSENGERS;
import static com.preons.pranav.util.Constants.STATION;
import static com.preons.pranav.util.Constants.TO;
import static com.preons.pranav.util.Constants.USER_COLUMN_ID;
import static com.preons.pranav.util.Constants.USER_NAME;
import static com.preons.pranav.util.Constants.VERSION;
import static com.preons.pranav.util.Constants.preferences;
import static com.preons.pranav.util.Constants.reString;
import static java.text.MessageFormat.format;

public class PaymentActivity extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    LinearLayout linearLayout;
    private Bundle bundle;
    String s;
    private String s1;
    private boolean b;
    private String s3;
    private int amt = 10;
    private DBHelper2 helper;
    private DBHelper helper1;
    private String p;
    RadioGroup radioGroup;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        textView = (TextView) findViewById(R.id.to);
        textView1 = (TextView) findViewById(R.id.from);
        textView2 = (TextView) findViewById(R.id.amount);
        textView3 = (TextView) findViewById(R.id.for_s);
        linearLayout = (LinearLayout) findViewById(R.id.ticket_content);
        bundle = getIntent().getExtras();
        if (preferences != null)
            helper = new DBHelper2(this, preferences.getString(USER_NAME, ADMIN),
                    preferences.getInt(VERSION, 1));
        helper1 = new DBHelper(this);
        radioGroup = (RadioGroup) findViewById(R.id.methods);
        radioGroup.check(R.id.cCard);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (bundle != null) {
            textView2.setText(format("₹ {0}", bundle.getInt(AMOUNT, 0)));
            String[] strings = bundle.getStringArray(LIST);
            if (b = bundle.getBoolean(IS_PLATFORM, false)) {
                linearLayout.setVisibility(View.GONE);
                textView3.setVisibility(View.VISIBLE);
                if (strings != null)
                    textView3.setText(s3 = reString(strings[bundle.getInt(STATION, 0)]));
                return;
            }
            p = String.valueOf(bundle.getInt(PASSENGERS, 1));
            if (strings != null) {
                textView.setText(s = reString(strings[bundle.getInt(TO, 0)]));
                textView1.setText(s1 = reString(strings[bundle.getInt(FROM, 0)]));
            }
            textView2.setText(format("₹ {0}", amt = bundle.getInt(AMOUNT, 10)));
        }
    }

    public void done(View view) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Processing Payment");
        dialog.show();
        Long l = Long.parseLong(dateFormat.format(new Date()));
        String ds = ":" + l % 100;
        l /= 100;
        ds = ":" + l % 100 + ds;
        l /= 100;
        ds = " " + l % 100 + ds;
        l /= 100;
        ds = "/" + l % 10000 + ds;
        l /= 10000;
        ds = "/" + l % 100 + ds;
        l /= 100;
        ds = "" + l % 100 + ds;
        final String finalS = ds;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                if (preferences != null) {
                    int id = preferences.getInt(USER_COLUMN_ID, 0);
                    if (helper1.getBal(id) > amt)
                        helper1.updateUser(id, helper1.getBal(id) - amt);
                    else {
                        Toast.makeText(getApplicationContext(), "Not enough balance", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                if (b)
                    helper.insertTransactions("", "", s3, String.valueOf(amt), radioButton.getText().toString(), String.valueOf(1), finalS);
                else
                    helper.insertTransactions(s, s1, "", String.valueOf(amt), radioButton.getText().toString(), p, finalS);

                Toast.makeText(getApplicationContext(), "Payment successful", Toast.LENGTH_LONG).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, 5000);
    }
}
