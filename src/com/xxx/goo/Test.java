package com.xxx.goo;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.MathContext;

/**
 * Created by yazha on 2017-02-17.
 */
public class Test{

    public static String reverseString(String str, String splitRegex){
        if(str == null || str.trim().length() == 0) return str;
        if(splitRegex == null){
            char[] chs = str.toCharArray();
            char[] nch = new char[chs.length];
            for (int i = 0; i < chs.length; i++) {
                nch[i] = chs[chs.length-1-i];
            }
            return new String(nch);
        }else {
            String[] arr = str.split(splitRegex.replace(".","\\."), -1);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[arr.length-1-i]).append(splitRegex);
            }
            if(sb.length() > str.length())
                sb.deleteCharAt(sb.length()-1);
            return new String(sb);
        }
    }

    private static void getRuningProcess(){
        List<String> processes = new ArrayList<String>();
        try {
            String line;
            Process p = Runtime.getRuntime().exec("cmd /c reg query HKEY_LOCAL_MACHINE//SOFTWARE//microsoft");
            BufferedReader input = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        System.out.println(processes);
    }


    private static void copyFile(File destfile, File srcFile){
        try{
            FileOutputStream fOut = new FileOutputStream(destfile, true);
            FileInputStream fIn = new FileInputStream(srcFile);
            fOut.write(("\n\n"+srcFile.getName()+"\n").getBytes());
            int len;
            byte[] buf = new byte[2048];
            while((len = fIn.read(buf)) != -1){
                fOut.write(buf, 0, len);
            }
            fOut.flush();
            fOut.close();
            fIn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(String str){

        return str == null || str.trim().length() == 0;
    }

    /**适配图片*/
    private static void adapteImage(String name, String path){
        String[] arr = name.split("_");
        String fieldName = arr[0];
            try {
                String type = arr[1].replace("IMAGE", "");
                if(arr.length == 2 || isEmpty(arr[2])){//field_IMAGE or field_IAMGE_
                    System.out.println("insertImage");
                }else if(arr.length >= 4){
                    int w = Integer.parseInt(arr[2]);
                    int h = Integer.parseInt(arr[3]);
                    System.out.println("insertImage:decodeSampledBitmapFromFile");
                }
                try {
                    if(!isEmpty(type)){
                        System.out.println("setWrapType");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) {
//        System.out.println("1>2<3=4".replace("[<>=]", ""));
//        System.out.println("aaabbbcddd".replace("([a-z]){1}","$1"));
//        System.out.println("aaabbbcddd".replace("[a-b]","1"));
//        System.out.println("aaabbbcddd".matches("[a-b]*"));

        File dir = new File("C:/Users/yazha/Desktop/日志");
        String[] files = dir.list();
        Arrays.sort(files);
        try {
            File output = new File(dir, "工作日志汇总.txt");
            if(!output.exists())
                output.createNewFile();
            FileOutputStream fos = new FileOutputStream(output, true);
            for (int i = 0; i < files.length; i++) {
                String text = readFile(new File(dir, files[i]));
                fos.write(files[i].substring(0, 10).concat("\n")
                        .concat(text.split("---------------------------------------------------")[1])
                        .concat("\n\n").getBytes());
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(File src){
        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(src);
            byte[] buf = new byte[2048];
            int len;
            while ((len = fis.read(buf)) != -1){
                sb.append(new String(buf, 0, len));
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
