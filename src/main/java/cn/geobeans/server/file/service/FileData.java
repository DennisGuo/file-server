package cn.geobeans.server.file.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.fileupload.FileItem;

import java.util.Date;

@Data
@NoArgsConstructor
public class FileData {

    //文件的唯一标志MD5
    private String md5;
    //文件名称
    private String name;
    //文件的类型
    private String contentType;
    //文件的大小
    private Long size;
    //创建时间
    private Date createTime;

    public FileData(FileItem item, String md5) {
        this.md5 = md5;
        this.name = item.getName();
        this.contentType = item.getContentType();
        this.size = item.getSize();
    }
}
