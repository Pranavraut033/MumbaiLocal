package com.preons.pranav.mumbailocal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import static com.preons.pranav.util.Constants.ADMIN;
import static com.preons.pranav.util.Constants.DATABASE_NAME;
import static com.preons.pranav.util.Constants.DATABASE_NAME1;
import static com.preons.pranav.util.Constants.FULL_NAME;
import static com.preons.pranav.util.Constants.IS_PLATFORM;
import static com.preons.pranav.util.Constants.copyFiles;
import static com.preons.pranav.util.Constants.preferences;

public class Dashboard extends AppCompatActivity {
    int[] ints = new int[]{
            R.id.ticket, R.id.platform, R.id.history, R.id.create, R.id.create1
    };
    TextView[] textViews = new TextView[ints.length];
    View.OnClickListener[] listeners = new View.OnClickListener[]{
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), SelectionActivity.class));
                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SelectionActivity.class);
                    intent.putExtra(IS_PLATFORM, true);
                    startActivity(intent);
                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   checkPermission();
                    File file = new File(Environment.getExternalStorageDirectory(), "databases");
                    boolean b= true;
                    if(!file.exists())
                        b = file.mkdir();
                    File file1 = new File(file.getPath(), "users.db");
                    if(b) {
                        try {
                            copyFiles(getDatabasePath(DATABASE_NAME), file1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (file1.exists()) {
                        Toast.makeText(getApplicationContext(), "Database store as" + file1.getPath(), Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermission();
                    File file = new File(Environment.getExternalStorageDirectory(), "databases");
                    boolean b= true;
                    if(!file.exists())
                        b = file.mkdir();
                    if(b){
                        File file1 = new File(file.getPath(), "transactions.db");
                        try {
                            copyFiles(getDatabasePath(DATABASE_NAME1), file1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(file1.exists()){
                            Toast.makeText(getApplicationContext(),"Database store as"+file1.getPath(),Toast.LENGTH_LONG).show();
                        }else
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                    }

                }
            }};

    private void checkPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            PERMISSIONS = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    TextView textView;

    @Override
    protected void onResume() {
        super.onResume();
        String s = preferences != null ? preferences.getString(FULL_NAME, "User") : "User";
        textView.setText(MessageFormat.format("Welcome, {0}", s));
        if(s.equals(ADMIN)){
            textViews[3].setVisibility(View.VISIBLE);
            textViews[4].setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        for (int i = 0; i < ints.length; i++) {
            textViews[i] = (TextView) findViewById(ints[i]);
            textViews[i].setOnClickListener(listeners[i]);
        }
        textView = (TextView) findViewById(R.id.full_name);
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null)
            for (String permission : permissions)
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
        return true;
    }
}
