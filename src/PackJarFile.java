import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipFile;

/**
 * Created by yazha on 2017-04-24.
 */
public class PackJarFile {

    public static void main(String[] args) {
        try {
            String jar = args[0];
            String dir = new File(jar).getParentFile().getAbsolutePath();
            new File(jar).createNewFile();
            ZipFile jarFile = new ZipFile(jar);
            JarOutputStream jos = new JarOutputStream(new FileOutputStream(jar));
            for (int i = 1; i < args.length; i++) {
                File file = new File(args[i]);
                writeFile(dir, jos, file);
            }
            jarFile.close();
            jos.setComment("PACK200");
            jos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeFile(String dir, JarOutputStream jos, File file) throws IOException {
        if(file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int j = 0; j < files.length; j++) {
                    writeFile(dir, jos, files[j]);
                }
            } else {
                JarEntry jarEntry = new JarEntry(file.getAbsolutePath().replace(dir, ""));
                jos.putNextEntry(jarEntry);
                FileInputStream fis = new FileInputStream(file);
                int len;
                byte[] buf = new byte[8192];
                while ((len = fis.read(buf)) > 0){
                    jos.write(buf, 0 , len);
                }
                fis.close();
                jos.flush();
                jos.closeEntry();
            }
        }
    }

}
