package com.cliffdevops.alpha.dufleet.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cliffdevops.alpha.dufleet.Common.AboutActivity;
import com.cliffdevops.alpha.dufleet.Common.HelpActivity;
import com.cliffdevops.alpha.dufleet.Common.LoginActivity;
import com.cliffdevops.alpha.dufleet.Common.ProfileActivity;
import com.cliffdevops.alpha.dufleet.R;

import java.util.Objects;

public class OilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oils);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}