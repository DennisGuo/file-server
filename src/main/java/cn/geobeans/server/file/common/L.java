package cn.geobeans.server.file.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志输入工具
 */
public class L {

    /**
     * DEBUG 日志
     * @param msg
     */
    public static void d(String msg) {
        print(msg,"DEBUG");
    }
    /**
     * INFO 日志
     * @param msg
     */
    public static void i(String msg) {
        print(msg,"INFO");
    }

    /**
     * ERROR 日志
     * @param msg
     */
    public static void e(String msg) {
        print(msg,"ERROR");
    }

    private static void print(String msg,String level){
        String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        System.out.println(String.format("[%s][%s] %s",time,level,msg) );
    }
}
