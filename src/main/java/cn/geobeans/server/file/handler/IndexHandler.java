package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.common.JSONResponse;
import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class IndexHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //String response = JSON.toJSONString(new JSONResponse(true,"Welcome To GeoBeans File Server."));
        super.json(httpExchange,new JSONResponse(true,"Welcome To GeoBeans File Server."));
    }

}
