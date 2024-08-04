package com.example.organaizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonHandler {

    private String data;

    public JsonHandler(String data){
        this.data = data;
    }

    public String addData(List<Item> items, String title) {
        JSONObject root;
        try {
            if (data.isEmpty()){
                root = new JSONObject();
            }
            else {
                root = new JSONObject(data);
            }
            JSONObject jsonObject = new JSONObject();
            JSONArray taskArray = new JSONArray();
            for (Item item : items) {
                JSONObject taskObject = new JSONObject();
                taskObject.put("time", item.getTitle());
                taskObject.put("task", item.getDescription());
                taskArray.put(taskObject);
            }

            jsonObject.put("title", title);
            jsonObject.put("tasks", taskArray);

            root.put(getId(), jsonObject);

            return root.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String getId(){
        if (isNewOrganaize()){
            return String.valueOf(getLastId() + 1);
        }
        else {
            return String.valueOf(MainActivity.getID());
        }
    }

    private boolean isNewOrganaize(){
        return MainActivity.getID() == 0;
    }

    public int getLastId(){
        int lastId;

        if (data.isEmpty()){
            lastId = 0;
            return lastId;
        }
        try {

            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.names();

            lastId = Integer.parseInt(jsonArray.getString(jsonArray.length() - 1));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return lastId;
    }
}
