cd C:\Workspace2\YDZF_V3.0\out\production\TestCode
jar cfe filecopy.jar OneKeyCommit OneKeyCommit.class Util.class
copy /y filecopy.jar C:\Workspace2\YDZF_HN_V3.0\copy-svn-file
cd C:\Workspace2\YDZF_HN_V3.0\copy-svn-file
zzz-filecopy.bat 或者 java -Dfile.encoding=utf-8 -jar filecopy.jar config.properties

cd C:\Workspace2\YDZF_V3.0\out\production\TestCode
jar cfe export.jar ExportJar ExportJar.class Util.class
copy /y export.jar C:\Workspace2\YDZF_HN_V3.0\generate-jar
cd C:\Workspace2\YDZF_HN_V3.0\generate-jar
zzzz-export.bat