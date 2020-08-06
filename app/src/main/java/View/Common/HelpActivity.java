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

public class HelpActivity extends AppCompatActivity {
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        pref = getSharedPreferences("login", MODE_PRIVATE);


        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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
        if (user.equals("Admin")) {
            Intent i = new Intent(HelpActivity.this, AdminActivity.class);
            startActivity(i);
            finish();
        } else if (user.equals("Driver")) {
            Intent i = new Intent(HelpActivity.this, DriverActivity.class);
            startActivity(i);
            finish();
        } else if (user.equals("Conductor")) {
            Intent i = new Intent(HelpActivity.this, ConductorActivity.class);
            startActivity(i);
            finish();
        } else if (user.equals("Reception")) {
            Intent i = new Intent(HelpActivity.this, ReceptionActivity.class);
            startActivity(i);
            finish();
        } else if (user.equals("Manager")) {
            Intent i = new Intent(HelpActivity.this, DashboardActivity.class);
            startActivity(i);
            finish();
        }
    }
}