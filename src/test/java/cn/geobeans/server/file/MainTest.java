package cn.geobeans.server.file;

import cn.geobeans.server.file.service.FileData;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Calendar;

@RunWith(JUnit4.class)
public class MainTest {

    @Test
    public void date(){
        String[] arr = "p1.pxx-23.xswde.jpg".split("\\.");
        String type = arr[arr.length-1];
        System.out.println(type);
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.YEAR) + "" +new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1)+ "" + new DecimalFormat("00").format(cal.get(Calendar.DATE));
        System.out.println(date);
    }

    @Test
    public void replace(){
        System.out.println("/tmp/imageserver/images/20180420/1524210523437_thumb.jpg".replace("/tmp/imageserver/images",""));
    }

    @Test
    public void contentType(){
        try {
            System.out.println(Files.probeContentType(Paths.get("/home/ghx/workspace/java/file-server/data/p1.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lengthTest(){
        String str = "guohegnxi,!#  $$qwe  rtas  dfg";
        System.out.println("String length :"+ str.length());
        System.out.println("byte[] length :"+ str.getBytes().length);
    }

    @Test
    public void jsonTest(){
        String json = "{\n" +
                "  \"MD5\": \"7d35ed24-2037-4982-9ce6-ac3c97058259\",\n" +
                "  \"NAME\": \"OBSERVATION_RECORD\",\n" +
                "  \"CONTENT_TYPE\":\"application/printJson\",\n" +
                "  \"SIZE\":12345\n" +
                "}";
        FileData rs = JSON.parseObject(json,FileData.class);
        System.out.println(JSON.toJSONString(rs,true));
    }

    @Test
    public void encode() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("loveyouX2323#.jpg","UTF-8"));
    }
}
