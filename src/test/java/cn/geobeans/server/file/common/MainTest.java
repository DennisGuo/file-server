package cn.geobeans.server.file.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
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
}
