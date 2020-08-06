package View.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cliffdevops.alpha.dufleet.R;

import View.Conductor.ConductorActivity;
import View.Driver.DriverActivity;
import View.Manager.DashboardActivity;
import View.Reception.ReceptionActivity;

public class ParcelActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);

        setSupportActionBar(toolbar);
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
        String user = pref.getString("user", "");
        switch (user) {
            case "Admin":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Driver":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Conductor":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), ConductorActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Reception":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), ReceptionActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Manager":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
        }
    }
}