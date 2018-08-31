package wingsoft.fileSystem;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import wingsoft.tool.common.Md5Tools;
import wingsoft.tool.common.CommonOperation;
import wingsoft.tool.common.MyDes;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;//
import java.util.Locale;

public class FtpFileOperation {
    private final String seqEncodeKey = "seq2017needenc!!!~";
    //
    protected String ftpUrl;
    protected int ftpPort;
    protected String userName;
    protected String password;
    protected String result;
    protected InputStream responseText;

    public FtpFileOperation() {
        this.ftpUrl = FTPServer.ftpIP;
        this.ftpPort = Integer.parseInt(FTPServer.port);
        this.userName = FTPServer.ftpUser;
        this.password = FTPServer.ftpPwd;
    }

    public void setResponseText(InputStream responseText) {
        this.responseText = responseText;
    }

    public InputStream getResponseText() {
        return this.responseText;
    }

    public String saveFile() {
        String result = this.save();
        return result;
    }

    public String saveFile(File doc, String type, String originFilename, String projid, String seq, boolean isMinFile) {
        HttpServletRequest request = ServletActionContext.getRequest();
        boolean isCap = "true".equals(request.getParameter("isCapturer"));
        String result = this.save(doc, type.toUpperCase(), originFilename, projid, seq, isMinFile, isCap);
        return result;
    }

    public int confirmFile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String seqList = request.getParameter("seqlist");
        String delSeqList = request.getParameter("delseqlist");
        String[] seqListArray = seqList.split("\\|");
        String[] delSeqListArray = delSeqList.split("\\|");

        int i;
        try {
            MyDes dec = new MyDes("seq2017needenc!!!~");
            for(i = 0; i < seqListArray.length; ++i) {
                try {
                    seqListArray[i] = dec.decrypt(seqListArray[i]);
                } catch (Exception var11) {
                }
            }

            seqList = StringUtils.join(seqListArray, "|");

            for(i = 0; i < delSeqListArray.length; ++i) {
                try {
                    delSeqListArray[i] = dec.decrypt(delSeqListArray[i]);
                } catch (Exception var10) {
                }
            }

            seqList = StringUtils.join(seqListArray, "|");
            delSeqList = StringUtils.join(delSeqListArray, "|");
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        String projid = request.getParameter("projid");
        i = -1;

        try {
            i = this.confirmFileList(seqList, delSeqList, projid);
        } catch (SQLException var9) {
            var9.printStackTrace();
        }

        return i;
    }

    public int preDeleteFile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String seq = request.getParameter("seq");

        try {
            seq = (new MyDes("seq2017needenc!!!~")).decrypt(seq);
        } catch (Exception var9) {
        }

        String minFile = request.getParameter("minFile");
        String projid = request.getParameter("projid");
        String fileName = null;
        int result = -1;
        if ("1".equals(minFile)) {
            fileName = seq + "_min";
        } else {
            fileName = seq;
        }

        try {
            result = this.setFilePredelete(seq, fileName, projid);
        } catch (SQLException var8) {
            var8.printStackTrace();
        }

        return result;
    }

    public int deleteFile() {
        return 1;
    }

    public ByteArrayOutputStream getImgStream() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.reset();
        String minFile = CommonOperation.nTrim(request.getParameter("minFile"));
        String seq = CommonOperation.nTrim(request.getParameter("seq"));

        System.out.println("minFile:"+minFile);
        System.out.println("seq:"+seq);
        if (seq.contains("jpeg"))
            seq = seq.replace("jpeg","jpg");
//        try {
//            seq = (new MyDes("seq2017needenc!!!~")).decrypt(seq);
//        } catch (Exception var24) {
//            var24.printStackTrace();
//        }

        String type = CommonOperation.nTrim(request.getParameter("type"));
        if(type.contains("jpeg"))
            type.replace("jpeg","jpg");
        String projid = request.getParameter("projid");
        String filePath = null;
        String fallbackPathForOldCapturer = null;
        String fallbackPathForOldCapturer2 = null;
        String filePath_LowerCaseExt = null;
        String fallbackPathForOldCapturer_LowerCaseExt = null;
        String fallbackPathForOldCapturer2_LowerCaseExt = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            String fileName;
            if ("1".equals(minFile)) {
                fileName = seq + "_min";
            } else {
                fileName = seq;
            }

            System.out.println("seq:" + seq + "  filename:" + fileName + "   projid:" + projid);
            filePath = this.getFilePathPrivateQCopy(seq,fileName,projid);
