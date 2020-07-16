package com.example.myapplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class ReadCSVThread extends Thread {
    String fileName;
    String folder;
    public ReadCSVThread(String folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }
    @Override
    public void run() {
        super.run();
        File inFile = new File(folder + File.separator + fileName);
        final StringBuilder cSb = new StringBuilder();
        String inString;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            while ((inString = reader.readLine()) != null) {
                cSb.append(inString).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}