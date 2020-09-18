package View.Reception;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.cliffdevops.alpha.dufleet.R;

import View.Common.AboutActivity;
import View.Common.LoginActivity;
import View.Common.ProfileActivity;

public class ReceptionActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception_dashboad);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CardView Btn1 = findViewById(R.id.btnAssignFleet);
        CardView Btn2 = findViewById(R.id.btnTask);
        CardView Btn3 = findViewById(R.id.btnParcels);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AssignFleetActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddParcelActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_reception, menu);
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

            case R.id.btnLogout:
                pref.edit().putBoolean("logged", false).apply();
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}