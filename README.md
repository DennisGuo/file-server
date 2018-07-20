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

## API test

- **/upload**

    Request
    
    ```bash
    curl -i -F 'file=@me.jpg' http://localhost:8888/upload
    ```
    
    Response
    
    ```json
    {
        "data": [
            {
                "contentType": "image/jpeg",
                "createTime": 1532058499067,
                "md5": "3909DBCAF2C02B51597E4A52ECE4FC39",
                "name": "me.jpg",
                "size": 21255
            }
        ],
        "success": true
    }
    ```

- **/get/{md5}**

    Request
    
    ```bash
    curl -i http://localhost:8888/get/3909DBCAF2C02B51597E4A52ECE4FC39
    ``` 
    
    Response
    
    ```json
    {
        "data": {
                "contentType": "image/jpeg",
                "createTime": 1532058499067,
                "md5": "3909DBCAF2C02B51597E4A52ECE4FC39",
                "name": "me.jpg",
                "size": 21255
        },
        "success": true
    }
    ```

- **/download/{md5}**

    Request
    
    ```bash
    curl -o me.jpg http://localhost:8888/download/3909DBCAF2C02B51597E4A52ECE4FC39
    ``` 

- **/list?page&limit**

    Request
    
    ```bash
    curl -i http://localhost:8888/list?page=1&limit=10
    ```
    
    Response
    
    ```json
    {
        "data": {
            "rows": [
                {
                    "contentType": "image/jpeg",
                    "createTime": 1532058577347,
                    "md5": "2DADEA65B5153D890B72FDBA00E04E4E",
                    "name": "d67c9a059b1a4a619a222bfd7126274a.jpg",
                    "size": 24590
                },
                {
                    "contentType": "image/jpeg",
                    "createTime": 1532058559807,
                    "md5": "3909DBCAF2C02B51597E4A52ECE4FC39",
                    "name": "me.jpg",
                    "size": 21255
                }
            ],
            "total": 2
        },
        "success": true
    }
    ```
