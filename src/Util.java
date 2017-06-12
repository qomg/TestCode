import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yazha on 2017-03-30.
 */
public class Util {


    public static String[] readFileList(File src) throws IOException{
        return readFile(src).split("\\r\\n");
    }

    private static String readFile(File src) throws IOException{
        StringBuffer sb = new StringBuffer();
        FileInputStream in = new FileInputStream(src);
        byte[] buf = new byte[2048];
        int len;
        while ((len = in.read(buf))!= -1){
            sb.append(new String(buf, 0, len));
        }
        in.close();
        Util.print(src.getName()+"的内容如下\n"+sb);
        return sb.toString().trim();
    }

    public static List<String> copyFiles(String src, String[] dest){
        List<String> newFiles = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream(src);
            List<FileOutputStream> o = new ArrayList<>();
            for (int i = 0; i < dest.length; i++) {
                if(ensureFile(dest[i]))
                    newFiles.add(dest[i]);
                o.add(new FileOutputStream(dest[i]));
            }
            byte[] buf = new byte[2048];
            int len;
            while ((len = in.read(buf))!= -1){
                for (FileOutputStream out : o) {
                    out.write(buf, 0 ,len);
                }
            }
            for (FileOutputStream out : o) {
                out.flush();
                out.close();
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFiles;
    }

    public static boolean ensureDirectory(String filePath){
        boolean result = false;
        File f = new File(filePath);
        if(!f.getParentFile().exists())
            result = f.getParentFile().mkdirs();
        else if(f.getParentFile().isDirectory())
            result = true;
        return result;
    }

    public static boolean ensureFile(String filePath){
        boolean isNew = false;
        if(ensureDirectory(filePath)){
            File file = new File(filePath);
            if(!file.exists())
                try {
                    isNew = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return isNew;
    }

    public static boolean copyFile(String src, String dest){
        boolean isNew = ensureFile(dest);
        copyFile(new File(src), new File(dest));
        return isNew;
    }

    private static void copyFile(File src, File dest){
        try {
            FileInputStream fis = new FileInputStream(src);
            FileOutputStream fos = new FileOutputStream(dest);
            byte[] buf = new byte[2048];
            int len;
            while ((len = fis.read(buf)) != -1){
                fos.write(buf, 0, len);
            }
            fos.flush();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(Process process) {
        try {
            InputStream in = process.getInputStream();
            StringBuffer text = new StringBuffer("result: ");
            byte[] buf = new byte[1024];
            int len;
            while((len = in.read(buf)) != -1){
                text.append(new String(buf, 0, len, "gbk"));
            }
            text.append("\nerror: ");
            in = process.getErrorStream();
            while((len = in.read(buf)) != -1){
                text.append(new String(buf, 0, len, "gbk"));
            }
            print(text.toString());
            print("Process finished with exit code " + process.exitValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void print(String text){
        Console console = System.console();
        if(console == null){
            System.out.println(text);
        }else{
            console.writer().println(text);
        }
    }

}
