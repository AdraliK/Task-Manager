package com.example.organaizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class OrganaizeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView addButton, backButton;
    private EditText editTextTask;
    private ArrayList<Item> items;
    private NewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.current_organaize);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainOrganaize), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.btnPlus);
        backButton = findViewById(R.id.imageViewBack);
        editTextTask = findViewById(R.id.editTextTask);

        items = new ArrayList<>();
        loadData();
        adapter = implementsAdapterMethods();
        recyclerView.setAdapter(adapter);

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDialog taskDialog = new TaskDialog(
                        OrganaizeActivity.this,
                        new TaskDialog.AddOrEditItem() {
                            @Override
                            public void addOrEdit(Item item) {
                                items.add(item);
                            }
                        },
                        null,
                        null);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(OrganaizeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private NewAdapter implementsAdapterMethods(){
        return new NewAdapter(
                R.layout.recycle_task,
                items,
                new NewAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(int position) {
                        Log.d("ClickListener", "item = " + position + " its delete button");
                        deleteData(position);
                    }
                },
                new NewAdapter.OnEditClickListener() {
                    @Override
                    public void onEditClick(int position) {
                        Log.d("ClickListener", "item = " + position + " its edit button");
                        String time = items.get(position).getTitle();
                        String task = items.get(position).getDescription();
                        TaskDialog taskDialog = new TaskDialog(
                                OrganaizeActivity.this,
                                new TaskDialog.AddOrEditItem() {
                                    @Override
                                    public void addOrEdit(Item item) {
                                        items.set(position, item);
                                    }
                                },
                                time,
                                task);
                    }
                }
        );
    }

    private void loadData(){
        JsonFile jsonFile = new JsonFile(OrganaizeActivity.this, "data.json");

        if (!jsonFile.fileExist() || MainActivity.getID() == 0) {
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonFile.getData());
            String title = jsonObject.getJSONObject(String.valueOf(MainActivity.getID())).getString("title");
            editTextTask.setText(title);
            JSONArray jsonArray = jsonObject.getJSONObject(String.valueOf(MainActivity.getID())).getJSONArray("tasks");
            String time, task;
            for (int i = 0; i < jsonArray.length(); i++){
                time = jsonArray.getJSONObject(i).getString("time");
                task = jsonArray.getJSONObject(i).getString("task");
                items.add(new Item(time, task));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveData(){
        JsonFile file = new JsonFile(OrganaizeActivity.this, "data.json");
        JsonHandler jsonHandler = new JsonHandler(file.getData());
        String data = jsonHandler.addData(items, editTextTask.getText().toString());
        file.writeData(data);
    }

    private void deleteData(int position){
        JsonFile file = new JsonFile(OrganaizeActivity.this, "data.json");
        items.remove(position);
        JsonHandler jsonHandler = new JsonHandler(file.getData());
        String data = jsonHandler.addData(items, editTextTask.getText().toString());
        file.writeData(data);
        adapter.notifyDataSetChanged();
    }


    public NewAdapter getAdapter(){
        return adapter;
    }

}
