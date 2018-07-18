package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.common.JSONResponse;
import cn.geobeans.server.file.service.FileData;
import cn.geobeans.server.file.service.FileDataService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DownloadHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        super.handle(httpExchange);
        try {
            String uri = httpExchange.getRequestURI().getRawPath();
            String[] arr = uri.split("/");
            String md5 = arr[arr.length - 1];
            JSONResponse rs;
            if (md5 == null || md5.trim().equals("")) {
                super.json(httpExchange, new JSONResponse("Incorrect md5."));
            } else {

                FileData data = FileDataService.getByMd5(md5);

                Map<String, String> header = new HashMap<>();
                header.put(KEY_CONTENT_TYPE, data.getContentType());
                header.put("Content-Disposition", "attachment;filename=" + URLEncoder.encode(data.getName(), CHARSET));

                byte[] bytes = FileDataService.getFileBytes(data);

                super.send(httpExchange, bytes, header);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            super.json(httpExchange, new JSONResponse(e.getMessage()));
        }
        ;
    }

}