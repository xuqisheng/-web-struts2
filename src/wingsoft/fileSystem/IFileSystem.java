package wingsoft.fileSystem;

import java.io.ByteArrayOutputStream;
import java.io.File;

public interface IFileSystem {
    String saveFile();

    String saveFile(File var1, String var2, String var3, String var4, String var5, boolean var6);

    void getFile();

    int deleteFile();

    int preDeleteFile();

    int confirmFile();

    String getFilePath(String var1, String var2);

    ByteArrayOutputStream getImgStream();
}