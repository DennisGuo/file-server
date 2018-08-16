package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.common.JSONResponse;
import cn.geobeans.server.file.common.L;
import cn.geobeans.server.file.service.FileData;
import cn.geobeans.server.file.service.FileDataService;
import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Cleanup;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有handler的基类
 */
public class BaseHandler implements HttpHandler {

    protected static final String CHARSET = "UTF-8";
    protected static final String KEY_CONTENT_TYPE = "Content-Type";


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        L.d(String.format("%s %s , requested from %s",
                httpExchange.getRequestMethod(),
                httpExchange.getRequestURI().toString(),
                httpExchange.getRemoteAddress().toString()));
    }

    /**
     *
     * @param httpExchange
     * @param body
     */
    protected void json(HttpExchange httpExchange,JSONResponse body) throws IOException {
        Map<String,String> header = new HashMap<>();
        header.put(KEY_CONTENT_TYPE,"application/json; charset="+CHARSET);
        send(httpExchange, JSON.toJSONString(body).getBytes(CHARSET),header);
    }


    protected void send(HttpExchange httpExchange, byte[] bytes, Map<String,String> header) throws IOException {
        setHeaders(httpExchange,header);
        httpExchange.sendResponseHeaders(200, bytes.length);
        @Cleanup OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
    }

    private void setHeaders(HttpExchange httpExchange, Map<String, String> header) {
        if(header!=null && header.keySet().size() > 0 ) {
            for(String key:header.keySet()){
                httpExchange.getResponseHeaders().set(key,header.get(key));
            }
        }
        httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin","*");
    }

    /**
     * 发送文件
     * @param httpExchange
     * @param data
     * @throws IOException
     */
    protected void sendFile(HttpExchange httpExchange, FileData data) throws IOException {
        Map<String, String> header = new HashMap<>();
        header.put(KEY_CONTENT_TYPE, data.getContentType());
        header.put("Content-Disposition", "attachment;filename=" + URLEncoder.encode(data.getName(), CHARSET));
        setHeaders(httpExchange,header);
        File file = FileDataService.getFile(data);
        if(file.exists()){
            httpExchange.sendResponseHeaders(200, Files.size(file.toPath()));
            @Cleanup OutputStream os = httpExchange.getResponseBody();
            @Cleanup FileInputStream is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = is.read(buffer)) > 0){
                os.write(buffer,0,len);
            }
            os.flush();

        }else{
            throw new RuntimeException("File not exist on the disk or be removed.");
        }
//        httpExchange.sendResponseHeaders(200, bytes.length);
    }

}
