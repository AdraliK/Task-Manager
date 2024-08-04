package com.example.organaizer;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFile {

    private File file;

    public JsonFile(Activity activity, String fileName){
        file = new File(activity.getFilesDir(), fileName);
    }

    public void writeData(String data){
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getData() {
        String data;
        if (!file.exists()) {
            return "";
        }
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            data = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public boolean fileExist(){
        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }
}
