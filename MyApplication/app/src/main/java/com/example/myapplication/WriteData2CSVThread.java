package com.example.myapplication;


import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class WriteData2CSVThread extends Thread {

    List<String> data;
    String fileName;
    String folder;
    StringBuilder sb;

    public WriteData2CSVThread(List<String> data, String folder, String fileName) {
        this.data = data;
        this.folder = folder;
        this.fileName = fileName;
    }

    private void createFolder() {
        File fileDir = new File(folder);
        boolean hasDir = fileDir.exists();
        if (!hasDir) {
            fileDir.mkdirs();// 這裡建立的是目錄
        }
    }
    @Override
    public void run() {
        super.run();
        createFolder();
        File eFile = new File(folder + File.separator + fileName);
        if (!eFile.exists()) {
            try {
                boolean newFile = eFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream os = new FileOutputStream(eFile, false);
            sb = new StringBuilder();
            for (String a:data){
                sb.append(a).append(",");
            }
            sb.append("\n");
            os.write(sb.toString().getBytes());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
