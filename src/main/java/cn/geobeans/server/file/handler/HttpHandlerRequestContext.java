package cn.geobeans.server.file.handler;

import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.fileupload.RequestContext;

import java.io.IOException;
import java.io.InputStream;

class HttpHandlerRequestContext implements RequestContext {

        private HttpExchange http;

        public HttpHandlerRequestContext(HttpExchange http){
            this.http=http;
        }

        @Override
        public String getCharacterEncoding(){
            //Need to figure this out yet
            return "UTF-8";
        }

        @Override
        public int getContentLength(){
            //tested to work with 0 as return. Deprecated anyways
            return 0;
        }

        @Override
        public String getContentType(){
                //Content-Type includes the boundary needed for parsing
                return http.getRequestHeaders().getFirst("Content-type");
        }

        @Override
        public InputStream getInputStream()throws IOException {
                //pass on input stream
                return http.getRequestBody();
        }
}