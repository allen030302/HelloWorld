package com.example.myapplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

class ReadCSVThread extends Thread {
    String fileName;
    //final StringBuilder cSb = new StringBuilder();
    ArrayList<String> arraycsv = new ArrayList<String>();
    String[] arrayfinish;
    public ReadCSVThread(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public void run() {
        super.run();
        File inFile = new File(fileName);
        String inString;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            while ((inString = reader.readLine()) != null) {
                //cSb.append(inString).append("\n");
                arrayfinish = inString.split(",");
                for (String a:arrayfinish) {
                    arraycsv.add(a);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getarraycsvlist(){
        return arraycsv;
    }
}