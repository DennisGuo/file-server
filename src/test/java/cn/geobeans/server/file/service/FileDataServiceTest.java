package cn.geobeans.server.file.service;

import cn.geobeans.server.file.BaseTest;
import cn.geobeans.server.file.common.PageList;
import cn.geobeans.server.file.config.Database;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;


public class FileDataServiceTest extends BaseTest {

    @Test
    public void save(){
        FileData data = new FileData();
        data.setName("测试文件.jpg");
        data.setMd5("sdfasdfasdfasdfasdf");
        data.setContentType("image/jpeg");
        data.setSize(2340534L);

        try {
            data = FileDataService.save(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        printJson(data);
    }

    @Test
    public void getByMd5(){
        String md5 = "sdfasdfasdfasdfasdf";
        FileData data = null;
        try {
            data = FileDataService.getByMd5(md5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printJson(data);
    }

    @Test
    public void delete() throws SQLException {
        String md5 = "sdfasdfasdfasdfasdf";
        System.out.println(FileDataService.deleteByMd5(md5));
    }

    @Test
    public void getList() throws SQLException {
        PageList<FileData> rs = FileDataService.getList(1,20);
        printJson(rs);
    }

    @Test
    public void getStatus(){
        try {
            printJson(FileDataService.getStatus());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}