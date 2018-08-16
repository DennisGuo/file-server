package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.Application;
import cn.geobeans.server.file.common.JSONResponse;
import cn.geobeans.server.file.common.PageList;
import cn.geobeans.server.file.service.FileData;
import cn.geobeans.server.file.service.FileDataService;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StatusHandler extends BaseHandler {

    public StatusHandler() {
        super(GET);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            super.handle(httpExchange);
            // TODO： 查询当前状态，磁盘：使用量/总容量

            super.json(httpExchange,new JSONResponse(FileDataService.getStatus()));
        } catch (Exception e) {
            e.printStackTrace();
            super.json(httpExchange,new JSONResponse(e.getMessage()));
        }
    }
}
