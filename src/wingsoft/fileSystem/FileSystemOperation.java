package wingsoft.fileSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class FileSystemOperation {
    private InputStream responseText;
    private File doc;

    public FileSystemOperation() {
        FtpFileOperation fs = new FtpFileOperation();
        fs.configFtp();
    }

    public File getDoc() {
        return this.doc;
    }

    public void setDoc(File doc) {
        this.doc = doc;
    }

    public void setResponseText(InputStream responseText) {
        this.responseText = responseText;
    }

    public InputStream getResponseText() {
        return this.responseText;
    }

//    public static IFileSystem constructFs() {
//        FtpFileOperation fs = new FtpFileOperation();
//        fs.configFtp();
//        return (IFileSystem)fs;
//    }
    public String getImgStreamAction() throws Exception{
        return this.getImgAction();
    }

    //ftp
    public String getImgAction() throws Exception {
        FtpFileOperation ftpFO = new FtpFileOperation();
        ByteArrayOutputStream bos = ftpFO.getImgStream();
        this.setResponseText(new ByteArrayInputStream(bos.toByteArray()));
        return "imageJpeg";
    }

    public ByteArrayOutputStream getImgStream(){
        ByteArrayOutputStream baos = null;
        return baos;
    }
}