package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.common.JSONResponse;
import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Cleanup;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有handler的基类
 */
public class BaseHandler implements HttpHandler {

    protected static final String CHARSET = "UTF-8";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }

    /**
     *
     * @param httpExchange
     * @param body
     */
    protected void json(HttpExchange httpExchange,JSONResponse body) throws IOException {
        Map<String,String> header = new HashMap<>();
        header.put("ContentType","application/json; charset="+CHARSET);
        send(httpExchange, JSON.toJSONString(body).getBytes(CHARSET),header);
    }


    protected void send(HttpExchange httpExchange, byte[] bytes, Map<String,String> header) throws IOException {
        if(header!=null && header.keySet().size() > 0 ) {
            for(String key:header.keySet()){
                httpExchange.getResponseHeaders().set(key,header.get(key));
            }
        }
        httpExchange.sendResponseHeaders(200, bytes.length);
        @Cleanup OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

}
