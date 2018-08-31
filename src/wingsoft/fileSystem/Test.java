package wingsoft.fileSystem;

import sun.net.ftp.FtpClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Test {
    public static void main(String[] args) throws Exception{
        String seq = "21118038.jpeg";
        String fileName = "21118038.jpeg";
        String projid = "WF_NYD";
        Test test = new Test();
        FtpFileOperation ftpFO = new FtpFileOperation();

        ByteArrayOutputStream bos = ftpFO.getImgStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
        System.out.println(bais);
//        FtpFileOperation ffo = new FtpFileOperation();

        //seq ,21118038
        //fileName 21118038
        //projid SHOP
//        String filePath = ffo.getFilePathPrivateQCopy(seq, fileName, projid);
        ///WF_NYD/pic/21118038.jpg&21118038.jpg
        ///WF_NYD/pic/21118038.jpeg21118038.jpeg
//        System.out.println(filePath);
    }
}
