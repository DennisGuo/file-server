package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.common.JSONResponse;
import cn.geobeans.server.file.common.PageList;
import cn.geobeans.server.file.service.FileData;
import cn.geobeans.server.file.service.FileDataService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 分页获取文件信息
 */
public class ListHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        super.handle(httpExchange);
        try {
            String query = httpExchange.getRequestURI().getQuery();
            int page = 1,limit=10;
            if(query!=null && !query.trim().equals("")){
                String[] kvs = query.split("&");
                for(String kv:kvs){
                    String[] arr = kv.split("=");
                    if(arr[0].equals("page")){
                        int tmp = Integer.parseInt(arr[1]);
                        page = tmp <= 0 ? 1: page;
                    }else if(arr[0].equals("limit")){
                        int tmp = Integer.parseInt(arr[1]);
                        limit = tmp <= 0 ? 10: limit;
                    }
                }
            }
            PageList<FileData> rs = FileDataService.getList(page,limit);

            super.json(httpExchange,new JSONResponse(rs));
        } catch (Exception e) {
            e.printStackTrace();
            super.json(httpExchange,new JSONResponse(e.getMessage()));
        }
    }

}
