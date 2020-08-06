package View.Driver;

import android.annotation.SuppressLint;
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

import java.util.Objects;

import View.Common.AboutActivity;
import View.Common.HelpActivity;
import View.Common.LoginActivity;
import View.Common.ParcelActivity;
import View.Common.ProfileActivity;

public class DriverActivity extends AppCompatActivity {
    private SharedPreferences pref;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboad);
        pref = getSharedPreferences("login", MODE_PRIVATE);
        TextView currentUser = findViewById(R.id.txtUser);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CardView trip = findViewById(R.id.btnTrip);
        CardView parcels = findViewById(R.id.btnParcels);
        CardView mileage = findViewById(R.id.btMileage);
        CardView todo_list = findViewById(R.id.btnTodo);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        currentUser.setText("Welcome " + pref.getString("surname", "Null"));

        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverActivity.this, TripActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        parcels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverActivity.this, ParcelActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mileage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverActivity.this, MileageActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

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