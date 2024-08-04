package com.example.organaizer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView addButton;
    private ArrayList<Item> items;
    private MyAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    private static int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recicleView);
        addButton = findViewById(R.id.btnPlus);

        items = new ArrayList<>();
        loadData();
        adapter = new MyAdapter(R.layout.recicle_view_plate, items, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle the click event
                Item item = items.get(position);
                ID = item.getId();
                Intent intent = new Intent(MainActivity.this, OrganaizeActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = 0;
                Intent intent = new Intent(MainActivity.this, OrganaizeActivity.class);
                startActivity(intent);
            }
        });

    }

    public static int getID(){
        return ID;
    }

    private void loadData(){
        JsonFile jsonFile = new JsonFile(MainActivity.this, "data.json");

        String title, description;
        int id;

        if (!jsonFile.fileExist()) {
            return;
        }

        try {

            JSONObject jsonObject = new JSONObject(jsonFile.getData());
            JSONArray jsonArray = jsonObject.names();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject taskObject = jsonObject.getJSONObject(jsonArray.getString(i));
                id = Integer.parseInt(jsonArray.get(i).toString());
                title = taskObject.getString("title");
                description = convertDescription(taskObject);
                items.add(new Item(id, title, description));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertDescription(JSONObject jsonObject){
        String description = "";

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("tasks");
            for (int i = 0; i < jsonArray.length(); i++) {
                description += jsonArray.getJSONObject(i).getString("task") + "\n";
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return description;
    }

}