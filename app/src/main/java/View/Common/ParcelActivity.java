package View.Common;

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

import Controller.ParcelAdapter;
import Model.ParcelDetails;
import View.Conductor.ConductorActivity;
import View.Driver.DriverActivity;
import View.Manager.DashboardActivity;
import View.Reception.ReceptionActivity;

public class ParcelActivity extends AppCompatActivity implements ParcelAdapter.OnParcelListener {

    private SharedPreferences pref;
    private ProgressBar progressBar;
    private ParcelAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<ParcelDetails> ParcelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.parcelList);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
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

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ParcelActivity.this,
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
        mAdapter = new ParcelAdapter(ParcelList, this);
        recyclerView.setAdapter(mAdapter);

        getParcels();

    }

    public void getParcels() {
        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_parcels.php";
        StringRequest stringRequest;

        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Test", response);

                try {
                    if (!response.equals("error")) {
                        JSONArray array = new JSONArray(response);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            ParcelDetails item = new ParcelDetails(

                                    object.getString("parcel_id"),
                                    object.getString("receiver_name"),
                                    object.getString("receiver_mobile"),
                                    object.getString("receiver_address"),
                                    object.getString("parcel_category"),
                                    object.getString("parcel_status"),
                                    object.getString("parcel_destination"),
                                    object.getString("parcel_origination"),
                                    object.getString("parcel_image")
                            );

                            ParcelList.add(item);
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

        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(String id, String category, String status, String name, String mobile, String address, String destination, String origin, String image) {

        Bundle extras = new Bundle();
        extras.putString("id", id);
        extras.putString("category", category);
        extras.putString("status", status);
        extras.putString("name", name);
        extras.putString("mobile", mobile);
        extras.putString("address", address);
        extras.putString("destination", destination);
        extras.putString("origin", origin);
        extras.putString("image", image);

        startActivity(new Intent(getApplicationContext(), ParcelDetailsActivity.class).putExtras(extras));
        overridePendingTransition(0, 0);
        finish();
    }
}