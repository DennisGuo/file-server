package cn.geobeans.server.file.common;

import lombok.Data;

@Data
public class JSONResponse {

    private Boolean success;
    private String message;
    private Object data;

    public JSONResponse() {
    }

    public JSONResponse(Object data) {
        this.success = true;
        this.data = data;
    }

    public JSONResponse(String message) {
        this.message = message;
    }

    public JSONResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JSONResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
