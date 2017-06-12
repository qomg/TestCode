import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by yazha on 2017-03-29.
 */
public class OneKeyCommit {

    public static void main(String[] args) {
        String config = args[0];
        try {
            Properties prop = readProperties(config);
            String fileListPath = prop.getProperty("fileListPath");
            String sourceDirectory = prop.getProperty("sourceDirectory");
            List<String> destinationDirectory = new ArrayList<>();
            Set<String> names = prop.stringPropertyNames();
            for (String key : names) {
                if(key.contains("destinationDirectory")){
                    String d = prop.getProperty(key);
                    if(!destinationDirectory.contains(d))
                        destinationDirectory.add(d);
                }
            }

            if(fileListPath != null && sourceDirectory != null && destinationDirectory.size() > 0) {
                File f = new File(fileListPath);
                String[] s = Util.readFileList(f);
                for (int i = 0; i < s.length; i++) {
                    String[] dest = new String[destinationDirectory.size()];
                    for (int j = 0; j < dest.length; j++) {
                        dest[j] = s[i].replace(sourceDirectory, destinationDirectory.get(j));
                    }
                    executeSvnAdd(Util.copyFiles(s[i], dest));
                }
                for (String dir : destinationDirectory) {
                    executeSvnCommit(dir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties readProperties(String config) throws IOException{
        Properties prop = new Properties();
        InputStream in = new FileInputStream(config);
        prop.load(in);
        return prop;
    }

    private static void executeSvnAdd(List<String> newFiles){
        if(newFiles == null || newFiles.size() == 0)
            return;
        try {
            // In case developer wants to executeSvnAdd a command with more than
            // one argument, it is necessary to use the overload that requires
            // the command and its arguments to be supplied in an array:
            newFiles.add(0, "add");
            newFiles.add(0, "svn");
            String[] cmd = new String[newFiles.size()];
            Process process = Runtime.getRuntime().exec(newFiles.toArray(cmd));
            Util.print(process);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeSvnCommit(String dir){
        try {
            // In case developer wants to executeSvnAdd a command with more than
            // one argument, it is necessary to use the overload that requires
            // the command and its arguments to be supplied in an array:
            String[] st = {"svn", "st", dir};
            Process process = Runtime.getRuntime().exec(st);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            ArrayList<String> wait = new ArrayList<>();
            ArrayList<String> fail = new ArrayList<>();
            String text;
            while((text = in.readLine()) != null){
                //M：内容被修改；C：发生冲突；A：预定加入到版本库；K：被锁定
                String path = text.split("       ")[1];
                File f = new File(path);
                char ch = text.charAt(0);
                switch (ch){
                    case 'M':
                    case 'A':
                        wait.add(f.getAbsolutePath());
                        break;
                    case 'C':
                    case 'D':
                    case 'K':
                        fail.add(f.getAbsolutePath());
                        break;
                }
            }
            if(wait.size() > 0) {
                wait.add(0, "commit all modified files.");
                wait.add(0, "-m");
                wait.add(0, "ci");
                wait.add(0, "svn");
                String[] cmd = new String[wait.size()];
                try {
                    Process p = Runtime.getRuntime().exec(wait.toArray(cmd));
                    Util.print(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fail.size() > 0) {
                Util.print("请手动提交：\n" + fail.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
