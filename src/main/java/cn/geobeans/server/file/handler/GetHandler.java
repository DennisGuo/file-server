package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.common.JSONResponse;
import cn.geobeans.server.file.service.FileDataService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 根据MD5获取
 */
public class GetHandler extends BaseHandler {

    public GetHandler() {
        super(GET);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        JSONResponse rs;
        try {
            super.handle(httpExchange);
            String uri = httpExchange.getRequestURI().getRawPath();
            String[] arr = uri.split("/");
            String md5 = arr[arr.length - 1];
            if (md5 == null || md5.trim().equals("")) {
                rs = new JSONResponse("Incorrect md5.");
            } else {
                rs = new JSONResponse(FileDataService.getByMd5(md5));
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs = new JSONResponse(e.getMessage());
        }
        super.json(httpExchange, rs);
    }

}
