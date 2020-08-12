package View.Conductor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.cliffdevops.alpha.dufleet.R;

import View.Common.AboutActivity;
import View.Common.LoginActivity;
import View.Common.ParcelActivity;
import View.Common.ProfileActivity;

public class ConductorActivity extends AppCompatActivity {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor);
        pref = getSharedPreferences("login", MODE_PRIVATE);
        TextView currentUser = findViewById(R.id.txtUser);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CardView revenue = findViewById(R.id.btnRevenue);
        CardView parcels = findViewById(R.id.btnParcels);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        currentUser.setText("Welcome " + pref.getString("surname", "Null"));

        revenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConductorActivity.this, RevenueActivity.class));
                finish();
            }
        });

        parcels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConductorActivity.this, ParcelActivity.class));
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_conductor, menu);
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
