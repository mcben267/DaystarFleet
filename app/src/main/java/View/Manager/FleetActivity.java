package View.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cliffdevops.alpha.dufleet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Controller.FleetAdapter;
import Model.FleetDetails;

public class FleetActivity extends AppCompatActivity implements FleetAdapter.OnFleetClickListener {

    private ProgressBar progressBar;
    private FleetAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<FleetDetails> FleetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.FleetListView);

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        intiRecycleView();

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

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FleetActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.btnRefresh) {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }

        return super.onOptionsItemSelected(item);
    }


    private void intiRecycleView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(null);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FleetAdapter(FleetList, this);
        recyclerView.setAdapter(mAdapter);

        getRevenueDetails();

    }

    public void getRevenueDetails() {
        String url = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_fleet.php";
        StringRequest stringRequest;

        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Test", response);

                        try {
                            if (!response.equals("error")) {
                                JSONArray array = new JSONArray(response);

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);

                                    FleetDetails item = new FleetDetails(

                                            object.getString("fleet_id"),
                                            object.getString("image"),
                                            object.getString("type"),
                                            object.getString("capacity"),
                                            object.getString("reg_no"),
                                            object.getString("commission_date"),
                                            object.getString("chaise_no"),
                                            object.getString("start_mileage")
                                    );

                                    FleetList.add(item);
                                    mAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);

                                }

                            }

                        } catch (JSONException ex) {
                            progressBar.setVisibility(View.GONE);
                            ex.printStackTrace();
                            showToast(ex.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error: Request Failed");
                progressBar.setVisibility(View.GONE);
            }
        });

        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(String name) {

    }
}