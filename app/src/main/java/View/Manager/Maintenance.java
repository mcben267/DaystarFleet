package View.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cliffdevops.alpha.dufleet.R;

import java.util.Objects;

import Controller.MaintenanceAdapter;

public class Maintenance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        updateMaintenanceList();

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

    public void updateMaintenanceList() {

        ListView MaintenanceList = findViewById(R.id.scheduleView);
        // Inflate header view
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.maintenance_table_header, MaintenanceList, false);
        // Add header view to the ListView
        MaintenanceList.addHeaderView(headerView);
        // Get the string array defined in strings.xml file
        String[] items = getResources().getStringArray(R.array.maintenance_list_items);
        // Create an adapter to bind data to the ListView
        MaintenanceAdapter adapter = new MaintenanceAdapter(this, R.layout.maintenance_table_row, R.id.txtFleetId, items);
        // Bind data to the ListView
        MaintenanceList.setAdapter(adapter);
    }

}