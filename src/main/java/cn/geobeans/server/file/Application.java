package cn.geobeans.server.file;

import cn.geobeans.server.file.common.L;
import cn.geobeans.server.file.config.Database;
import cn.geobeans.server.file.handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Application {

    //是否打印调试信息
    public static Boolean DEBUG = true;
    //存储路径
    public static String PATH = "/tmp/storage";
    //端口
    private static Integer PORT = 8888;
    //最大线程数
    private static Integer MAX_THREAD = 2000;

    /**
     * 直接使用 java -jar file-server-all-xxx.jar 运行
     * 支持参数：-c -p -t -h
     * @param args
     */
    public static void main(String[] args) {
        if(welcome(args)){
            init();
        }
    }

    /**
     * 服务初始化
     */
    private static void init() {

        L.i(String.format("\nFile store path:\t%s\nServer port:\t%s\nMax threads:\t%s",PATH,PORT,MAX_THREAD));
        try {
            //初始化数据库
            Database.init();
            //启动HTTP服务
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new IndexHandler());
            server.createContext("/upload", new UploadHandler());
            server.createContext("/get", new GetHandler());
            server.createContext("/download", new DownloadHandler());
            server.createContext("/list", new ListHandler());
            server.setExecutor(getHttpExecutor()); // creates a default executor
            server.start();
            L.i(String.format("Please visit：http://localhost:%s/",PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 欢迎并读取配置参数
     * @param args
     */
    private static boolean welcome(String[] args) {
        boolean rs = true;
        L.i("Welcome to GeoBeans File Server.");
        for(int i=0;i< args.length;i++){
            String key = args[i];
            if(key.equals("-p")){
                try {
                    PORT = Integer.parseInt(args[i+1]);
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                    L.e("Please input correct port number, e.g. -p "+PORT);
                    rs = false;
                }
            }else if(key.equals("-t")){
                try {
                    MAX_THREAD = Integer.parseInt(args[i+1]);
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                    L.e("Please input correct max thread number , e.g. -t "+MAX_THREAD);
                    rs = false;
                }
            }else if(key.equals("-c")){
                try {
                    PATH = args[i+1];
                } catch (Exception e) {
                    //e.printStackTrace();
                    L.e("Please input correct path , e.g. -c "+PATH);
                    rs = false;
                }
            }else if(key.equals("-h") || key.equals("--help")){
                L.i(String.format("\n-p Server port, default:%s" +
                                "\n-t Max thread number, default:%s" +
                                "\n-s File store path, default:%s" +
                                "\n-h Show this help" +
                                "\nIssue commit:https://github.com/DennisGuo/file-server/issues",
                        PORT,
                        MAX_THREAD,
                        PATH)
                );
                rs = false;
            }
        }
        return rs;
    }

    private static Executor getHttpExecutor() {
        return Executors.newFixedThreadPool(MAX_THREAD);
    }

}
