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

import Controller.SubContractAdapter;
import Model.SubContractDetails;

public class SubContractsActivity extends AppCompatActivity implements SubContractAdapter.OnSubContractListener {

    private ProgressBar progressBar;
    private SubContractAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<SubContractDetails> SubContractList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcontrants);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.SubcontractListView);

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
                Toast.makeText(SubContractsActivity.this,
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
        mAdapter = new SubContractAdapter(SubContractList, this);
        recyclerView.setAdapter(mAdapter);

        getSubContractDetails();

    }

    public void getSubContractDetails() {
        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_subcontracts.php";
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

                            SubContractDetails item = new SubContractDetails(

                                    object.getString("subcontract_id"),
                                    object.getString("company_name"),
                                    object.getString("contact_person"),
                                    object.getString("contact_mobile"),
                                    object.getString("contract_details"),
                                    object.getString("contract_duration"),
                                    object.getString("contract_expire")
                            );

                            SubContractList.add(item);
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

    @Override
    public void onItemClick(String name) {
        showToast(name);
    }

}