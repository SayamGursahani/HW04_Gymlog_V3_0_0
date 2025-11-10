package com.example.hw04_gymlog_v300;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText exerciseEt, setsEt, repsEt;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private GymLogAdapter adapter;
    private AppDatabase db;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences(LoginActivity.PREFS, MODE_PRIVATE);
        username = sp.getString(LoginActivity.KEY_USER, null);
        if (username == null) {
            // Not logged in -> go to login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        db = AppDatabase.getInstance(this);

        exerciseEt = findViewById(R.id.input_exercise);
        setsEt = findViewById(R.id.input_sets);
        repsEt = findViewById(R.id.input_reps);
        recyclerView = findViewById(R.id.recycler_logs);
        emptyView = findViewById(R.id.empty_text);
        Button addBtn = findViewById(R.id.button_add);
        Button signOutBtn = findViewById(R.id.button_sign_out);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GymLogAdapter();
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(v -> addLog());
        signOutBtn.setOnClickListener(v -> {
            sp.edit().remove(LoginActivity.KEY_USER).apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        refreshList();
    }

    private void addLog() {
        String exercise = exerciseEt.getText().toString().trim();
        String setsS = setsEt.getText().toString().trim();
        String repsS = repsEt.getText().toString().trim();

        if (exercise.isEmpty() || setsS.isEmpty() || repsS.isEmpty()) return;

        int sets = Integer.parseInt(setsS);
        int reps = Integer.parseInt(repsS);

        GymLog log = new GymLog(username, exercise, sets, reps, System.currentTimeMillis());
        db.gymLogDAO().insert(log);

        exerciseEt.setText("");
        setsEt.setText("");
        repsEt.setText("");

        refreshList();
    }

    private void refreshList() {
        List<GymLog> items = db.gymLogDAO().getAllForUser(username);
        adapter.submit(items);
        emptyView.setVisibility(items.isEmpty() ? TextView.VISIBLE : TextView.GONE);
    }
}
