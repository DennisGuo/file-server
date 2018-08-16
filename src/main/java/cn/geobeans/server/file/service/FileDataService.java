package cn.geobeans.server.file.service;

import cn.geobeans.server.file.Application;
import cn.geobeans.server.file.common.PageList;
import cn.geobeans.server.file.config.Database;
import com.alibaba.fastjson.JSONObject;
import com.twmacinta.util.MD5;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FileDataService {

    private static final String TABLE = "FILE";

    /**
     * 计算二进制文件的MD5值
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
//    public static String md5(byte[] data) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(data);
//        byte[] digest = md.digest();
//        return DatatypeConverter.printHexBinary(digest).toUpperCase();
//    }

    /**
     * 计算文件的md5
     *
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String md5(File file) throws IOException, NoSuchAlgorithmException {
//        byte[] data = Files.readAllBytes(file.toPath());
//        return md5(data);
        return MD5.asHex(MD5.getHash(file)).toUpperCase();
    }

    /**
     * 存储文件对象
     *
     * @param item
     * @return
     */
    public static FileData save(FileItem item) throws Exception {
        FileData fileData = null;

        //先存到tmp中，计算md5
        File file = new File(Application.PATH + File.separator + "tmp"+File.separator + UUID.randomUUID());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        item.write(file);
        String md5 = md5(file);

        try {
            fileData = getByMd5(md5);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        if (fileData != null) {
            //如果已经存在了就删除
            file.delete();
        } else {

            //移动到data中
            String dest = Application.PATH +File.separator + "data" + File.separator + md5 + "." + FilenameUtils.getExtension(item.getName());
            File target = new File(dest);
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            Files.move(file.toPath(), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
            //保存信息
            fileData = save(new FileData(item, md5));
        }
        return fileData;
    }

    /**
     * 存到数据库中
     *
     * @param data
     * @return
     */
    public static FileData save(FileData data) throws SQLException {
        data.setCreateTime(new Date());
        String sql = "INSERT INTO " + TABLE + "(MD5,NAME,CONTENT_TYPE,SIZE,CREATE_TIME) VALUES(?,?,?,?,?)";
        int rs = Database.executeUpdate(sql, data.getMd5(), data.getName(), data.getContentType(), data.getSize(), data.getCreateTime());
        if (rs > 0) {
            return data;
        } else {
            return null;
        }
    }

    /**
     * 根据md5获取FileData对象
     *
     * @param md5
     * @return
     */
    public static FileData getByMd5(String md5) throws SQLException {
        String sql = "SELECT * FROM " + TABLE + " WHERE MD5 = ?";
        List<JSONObject> rs = Database.executeQuery(sql, md5);
        if (rs.size() > 0) {
            JSONObject item = rs.get(0);
            return item.toJavaObject(FileData.class);
        }
        throw new RuntimeException("No file exist with md5:"+md5);
    }

    /**
     * 根据md5删除文件
     *
     * @param md5
     * @return
     */
    public static Boolean deleteByMd5(String md5) throws SQLException {
        String sql = "DELETE FROM " + TABLE + " WHERE MD5 = ?";
        int rs = Database.executeUpdate(sql, md5);
        return rs != 0;
    }

    /**
     * 读取文件二数据
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] getFileBytes(FileData data) throws IOException {
        String path = Application.PATH + File.separator +"data" + File.separator +data.getMd5() + "." + FilenameUtils.getExtension(data.getName());
        return Files.readAllBytes(Paths.get(path));
    }

    /**
     * 获取文件
     * @param data
     * @return
     */
    public static File getFile(FileData data){
        String path = Application.PATH + File.separator +"data" + File.separator +data.getMd5() + "." + FilenameUtils.getExtension(data.getName());
        return new File(path);
    }

    /**
     * 分页获取
     * @param page
     * @param limit
     * @return
     */
    public static PageList<FileData> getList(int page, int limit) throws SQLException {
        int total = getTotal();
        String sql = "SELECT * FROM "+TABLE +" ORDER BY MD5 LIMIT ? OFFSET ? ";
        List<JSONObject> rs = Database.executeQuery(sql,limit,((page-1)*limit));
        List<FileData> rows = new ArrayList<>();
        for(JSONObject item :rs){
            rows.add(item.toJavaObject(FileData.class));
        }
        return new PageList<>(total,rows);
    }

    /**
     * 获取总数
     * @return
     * @throws SQLException
     */
    public  static int getTotal() throws SQLException {
        String sql = "SELECT COUNT(1) FROM "+TABLE;
        List<JSONObject> rs = Database.executeQuery(sql);
        if(rs!=null &&rs.size()>0){
            return ((Number)rs.get(0).values().toArray()[0]).intValue();
        }
        return 0;
    }

    /**
     * 获取当前的存储状态
     * @return
     * @throws IOException
     */
    public static JSONObject getStatus() throws IOException, SQLException {
        FileStore store = Files.getFileStore(Paths.get(Application.PATH));
        long total = store.getTotalSpace();
        long usable = store.getUsableSpace();
        int count = getTotal();
        JSONObject rs = new JSONObject();
        rs.put("diskTotal",total);
        rs.put("diskUsable",usable);
        rs.put("total",count);
        return rs;
    }
}
