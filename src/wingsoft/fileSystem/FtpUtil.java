package wingsoft.fileSystem;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import wingsoft.tool.common.Md5Tools;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;


public class FtpUtil {
    public FtpUtil() {}

    public String uploadFile(String url, int port, String username, String password, InputStream input, String seq, String fileName, String type, String originFilename, String projid, boolean doSetMap, boolean isCap) {
        String result = null;
        FTPClient ftp = new FTPClient();
        String directory = null;
        String fName = null;
        try {
            ftp.connect(url, port);
        } catch (SocketException var37) {
            result = "Err:1";
            var37.printStackTrace();
            return result;
        } catch (IOException var38) {
            result = "Err:1";
            var38.printStackTrace();
            return result;
        }

        try {
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                result = "Err:2";
                return result;
            }
        } catch (IOException var36) {
            var36.printStackTrace();
        }

        try {
            String dir_seq = this.makeDir(seq, type, projid, isCap);
            directory = dir_seq.split("&")[0];
            String dir = null;
            dir = directory.substring(0, directory.lastIndexOf("/"));
            if (isCap) {
                String hashSource = "encode/" + directory.substring(0, directory.indexOf("."));
                String hashFilename = Md5Tools.getMD5(hashSource.getBytes());
                fName = hashFilename + "." + type;
            } else {
                fName = directory.substring(directory.lastIndexOf("/") + 1);
            }

            if (fileName.contains("_min")) {
                fName = fName.substring(0, fName.lastIndexOf(".")) + "_min." + type;
            }

            directory = dir + "/" + fName;
            if (!ftp.changeWorkingDirectory(dir)) {
                String[] addr = dir.split("/");

                for(int i = 0; i < addr.length; ++i) {
                    boolean exist = ftp.changeWorkingDirectory(addr[i]);
                    if (!exist) {
                        if (!ftp.makeDirectory(addr[i])) {
                            result = "Err:3";
                            ftp.disconnect();
                            return result;
                        }

                        ftp.changeWorkingDirectory(addr[i]);
                    }
                }
            }
        } catch (Exception var40) {
            var40.printStackTrace();
        }

        try {
            ftp.setFileType(2);
            ftp.storeFile(fName, input);
            input.close();
            ftp.logout();
            result = "Suc:" + seq;

            try {
                if (doSetMap) {
                    this.setMap(seq, type, fileName, directory, originFilename, projid);
                }
            } catch (Exception var34) {
                var34.printStackTrace();
                result = "Err:4";
            }
        } catch (Exception var35) {
            var35.printStackTrace();
            result = "Err:5";
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException var33) {
                }
            }

        }

        return result;
    }

    protected void setMap(String seq, String type, String fileName, String directory, String originFilename, String projid) throws Exception {
        this.insertFtpMap(fileName, seq, type, directory, originFilename, projid);
    }

    protected void insertFtpMap(String fileName, String seq, String type, String directory, String originFilename, String projid) throws Exception {
        ConnectionPool pool
         = ConnectionPoolManager.getPool("CMServer");

        Connection conn = null;
        PreparedStatement ps = null;
        String sql = null;
        sql = "insert into FILESYSTEMMAP(seq,type,path,filename) values(?,?,?,?)";

        try {
            conn = pool.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.setString(1, seq);
            ps.setString(2, type);
            ps.setString(3, directory);
            if (originFilename == "") {
                ps.setString(4, fileName);
            } else {
                ps.setString(4, originFilename);
            }
            ps.executeUpdate();
            conn.commit();
        } catch (Exception var15) {
            var15.printStackTrace();
            conn.rollback();
//            throw var15;
        } finally {
            ps.close();
            pool.returnConnection(conn);
        }

    }

    protected String makeDir(String seq, String type, String projid, boolean isCap) {
        String directory = "";
        directory = directory + projid + "/" + type;
        Long seq_value = Long.parseLong(seq);
        directory = directory + "/" + parseDirFromLong(seq_value, isCap);
        if (isCap) {
            DecimalFormat nf = new DecimalFormat();
            nf.applyPattern("000");
            directory = directory + "/" + nf.format(seq_value % 1000L) + "." + type;
        } else {
            directory = directory + "/" + seq + "." + type;
        }

        String result = directory + "&" + seq;
        return result;
    }

    public static String parseDirFromLong(Long value, boolean isCap) {
        String s_value = "";
        Long level1_value;
        Long level2_value;
        Long level3_value;
        if (isCap) {
            level1_value = value / 1000000000L;
            level2_value = value / 1000000L % 1000L;
            level3_value = value / 1000L % 1000L;
        } else {
            level1_value = value / 10000000000L;
            level2_value = value / 10000000L % 1000L;
            level3_value = value / 10000L % 1000L;
        }

        DecimalFormat nf = new DecimalFormat();
        nf.applyPattern("000");
        s_value = nf.format(level1_value) + "/" + nf.format(level2_value) + "/" + nf.format(level3_value);
        return s_value;
    }

    public boolean downFile(String url, int port, String username, String password, String remotePath, String fileName, OutputStream os) {
        boolean isSuccess = false;
        FTPClient ftp = new FTPClient();

        try {
            ftp.connect(url, port);
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            boolean var17;
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                var17 = isSuccess;
                return var17;
            }

            if (!ftp.changeWorkingDirectory(remotePath)) {
                ftp.disconnect();
                var17 = isSuccess;
                return var17;
            }

            FTPFile[] fs = ftp.listFiles();
            FTPFile[] var15 = fs;
            int var14 = fs.length;

            for(int var13 = 0; var13 < var14; ++var13) {
                FTPFile ff = var15[var13];
                if (ff.getName().equals(fileName)) {
                    ftp.setFileType(2);
                    ftp.retrieveFile(ff.getName(), os);
                    os.close();
                    isSuccess = true;
                    break;
                }
            }

            ftp.logout();
        } catch (IOException var26) {
            var26.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException var25) {
                    ;
                }
            }

        }

        return isSuccess;
    }
}
