package com.cliffdevops.alpha.dufleet.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cliffdevops.alpha.dufleet.Common.AboutActivity;
import com.cliffdevops.alpha.dufleet.Common.HelpActivity;
import com.cliffdevops.alpha.dufleet.Common.LoginActivity;
import com.cliffdevops.alpha.dufleet.Common.ProfileActivity;
import com.cliffdevops.alpha.dufleet.R;

public class DriverActivity extends AppCompatActivity {
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboad);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_driver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.btnProfile:
                i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btnAbout:
                i = new Intent(this, AboutActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.btnHelp:
                i = new Intent(this, HelpActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.btnLogout:
                pref.edit().putBoolean("logged", false).apply();
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}