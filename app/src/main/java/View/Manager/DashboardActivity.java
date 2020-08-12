package View.Manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.cliffdevops.alpha.dufleet.R;

import java.util.Objects;

import Controller.DutyListAdapter;
import View.Common.AboutActivity;
import View.Common.LoginActivity;
import View.Common.ProfileActivity;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences pref;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView currentUser = findViewById(R.id.txtUser);

        this.setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        currentUser.setText("Welcome " + pref.getString("surname", "Null"));

        //defining Card view Buttons
        CardView btnHome1 = findViewById(R.id.btnHome1);
        CardView btnHome2 = findViewById(R.id.btnHome2);
        CardView btnHome3 = findViewById(R.id.btnHome3);
        CardView btnHome4 = findViewById(R.id.btnHome4);
        CardView btnHome5 = findViewById(R.id.btnHome5);
        CardView btnHome6 = findViewById(R.id.btnHome6);
        CardView btnHome7 = findViewById(R.id.btnHome7);
        CardView btnHome8 = findViewById(R.id.btnHome8);
        CardView btnHome9 = findViewById(R.id.btnHome9);

        //onclick listener
        btnHome1.setOnClickListener(this);
        btnHome2.setOnClickListener(this);
        btnHome3.setOnClickListener(this);
        btnHome4.setOnClickListener(this);
        btnHome5.setOnClickListener(this);
        btnHome6.setOnClickListener(this);
        btnHome7.setOnClickListener(this);
        btnHome8.setOnClickListener(this);
        btnHome9.setOnClickListener(this);

        updateDutyList();

    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btnProfile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case R.id.btnAbout:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnLogout:
                pref.edit().putBoolean("logged", false).apply();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHome1:
                startActivity(new Intent(getApplicationContext(), TodoActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnHome2:
                startActivity(new Intent(getApplicationContext(), BookingActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnHome3:
                startActivity(new Intent(getApplicationContext(), RevenueActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnHome4:
                startActivity(new Intent(getApplicationContext(), FuelActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnHome5:
                startActivity(new Intent(getApplicationContext(), Maintenance.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnHome6:
                startActivity(new Intent(getApplicationContext(), FleetActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnHome7:
                startActivity(new Intent(getApplicationContext(), StaffActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnHome8:
                startActivity(new Intent(getApplicationContext(), ReportsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.btnHome9:
                startActivity(new Intent(getApplicationContext(), SubContractsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
        }

    }

    public void updateDutyList() {

        ListView dutyList = findViewById(R.id.dutyListView);
        // Inflate header view
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.table_header, dutyList, false);
        // Add header view to the ListView
        dutyList.addHeaderView(headerView);
        // Get the string array defined in strings.xml file
        String[] items = getResources().getStringArray(R.array.list_items);
        // Create an adapter to bind data to the ListView
        DutyListAdapter adapter = new DutyListAdapter(this, R.layout.table_row, R.id.txtFleetId, items);
        // Bind data to the ListView
        dutyList.setAdapter(adapter);
    }
}