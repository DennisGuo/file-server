# File Server
A java based lightweight file storage server, provide file upload / store / download etc HTTP service.

## Features

- No other dependencies.
- Really lightweight, the executable jar file size is only `2.7M`.
- Quick deploy. 

## How to run 

> java -jar file-sever-all-1.0.0.jar 

Supported params:

- `-p` Server port, default:`8888`
- `-t` Max thread number, default:`2000`
- `-s` File store path, default:`/tmp/storage`
- `-h` Show this help


## API

```json
{
    
    "success": true,
    "message": "Welcome To GeoBeans File Server.",
    "data": {
        "apis": [
            {
                "description": "Upload file multipart/form-data",
                "uri": "/upload"
            },
            {
                "description": "Get file info with md5.",
                "uri": "/get/{md5}"
            },
            {
                "description": "Download file with md5.",
                "uri": "/download/{md5}"
            },
            {
                "description": "Get file list with pages.",
                "uri": "/list?page&limit"
            }
        ],
        "author": "guohengxi.dennis@gmail.com"
    }
}
```