package com.xxx.table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yazha on 2017-03-29.
 */
public class Test {

    public static void main(String[] args) {
        final int maxColumns = 5;
        Table table = new Table();
        table.setWidth(500);
        table.setColumnLimit(maxColumns); // 最多两列
        table.setRowLimit(1024); // 最多1024行

        Random r = new Random();
        ArrayList<TableData> tds = new ArrayList<>();
        for (int i = 0; i < maxColumns; i++) {
            TableHeader th = new TableHeader();
            th.setCls("v_th");
            th.setText("t"+i);
            tds.add(th);
        }
        for (int i = 0; i < 15; i++) {
            TableData td = new TableData();
            td.setColspan(r.nextInt(maxColumns)+1);
            td.setText(i+"");
            tds.add(td);
        }
        table.addTableDataList(tds);
        try {
            toFile(table.toString());
            showDocument("C:/Users/yazha/Desktop/test.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDocument(String s) throws Exception{
        String s1 = "rundll32 url.dll,FileProtocolHandler " + s;
        Runtime.getRuntime().exec(s1);
    }

    private static void toFile(String text) throws IOException{
        File f = new File("C:/Users/yazha/Desktop/test.html");
        if(!f.exists())
            f.createNewFile();
        FileOutputStream out = new FileOutputStream(f);
        out.write(text.getBytes());
        out.flush();
        out.close();
    }

}
