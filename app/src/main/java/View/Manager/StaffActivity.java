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

import Controller.StaffAdapter;
import Model.StaffDetails;

public class StaffActivity extends AppCompatActivity implements StaffAdapter.OnStaffListener {

    private StaffAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<StaffDetails> StaffList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.staffList);

        intiRecycleView();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
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
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        overridePendingTransition(0, 0);
        finish();
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
        mAdapter = new StaffAdapter(StaffList, this);
        recyclerView.setAdapter(mAdapter);

        getStaffDetails();

    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StaffActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNoteClick(String name) {
        showToast(name);
    }

    public void getStaffDetails() {
        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_staff.php";
        StringRequest stringRequest;

        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Test", response);

                try {
                    if (!response.equals("error")) {
                        JSONArray array = new JSONArray(response);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            StaffDetails item = new StaffDetails(
                                    object.getString("profile_pic"),
                                    object.getString("name") + " " +
                                            object.getString("surname"),
                                    object.getString("staff_id"),
                                    object.getString("role"),
                                    object.getString("mobile"),
                                    object.getString("email"),
                                    object.getString("tax_pin"),
                                    object.getString("national_id"),
                                    object.getString("license"),
                                    object.getString("insurance_status"),
                                    object.getString("insurance_policy"),
                                    object.getString("blood_group"),
                                    object.getString("medical_state")
                            );

                            StaffList.add(item);
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
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error: Request Failed");
                        progressBar.setVisibility(View.GONE);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}