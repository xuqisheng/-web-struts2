package wingsoft.shopping.service.custom;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.config.RequestConfig;
import wingsoft.tool.common.Md5Tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

public class CreateStaticFile {
    private static final String PATH = CreateStaticFile.class.getClassLoader().getResource("").getPath();
    private static final String DIRECTORY_PATH = PATH +"/json/";

    @Deprecated
    public boolean createFile(String url){
        boolean status = false;
        int statusCode = 0;
        HttpClient httpClient = null;
        GetMethod getMethod = null;
        StringBuffer sb = null;
        InputStream in = null;
        BufferedReader br = null;
        try{
            httpClient = new HttpClient();
            getMethod = new GetMethod(url);
            getMethod.getParams().setSoTimeout(1000);
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
            getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            getMethod.addRequestHeader("Content-Type","text/html;charset=UTF-8");
            statusCode = httpClient.executeMethod(getMethod);
            String line = null;
            String pageContent = null;
            if (statusCode!=200) {
                System.out.println("静态页面引擎在解析"+url+"产生静态页面时出错!");
                return status;
            }else{
                sb = new StringBuffer();
                in = getMethod.getResponseBodyAsStream();
                br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                while((line=br.readLine())!=null){
                    sb.append(line+"\n");
                }
                if(br!=null) br.close();
                pageContent = sb.toString();
                try{
                    System.out.println("开始写文件");
                    String json = "";
                    if(pageContent.startsWith("{")){
                        JSONObject jsonObject = JSONObject.fromObject(pageContent);
                        json = jsonObject.toString();
                    }else if (pageContent.startsWith("[")){
                        JSONArray jsonArray = JSONArray.fromObject(pageContent);
                        json = jsonArray.toString();
                    }

                    this.writeJsonFiles(url,json);
                    System.out.println("结束写文件");
                }catch (JSONException je){
                    //je.printStackTrace();
                    System.out.println("请求非json格式啊啊啊啊啊啊");
                    return false;
                }
                status = true;
            }
        }catch (JSONException jsonE){
            System.out.println("请求路径并不是json格式");
            return status;
        }catch(Exception ex){
            System.out.println("此链接超时");
            return status;
        }finally{
            getMethod.releaseConnection();
        }
//        System.out.println(status);
        return status;
    }

    // 写文件
    private synchronized void writeJsonFiles(String url, String pageContent) throws Exception{
        FileWriter fw = null;
        String fileName = Md5Tools.getMD5(url.getBytes());//update
        File fileDict = new File(PATH+"/json/");
        if(!fileDict.exists())
            fileDict.mkdirs();
        File file = new File(fileDict.getPath()+"/"+fileName+".json");
        if(!file.exists()){
            file.createNewFile();
        }
        System.out.println("file文件位置："+file);
        fw = new FileWriter(file);
        fw.write(pageContent);
        if(fw!=null)
            fw.close();
    }

    private synchronized boolean deleteFiles(String url){
        File file = new File(url);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (url.endsWith(File.separator)) {
                temp = new File(url + tempList[i]);
            } else {
                temp = new File(url + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteFiles(url + "/" + tempList[i]);//先删除文件夹里面的文件
                return true;
            }
        }
        return true;
    }


    //根据请求判断文件是否存在//存在返回
    public String getIndex(String name){
        String fileName = "notExists";
        try {
            fileName = Md5Tools.getMD5(name.getBytes());
            File dicFile = new File(PATH+"/json/");
            HashSet<String> hashString=null;
      //      System.out.println("dicFile:"+dicFile);
            if(dicFile.isDirectory()) {
                MyFilter filter = new MyFilter(".json");
                String strs[] = dicFile.list(filter);
                hashString= new HashSet<String>();
                for(int i=0;i<strs.length;i++){
                    strs[i] = strs[i].substring(0,strs[i].length()-5);
                    hashString.add(strs[i]);
                }
            }
          //  System.out.println("文件目录:"+hashString);
            if(hashString==null||!hashString.contains(fileName)){
                return "notExists";
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return fileName;
    }

    /**
     * @param urlStr
     * @oaran content
     * @return
     */
    public String getURLContent(String urlStr,String result) {
        String json = "";
//        System.out.println("开始写文件:"+result);
        if(result.startsWith("{")){
            JSONObject jsonObject = JSONObject.fromObject(result);
            json = jsonObject.toString();
        }else if (result.startsWith("[")){
            JSONArray jsonArray = JSONArray.fromObject(result);
            json = jsonArray.toString();
        }else {
            json = result;
        }
        try{
            this.writeJsonFiles(urlStr,json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String returnJson(String fileName){
        String filePath = PATH+"json/"+fileName+".json";
        String str = "";
        try {
            FileReader file = new FileReader(filePath);
            BufferedReader br = new BufferedReader(file);
            do{
                str = br.readLine();
            }while(br.read()!=-1);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return str;
    }

//        public static void main(String[] args) throws Exception{
//        String url1 ="http://localhost:8080/shopping/index/SHOP_GetRECOMMEND.action";
//        String url2  ="http://localhost:8080/shopping/index/SHOP_GetCart.action";
//        String url3 ="http://localhost:8080/shopping/index/SHOP_CATEGORY.action";
//        String url4 ="http://localhost:8080/shopping/index/SHOP_hot_search.action";
//        String url5 ="http://localhost:8080/shopping/index/MSG_GetMsgInfo.action";
//        String str [] ={url1,url2,url3,url4,url5};
//        CreateStaticFile csf = new CreateStaticFile();
//        for(int i=0;i<str.length;i++){
//            csf.writeJsonFiles(str[i],"[{a:'b',b:'b'}]");
//            System.out.println(csf.getIndex(str[i]));
//        }
////            boolean st = csf.deleteFiles(DIRECTORY_PATH);
////            System.out.println(st);
//    }
}