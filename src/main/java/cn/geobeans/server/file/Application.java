package cn.geobeans.server.file;

import cn.geobeans.server.file.common.L;
import cn.geobeans.server.file.handler.IndexHandler;
import cn.geobeans.server.file.handler.StorageHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Application {

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

        L.d("Welcome to GeoBeans File Server.");

        for(int i=0;i< args.length;i++){
            String key = args[i];
            if(key.equals("-p")){
                try {
                    PORT = Integer.parseInt(args[i+1]);
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                    L.e("请使用正确的端口, 示例: -p "+PATH);
                }
            }else if(key.equals("-t")){
                try {
                    MAX_THREAD = Integer.parseInt(args[i+1]);
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                    L.e("请使用正确的最大线程数量, 示例: -t "+MAX_THREAD);
                }
            }else if(key.equals("-h") || key.equals("--help")){
                L.i(String.format("\n-p \t 服务端口地址[默认:%s], 示例: -p %s\n-t \t 最大线程数量[默认:%s], 示例: -t %s\n-s \t 文件存储地址[默认:%s], 示例: -s %s",
                        PORT,PORT,
                        MAX_THREAD,MAX_THREAD,
                        PATH,PATH)
                );
                return ;
            }
        }
        init();
    }

    private static void init() {
        L.i(String.format("\n存储路径: \t%s\n服务端口:\t %s\n最大线程数量: \t%s",PATH,PORT,MAX_THREAD));
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new IndexHandler());
            server.createContext("/storage", new StorageHandler());
            server.setExecutor(getHttpExecutor()); // creates a default executor
            server.start();
            L.i(String.format("请访问：http://localhost:%s/",PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Executor getHttpExecutor() {
        return Executors.newFixedThreadPool(MAX_THREAD);
    }

}
