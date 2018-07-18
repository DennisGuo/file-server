package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.common.JSONResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class IndexHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        super.handle(httpExchange);
        //String response = JSON.toJSONString(new JSONResponse(true,"Welcome To GeoBeans File Server."));
        String[] arr = new String[]{
                "/upload","Upload file multipart/form-data.",
                "/get/{md5}","Get file info with md5.",
                "/download/{md5}","Download file with md5.",
                "/list?page&limit","Get file list with pages.",
        };
        //定义API
        JSONArray apis = new JSONArray();
        for(int i=0;i<arr.length;i=i+2){
            JSONObject api = new JSONObject();
            api.put("uri",arr[i]);
            api.put("description",arr[i+1]);
            apis.add(api);
        }

        JSONObject data = new JSONObject();
        data.put("author","guohengxi.dennis@gmail.com");
        data.put("apis",apis);

        super.json(httpExchange,new JSONResponse(true,"Welcome To GeoBeans File Server.",data));
    }

}
