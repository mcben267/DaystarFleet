package View.Manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import Controller.TaskAdapter;
import Model.TaskDetails;

public class TodoActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    private SharedPreferences pref;
    private ProgressBar progressBar;
    private TaskAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<TaskDetails> TaskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);

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
                Toast.makeText(TodoActivity.this,
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

                                    TaskDetails item = new TaskDetails(

                                            object.getString("task_id"),
                                            object.getString("title"),
                                            object.getString("status"),
                                            object.getString("details"),
                                            object.getString("timestamp")
                                    );

                                    TaskList.add(item);
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