//            filePath = this.getFilePathPrivate(seq, fileName, projid);
            System.out.println(filePath);
            String remoteFilename;
            String directory;
            String hashSourceForFallback2;
            String remoteFilenameFallbackForOldCapturer2_LowerCaseExt;
            if ("NULL".equals(filePath)) {
                remoteFilename = request.getParameter("dest");
                directory = remoteFilename + "/" + type.substring(1);
                if (".JPEG".equals(type)) {
                    type = ".JPG";
                }else if("JPG".equals(type)){
                    type = ".JPG";
                }

                Long seq_value = Long.parseLong(seq);
                directory = directory + "/" + FtpUtil.parseDirFromLong(seq_value, true);
                DecimalFormat nf = new DecimalFormat();
                nf.applyPattern("000");
                hashSourceForFallback2 = "encode/" + directory + "/" + seq_value + type;
                directory = directory + "/" + nf.format(seq_value % 1000L) + type;
                remoteFilenameFallbackForOldCapturer2_LowerCaseExt = "encode/" + directory.substring(0, directory.indexOf("."));
                String hashFilename = Md5Tools.getMD5(remoteFilenameFallbackForOldCapturer2_LowerCaseExt.getBytes());
                filePath = directory.substring(0, directory.lastIndexOf("/") + 1) + hashFilename + ("1".equals(minFile) ? "_min" : "") + type + "&" + fileName;
                filePath_LowerCaseExt = directory.substring(0, directory.lastIndexOf("/") + 1) + hashFilename + ("1".equals(minFile) ? "_min" : "") + type.toLowerCase() + "&" + fileName;
                fallbackPathForOldCapturer = directory.substring(0, directory.lastIndexOf(".")) + ("1".equals(minFile) ? "_min" : "") + type + "&" + fileName;
                fallbackPathForOldCapturer_LowerCaseExt = directory.substring(0, directory.lastIndexOf(".")) + ("1".equals(minFile) ? "_min" : "") + type.toLowerCase() + "&" + fileName;
                String hashFilenameForFallback2 = Md5Tools.getMD5(hashSourceForFallback2.getBytes());
                fallbackPathForOldCapturer2 = directory.substring(0, directory.lastIndexOf("/") + 1) + hashFilenameForFallback2 + ("1".equals(minFile) ? "_min" : "") + type + "&" + fileName;
                fallbackPathForOldCapturer2_LowerCaseExt = directory.substring(0, directory.lastIndexOf("/") + 1) + hashFilenameForFallback2 + ("1".equals(minFile) ? "_min" : "") + type.toLowerCase() + "&" + fileName;
            }

            remoteFilename = filePath.substring(0, filePath.lastIndexOf("&"));
            directory = fallbackPathForOldCapturer == null ? null : fallbackPathForOldCapturer.substring(0, fallbackPathForOldCapturer.lastIndexOf("&"));
            String remoteFilenameFallbackForOldCapturer2 = fallbackPathForOldCapturer2 == null ? null : fallbackPathForOldCapturer2.substring(0, fallbackPathForOldCapturer2.lastIndexOf("&"));
            String remoteFilename_LowerCaseExt = filePath_LowerCaseExt == null ? null : filePath_LowerCaseExt.substring(0, filePath_LowerCaseExt.lastIndexOf("&"));
            hashSourceForFallback2 = fallbackPathForOldCapturer_LowerCaseExt == null ? null : fallbackPathForOldCapturer_LowerCaseExt.substring(0, fallbackPathForOldCapturer_LowerCaseExt.lastIndexOf("&"));
            remoteFilenameFallbackForOldCapturer2_LowerCaseExt = fallbackPathForOldCapturer2_LowerCaseExt == null ? null : fallbackPathForOldCapturer2_LowerCaseExt.substring(0, fallbackPathForOldCapturer2_LowerCaseExt.lastIndexOf("&"));
            System.out.println("fileName:"+fileName);
            System.out.println("filePath:"+filePath);
            if (!this.getPicFromFtp(remoteFilename, bos) && remoteFilename_LowerCaseExt != null && !this.getPicFromFtp(remoteFilename_LowerCaseExt, bos) && directory != null && !this.getPicFromFtp(directory, bos) && hashSourceForFallback2 != null && !this.getPicFromFtp(hashSourceForFallback2, bos) && remoteFilenameFallbackForOldCapturer2 != null && !this.getPicFromFtp(remoteFilenameFallbackForOldCapturer2, bos) && fallbackPathForOldCapturer2_LowerCaseExt != null) {
                this.getPicFromFtp(remoteFilenameFallbackForOldCapturer2_LowerCaseExt, bos);
            }
        } catch (Exception var23) {
            var23.printStackTrace();
        }

        return bos;
    }

    public void getFile() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.reset();
        String minFile = request.getParameter("minFile");
        String seq = request.getParameter("seq");

        try {
            seq = (new MyDes("seq2017needenc!!!~")).decrypt(seq);
        } catch (Exception var30) {
        }

        String type = request.getParameter("type");
        String projid = request.getParameter("projid");
        String isCap = request.getParameter("isCapturer");
        String fileName;
        if ("1".equals(minFile)) {
            fileName = seq + "_min";
        } else {
            fileName = seq;
        }

        String filePath = null;
        String fallbackPathForOldCapturer = null;
        String fallbackPathForOldCapturer2 = null;
        String filePath_LowerCaseExt = null;
        String fallbackPathForOldCapturer_LowerCaseExt = null;
        String fallbackPathForOldCapturer2_LowerCaseExt = null;

        try {
            filePath = this.getFilePathPrivate(seq, fileName, projid);
        } catch (Exception var29) {
            var29.printStackTrace();
        }

        String remoteFilename;
        String remoteFilenameFallbackForOldCapturer;
        String remoteFilenameFallbackForOldCapturer_LowerCaseExt;
        String remoteFilenameFallbackForOldCapturer2_LowerCaseExt;
        String respFilename;
        String reType;
        if ("NULL".equals(filePath) && "true".equals(isCap)) {
            remoteFilename = request.getParameter("dest");
            remoteFilenameFallbackForOldCapturer = remoteFilename + "/" + type.substring(1);
            if (".JPEG".equals(type)) {
                type = ".JPG";
            }

            Long seq_value = Long.parseLong(seq);
            remoteFilenameFallbackForOldCapturer = remoteFilenameFallbackForOldCapturer + "/" + FtpUtil.parseDirFromLong(seq_value, true);
            DecimalFormat nf = new DecimalFormat();
            nf.applyPattern("000");
            remoteFilenameFallbackForOldCapturer_LowerCaseExt = "encode/" + remoteFilenameFallbackForOldCapturer + "/" + seq_value + type;
            remoteFilenameFallbackForOldCapturer = remoteFilenameFallbackForOldCapturer + "/" + nf.format(seq_value % 1000L) + type;
            remoteFilenameFallbackForOldCapturer2_LowerCaseExt = "encode/" + remoteFilenameFallbackForOldCapturer.substring(0, remoteFilenameFallbackForOldCapturer.indexOf("."));
            respFilename = Md5Tools.getMD5(remoteFilenameFallbackForOldCapturer2_LowerCaseExt.getBytes());
            filePath = remoteFilenameFallbackForOldCapturer.substring(0, remoteFilenameFallbackForOldCapturer.lastIndexOf("/") + 1) + respFilename + ("1".equals(minFile) ? "_min" : "") + type + "&" + fileName;
            filePath_LowerCaseExt = remoteFilenameFallbackForOldCapturer.substring(0, remoteFilenameFallbackForOldCapturer.lastIndexOf("/") + 1) + respFilename + ("1".equals(minFile) ? "_min" : "") + type.toLowerCase() + "&" + fileName;
            fallbackPathForOldCapturer = remoteFilenameFallbackForOldCapturer.substring(0, remoteFilenameFallbackForOldCapturer.lastIndexOf(".")) + ("1".equals(minFile) ? "_min" : "") + type + "&" + fileName;
            fallbackPathForOldCapturer_LowerCaseExt = remoteFilenameFallbackForOldCapturer.substring(0, remoteFilenameFallbackForOldCapturer.lastIndexOf(".")) + ("1".equals(minFile) ? "_min" : "") + type.toLowerCase() + "&" + fileName;
            reType = Md5Tools.getMD5(remoteFilenameFallbackForOldCapturer_LowerCaseExt.getBytes());
            fallbackPathForOldCapturer2 = remoteFilenameFallbackForOldCapturer.substring(0, remoteFilenameFallbackForOldCapturer.lastIndexOf("/") + 1) + reType + ("1".equals(minFile) ? "_min" : "") + type + "&" + fileName;
            fallbackPathForOldCapturer2_LowerCaseExt = remoteFilenameFallbackForOldCapturer.substring(0, remoteFilenameFallbackForOldCapturer.lastIndexOf("/") + 1) + reType + ("1".equals(minFile) ? "_min" : "") + type.toLowerCase() + "&" + fileName;
        }

        if ("NULL".equals(filePath)) {
            try {
                response.sendError(0);
            } catch (Exception var25) {
                var25.printStackTrace();
            }
        } else {
            remoteFilename = filePath.substring(0, filePath.lastIndexOf("&"));
            remoteFilenameFallbackForOldCapturer = fallbackPathForOldCapturer == null ? null : fallbackPathForOldCapturer.substring(0, fallbackPathForOldCapturer.lastIndexOf("&"));
            String remoteFilenameFallbackForOldCapturer2 = fallbackPathForOldCapturer2 == null ? null : fallbackPathForOldCapturer2.substring(0, fallbackPathForOldCapturer2.lastIndexOf("&"));
            String remoteFilename_LowerCaseExt = filePath_LowerCaseExt == null ? null : filePath_LowerCaseExt.substring(0, filePath_LowerCaseExt.lastIndexOf("&"));
            remoteFilenameFallbackForOldCapturer_LowerCaseExt = fallbackPathForOldCapturer_LowerCaseExt == null ? null : fallbackPathForOldCapturer_LowerCaseExt.substring(0, fallbackPathForOldCapturer_LowerCaseExt.lastIndexOf("&"));
            remoteFilenameFallbackForOldCapturer2_LowerCaseExt = fallbackPathForOldCapturer2_LowerCaseExt == null ? null : fallbackPathForOldCapturer2_LowerCaseExt.substring(0, fallbackPathForOldCapturer2_LowerCaseExt.lastIndexOf("&"));
            respFilename = filePath.substring(filePath.lastIndexOf("&") + 1);
            if (type != null) {
                type = type.toLowerCase(Locale.ENGLISH);
                reType = "text/html";
                String reFileName = respFilename;
                if (!respFilename.contains(".") && !"".equals(type)) {
                    reFileName = respFilename + type;
                }

                if (type.equals(".xls")) {
                    reType = "application/msexcel";
                } else if (type.equals(".xlsx")) {
                    reType = "application/msexcel";
                } else if (type.equals(".pdf")) {
                    reType = "application/pdf";
                } else if (type.equals(".doc")) {
                    reType = "application/msword";
                } else if (type.equals(".jpg")) {
                    reType = "image/jpeg";
                } else if (type.equals(".png")) {
                    reType = "image/png";
                } else if (type.equals(".txt")) {
                    reType = "text/plain";
                } else if (type.equals(".docx")) {
                    reType = "application/msword";
                } else if (type.equals(".zip")) {
                    reType = "application/x-zip-compressed";
                } else if (type.equals(".rar")) {
                    reType = "application/octet-stream";
                }

                response.setContentType(reType + ";charset=utf-8");

                try {
                    reFileName = URLEncoder.encode(reFileName, "UTF-8");
                    reFileName = reFileName.replace("+", "%20");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + reFileName + "\"");
                } catch (UnsupportedEncodingException var27) {
                    var27.printStackTrace();
                } catch (Exception var28) {
                    var28.printStackTrace();
                }

                response.setCharacterEncoding("utf-8");
            }

            try {
                OutputStream os = response.getOutputStream();
                if (!this.getPicFromFtp(remoteFilename, os) && remoteFilename_LowerCaseExt != null && !this.getPicFromFtp(remoteFilename_LowerCaseExt, os) && remoteFilenameFallbackForOldCapturer != null && !this.getPicFromFtp(remoteFilenameFallbackForOldCapturer, os) && remoteFilenameFallbackForOldCapturer_LowerCaseExt != null && !this.getPicFromFtp(remoteFilenameFallbackForOldCapturer_LowerCaseExt, os) && remoteFilenameFallbackForOldCapturer2 != null && !this.getPicFromFtp(remoteFilenameFallbackForOldCapturer2, os) && fallbackPathForOldCapturer2_LowerCaseExt != null) {
                    this.getPicFromFtp(remoteFilenameFallbackForOldCapturer2_LowerCaseExt, os);
                }
            } catch (Exception var26) {
                var26.printStackTrace();
            }

        }
    }

    public void configFtp() {
        this.ftpUrl = FTPServer.ftpIP;

        try {
            this.ftpPort = Integer.parseInt(FTPServer.port);
        } catch (Exception var1) {
            this.ftpPort = 21;
        }

        this.userName = FTPServer.ftpUser;
        this.password = FTPServer.ftpPwd;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String seq = request.getParameter("seq");
        String minFile = request.getParameter("minFile");
        String projid = request.getParameter("projid");
        String type = request.getParameter("type");
        boolean isCap = "true".equals(request.getParameter("isCapturer"));
        if (seq == null) {
            seq ="nulla";
            //seq = this.getSequence(projid).toString();
        } else {
            try {
                seq = (new MyDes("seq2017needenc!!!~")).decrypt(seq);
            } catch (Exception var13) {
            }
        }

        String fileName;
        if ("1".equals(minFile)) {
            fileName = seq + "_min";
        } else {
            fileName = seq;
        }

        FtpUtil fu = new FtpUtil();

        try {
            InputStream in = request.getInputStream();
            String flag = fu.uploadFile(this.ftpUrl, this.ftpPort, this.userName, this.password, in, seq, fileName, type, seq, projid, !"1".equals(minFile), isCap);
            String[] results = flag.split(":");
            if (results[0].equals("Err")) {
                if (results[1].equals("1")) {
                    this.result = "ERROR:服务器hostname或port有误";
                } else if (results[1].equals("2")) {
                    this.result = "ERROR:用户名密码有误";
                } else if (results[1].equals("3")) {
                    this.result = "ERROR:服务器创建目录失败";
                } else if (results[1].equals("4")) {
                    this.result = "ERROR:写入后台数据库出错";
                } else if (results[1].equals("5")) {
                    this.result = "ERROR:写入文件流时出错";
                }
            } else {
                this.result = results[1];
            }
        } catch (IOException var12) {
            this.result = "ERROR:IOException";
            var12.printStackTrace();
        }

        return this.result;
    }

    private File generateThumbImg(File bigImgFile, int height) throws Exception {
        File minifiedFile = null;
        BufferedImage img = ImageIO.read(bigImgFile);
        int oriWidth = img.getWidth();
        int oriHeight = img.getHeight();
        if (oriWidth < 800 && oriHeight < height * 2) {
            return bigImgFile;
        } else {
            int toWidth = (int)Float.parseFloat(String.valueOf(oriWidth * height)) / oriHeight;
            BufferedImage minifiedImg = new BufferedImage(toWidth, height, 1);
            minifiedImg.getGraphics().drawImage(img.getScaledInstance(toWidth, height, 4), 0, 0, (ImageObserver)null);
            minifiedFile = new File(bigImgFile.getCanonicalPath() + ".min");
            ImageIO.write(minifiedImg, "JPG", minifiedFile);
            minifiedImg.flush();
            return minifiedFile;
        }
    }

    public String save(File doc, String type, String originFilename, String projid, String seq, boolean isMinFile, boolean isCap) {
        if (seq == null) {
            seq="nulla";
//            seq = this.getSequence(projid).toString();
        }

        String fileName;
        if (isMinFile) {
            fileName = seq + "_min";
        } else {
            fileName = seq;
        }

        FtpUtil fu = new FtpUtil();

        try {
            InputStream in = new FileInputStream(doc);
            String flag = fu.uploadFile(this.ftpUrl, this.ftpPort, this.userName, this.password, in, seq, fileName, type, originFilename, projid, !isMinFile, isCap);
            if ("jpg".equalsIgnoreCase(type) || "jpeg".equalsIgnoreCase(type) || isCap) {
                File thumbImg = this.generateThumbImg(doc, 120);
                InputStream in_min = new FileInputStream(thumbImg);
                fu.uploadFile(this.ftpUrl, this.ftpPort, this.userName, this.password, in_min, seq, seq + "_min", type, originFilename, projid, false, isCap);
            }

            String[] results = flag.split(":");
            if (results[0].equals("Err")) {
                if (results[1].equals("1")) {
                    this.result = "ERROR:文件服务器不可用（后台配置的ftp服务器地址或端口错误）";
                } else if (results[1].equals("2")) {
                    this.result = "ERROR:文件服务器不可用（后台配置的ftp用户名密码错误）";
                } else if (results[1].equals("3")) {
                    this.result = "ERROR:文件服务器访问错误或权限不足（创建目录失败）";
                } else if (results[1].equals("4")) {
                    this.result = "ERROR:写入后台数据库出错";
                } else if (results[1].equals("5")) {
                    this.result = "ERROR:文件服务器访问错误或权限不足（写入文件流时出错）";
                }
            } else {
                this.result = results[1];
                if (!isCap) {
                    this.result = (new MyDes("seq2017needenc!!!~")).encrypt(this.result);
                }
            }
        } catch (IOException var14) {
            this.result = "ERROR:文件服务器访问错误（IOException）";
            var14.printStackTrace();
        } catch (Exception var15) {
            this.result = "ERROR:其他错误（可能是文件序号加密失败）";
            var15.printStackTrace();
        }

        return this.result;
    }

    public boolean getPicFromFtp(String filePath, OutputStream os) {
        FtpUtil fu = new FtpUtil();
        filePath = filePath.trim();
        String dir = filePath.substring(0, filePath.lastIndexOf("/"));
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        return fu.downFile(this.ftpUrl, this.ftpPort, this.userName, this.password, dir, fileName, os);
    }

    protected int setFilePredelete(String seq, String name, String projid) throws SQLException {
        ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
        Connection conn = null;
        int rowsdeleted = 0;
        String sql = null;
        if (name.contains("_min")) {
            sql = "update FILESYSTEMMAP set DELMARK ='Y' where seq = '" + seq + "' and filename='" + name + "'";
        } else {
            sql = "update FILESYSTEMMAP set DELMARK ='Y' where seq = '" + seq + "'";
        }

        try {
            conn = pool.getConnection();
            Statement stmt = conn.createStatement();
            rowsdeleted = stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception var12) {
            var12.printStackTrace();
            conn.rollback();
        } finally {
            pool.returnConnection(conn);
        }

        return rowsdeleted > 0 ? 0 : 1;
    }

    protected int confirmFileList(String seqList, String delSeqList, String projid) throws SQLException {
        ConnectionPool pool;
        pool = ConnectionPoolManager.getPool("CMServer");

        Connection conn = null;
        int rowsconfirmed = 0;
        String sql = null;
        if (!seqList.equals("")) {
            String seqListInSql = "'" + seqList.replace("|", "','") + "'";
            sql = "update FILESYSTEMMAP set USEDMARK = 'Y', DELMARK = 'N' where seq in (" + seqListInSql + ")";
        }

        try {
            conn = pool.getConnection();
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            if (sql != null) {
                rowsconfirmed += stmt.executeUpdate(sql);
            }

            stmt.close();
            conn.commit();
        } catch (Exception var12) {
            var12.printStackTrace();
            conn.rollback();
        } finally {
            pool.returnConnection(conn);
        }

        return rowsconfirmed > 0 ? 0 : 1;
    }

    public String getFilePath(String seq, String projid) {
        String filePath = null;

        try {
            filePath = this.getFilePathPrivate(seq, "", projid);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return filePath;
    }

    //
    protected String getFilePathPrivateQCopy(String seq,String name,String projid){
        projid ="WF_NYD";
        return projid+"/"+"pic"+"/"+seq+"&"+name;
    }

    protected String getFilePathPrivate(String seq, String name, String projid) throws SQLException {
        ConnectionPool pool;
        pool = ConnectionPoolManager.getPool("CMServer");

        Connection conn = null;
        String sql = "select path, filename from FILESYSTEMMAP where seq = '" + seq + "'";
        String filePath = "NULL";

        try {
            conn = pool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String fullPath = CommonOperation.nTrim(rs.getString(1));
                if (name.endsWith("_min")) {
                    fullPath = fullPath.substring(0, fullPath.lastIndexOf(".")) + "_min" + fullPath.substring(fullPath.lastIndexOf("."));
                }
                filePath = fullPath + '&' + CommonOperation.nTrim(rs.getString(2));
            }

            stmt.close();
        } catch (Exception var14) {
            var14.printStackTrace();
            conn.rollback();
        } finally {
            pool.returnConnection(conn);
        }

        return filePath;
    }

}
