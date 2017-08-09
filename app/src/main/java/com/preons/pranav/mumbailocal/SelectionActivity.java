package com.preons.pranav.mumbailocal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.preons.pranav.util.Constants;

import static com.preons.pranav.util.Constants.AMOUNT;
import static com.preons.pranav.util.Constants.CENTRAL;
import static com.preons.pranav.util.Constants.FROM;
import static com.preons.pranav.util.Constants.HARBOUR;
import static com.preons.pranav.util.Constants.IS_PLATFORM;
import static com.preons.pranav.util.Constants.LINE;
import static com.preons.pranav.util.Constants.LIST;
import static com.preons.pranav.util.Constants.STATION;
import static com.preons.pranav.util.Constants.TO;
import static com.preons.pranav.util.Constants.WESTERN;
import static com.preons.pranav.util.Constants.dDime;
import static com.preons.pranav.util.Constants.reString;

public class SelectionActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;

    private String[] dString = WESTERN;
    private int line;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.western:
                    listView.setAdapter(adapter.setStationList(WESTERN));
                    textView.setText(R.string.western);
                    dString = WESTERN;
                    line = 0;
                    return true;
                case R.id.central:
                    listView.setAdapter(adapter.setStationList(CENTRAL));
                    textView.setText(R.string.central);
                    dString = CENTRAL;
                    line = 1;
                    return true;
                case R.id.harbour:
                    listView.setAdapter(adapter.setStationList(HARBOUR));
                    textView.setText(R.string.harbour);
                    dString = HARBOUR;
                    line = 2;
                    return true;
            }
            return false;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next:
                if(b){
                    Intent intent = new Intent(this,PaymentActivity.class);
                    intent.putExtra(AMOUNT,10);
                    intent.putExtra(IS_PLATFORM, b);
                    intent.putExtra(LIST, dString);
                    intent.putExtra(STATION, s);
                    startActivity(intent);
                    return super.onOptionsItemSelected(item);
                }
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra(FROM, from);
                intent.putExtra(TO, to);
                intent.putExtra(LINE, line);
                intent.putExtra(LIST, dString);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clicks(View view) {
        switch (view.getId()) {
            case R.id.from:
                inter(textView1, textView2);
                isFrom = true;
                break;
            case R.id.to:
                inter(textView2, textView1);
                isFrom = false;
                break;
        }
    }

    private void inter(TextView to, TextView from) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            to.animate().translationZ(dDime).scaleY(1.1f);
            from.animate().translationZ(0f).scaleY(1);
        } else {
            from.animate().scaleY(1.1f);
            to.animate().scaleY(1);
        }
    }
    TextView textView1;
    TextView textView2;
    TextView textView3;
    private boolean isFrom = true;
    CustomAdapter adapter;
    BottomNavigationView navigation;
    Menu menu;
    private int from;
    private int to;
    private int s;
    Bundle bundle;
    private boolean b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        bundle = getIntent().getExtras();
        textView1 = (TextView) findViewById(R.id.from);
        textView2 = (TextView) findViewById(R.id.to);
        textView3 = (TextView) findViewById(R.id.platform);
        inter(textView1, textView2);
        if (bundle != null && (b = bundle.getBoolean(IS_PLATFORM, false))) {
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textView3.animate().translationZ(dDime).scaleY(1.1f);
            } else
                textView3.animate().scaleY(1.1f);
        }
        adapter = new CustomAdapter(this);
        textView = (TextView) findViewById(R.id.message);
        listView = (ListView) findViewById(R.id.station_list);
        listView.setAdapter(adapter.setStationList(WESTERN));
        adapter.setClickListener(new CustomAdapter.ClickListener() {
            @Override
            public void clickEvent(int position) {
                if (b) {
                    textView3.setText(reString(dString[s = position]));
                    menu.getItem(0).setEnabled(!textView3.getText().toString().isEmpty());
                    return;
                }
                if (isFrom) {
                    textView1.setText(reString(dString[from = position]));
                    inter(textView2, textView1);
                } else {
                    inter(textView1, textView2);
                    textView2.setText(reString(dString[to = position]));
                }
                isFrom = !isFrom;
                for (int i = 0; i < 3; i++) navigation.getMenu().getItem(i).setEnabled(i == line);
                menu.getItem(0).setEnabled(!textView1.getText().toString().isEmpty()
                        && !textView2.getText().toString().isEmpty());
            }
        });
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
