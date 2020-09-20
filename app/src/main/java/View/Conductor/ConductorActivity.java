package View.Conductor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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

import Controller.TaskAdapter;
import Model.TaskDetails;
import View.Common.AboutActivity;
import View.Common.LoginActivity;
import View.Common.ParcelActivity;
import View.Common.ProfileActivity;

public class ConductorActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    private SharedPreferences pref;
    private TaskAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<TaskDetails> TaskList = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_dashboard);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CardView revenue = findViewById(R.id.btnRevenue);
        CardView parcels = findViewById(R.id.btnParcels);
        recyclerView = findViewById(R.id.TodoListView);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        intiRecycleView();

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

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ConductorActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intiRecycleView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(null);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TaskAdapter(TaskList, this);
        recyclerView.setAdapter(mAdapter);

        getTasksDetails();

    }

    public void getTasksDetails() {
        String url = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_tasks.php";
        StringRequest stringRequest;

        RequestQueue queue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if (!response.equals("error")) {
                                JSONArray array = new JSONArray(response);

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);

                                    TaskDetails item = new TaskDetails(

                                            object.getString("task_id"),
                                            object.getString("title"),
                                            object.getString("task_status"),
                                            object.getString("details"),
                                            object.getString("timestamp")
                                    );

                                    TaskList.add(item);
                                    mAdapter.notifyDataSetChanged();

                                }

                            }

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            showToast(ex.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error: Request Failed");
            }
        });

        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(String name) {

    }


}
