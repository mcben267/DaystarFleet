package View.Manager;

import android.content.Intent;
import android.content.SharedPreferences;
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

import Controller.RevenueAdapter;
import Model.RevenueDetails;
import View.Common.AdminActivity;
import View.Conductor.ConductorActivity;

public class RevenueActivity extends AppCompatActivity implements RevenueAdapter.OnRevenueListener {

    private SharedPreferences pref;
    private ProgressBar progressBar;
    private RevenueAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<RevenueDetails> RevenueList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.RevenueListView);

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
        String user = pref.getString("user", "");
        switch (user) {

            case "Admin":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case "Conductor":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), ConductorActivity.class));
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

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RevenueActivity.this,
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
        mAdapter = new RevenueAdapter(RevenueList, this);
        recyclerView.setAdapter(mAdapter);

        getRevenueDetails();

    }

    public void getRevenueDetails() {
        String url = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_revenue.php";
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

                                    RevenueDetails item = new RevenueDetails(

                                            object.getString("fleet_id"),
                                            object.getString("mpesa_ref"),
                                            "KSH " + object.getString("amount"),
                                            object.getString("timestamp"),
                                            object.getString("conductor_name")
                                    );

                                    RevenueList.add(item);
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
        showToast(name);
    }

}