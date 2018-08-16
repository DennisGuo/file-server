package cn.geobeans.server.file.handler;

import cn.geobeans.server.file.common.JSONResponse;
import cn.geobeans.server.file.service.FileData;
import cn.geobeans.server.file.service.FileDataService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadHandler extends BaseHandler{

    public UploadHandler() {
        super(POST);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            super.handle(httpExchange);
            DiskFileItemFactory d = new DiskFileItemFactory();
            ServletFileUpload up = new ServletFileUpload(d);
            List<FileData> rs = new ArrayList<>();
            List<FileItem> result = up.parseRequest(new HttpHandlerRequestContext(httpExchange));
            for(FileItem item:result){
                FileData tmp = FileDataService.save(item);
                if(tmp != null){
                    rs.add(tmp);
                }
            }
            super.json(httpExchange,new JSONResponse(rs));
        } catch (Exception e) {
            e.printStackTrace();
            super.json(httpExchange,new JSONResponse(false,e.getMessage()));
        }

    }
}
