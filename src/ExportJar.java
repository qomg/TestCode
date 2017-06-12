import java.io.*;

/**
 * Created by yazha on 2016/11/14.
 */
public class ExportJar {

    private static final String src = "src/main/java";
    private static final String resources = "src/main/resources";
    private static final String page = "src/main/webapp";
    private static final String output = "src/main/webapp/WEB-INF/classes";

    public static void main(String[] args) {
        String name = "filelist.txt";
        if (args != null && args.length > 0) {
            name = args[0];
        }
        File file = new File(name);
        File dir = new File(file.getParentFile(), "out");
        if(dir.exists()){
            dir.delete();
        }
        dir.mkdirs();//删除并重建目录
        String root = dir.getAbsolutePath().replace("\\", "/")+"/";
        try {
            String[] s = Util.readFileList(file);
            for (int i = 0; i < s.length; i++) {
                String path = s[i].replace('\\', '/').trim();
                if(path.contains(src)){
                    copyFileFromSource(path, root);
                }else if(path.contains(resources)){
                    copyFileFromResource(path, root);
                }else if(path.contains(page)){
                    copyFileFromPage(path, root);
                }
            }
            executeGenerateJar(file.getParentFile(), dir);
            executeRemoveDirectory(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFileFromSource(String path, String root){
        path = path.replace(src, output);
        String rootDir = path.substring(0, path.indexOf(output));
        File temp = new File(path);
        if(temp.getName().endsWith(".java")){
            String javaName = temp.getName().replace(".java", "");//java类名，不含后缀
            File[] files = temp.getParentFile().listFiles((dir, name) -> {
                //匹配java类和其内部类 如 ClassName$HelloWord.class
                return name.matches(javaName + "(\\$[\\w]+)*\\.class");
            });
            if(files != null && files.length > 0){
                for (File file : files) {
                    String filePath = file.getAbsolutePath().replace("\\", "/");
                    Util.copyFile(filePath, filePath.replace(rootDir, root));
                }
            }
        }else{
            Util.copyFile(path, path.replace(rootDir, root));
        }
    }

    private static void copyFileFromResource(String path, String root){
        path = path.replace(resources, output);
        String rootDir = path.substring(0, path.indexOf(output));
        Util.copyFile(path, path.replace(rootDir, root));
    }

    private static void copyFileFromPage(String path, String root){
        String rootDir = path.substring(0, path.indexOf(page));
        Util.copyFile(path, path.replace(rootDir, root));
    }

    private static void executeGenerateJar(File target, File dir){
        try {
            File jar = new File(target, System.currentTimeMillis() + ".jar");
            // Execute a command with an argument
            //String command = "jar cvf " + name +" -C " + folder + " .";//最后面的点不能省略，表示匹配任意内容
            //Runtime.getRuntime().exec(command);

            // In case developer wants to execute a command with more than
            // one argument, it is necessary to use the overload that requires
            // the command and its arguments to be supplied in an array:
            String[] commands = {"jar", "cvf", jar.getAbsolutePath(), "-C", dir.getAbsolutePath(), "."};
            Process process = Runtime.getRuntime().exec(commands);
            Util.print(process);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeRemoveDirectory(File dir){
        try {
            // In case developer wants to execute a command with more than
            // one argument, it is necessary to use the overload that requires
            // the command and its arguments to be supplied in an array:
            String[] commands = {"cmd", "/c", "rd", "/s/q", dir.getAbsolutePath()};
            Process process = Runtime.getRuntime().exec(commands);
            Util.print(process);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}