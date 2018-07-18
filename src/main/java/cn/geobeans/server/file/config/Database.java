package cn.geobeans.server.file.config;


import cn.geobeans.server.file.Application;
import cn.geobeans.server.file.common.L;
import cn.geobeans.server.file.common.U;
import com.alibaba.fastjson.JSONObject;
import lombok.Cleanup;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作
 */
public class Database {

    private static JdbcConnectionPool pool;

    public static void main(String[] args) {
        init();
    }


    /**
     * 初始化
     */
    public static void init() {

        String url = "jdbc:h2:" + Application.PATH + "/h2data";
        String user = "sa";
        String pwd = "sa";

        pool = JdbcConnectionPool.create(url, user, pwd);

        initSchema();
    }

    /**
     * 执行schema.sql
     */
    private static void initSchema() {
        String sql = U.getResourceFile("schema.sql");
        try {
            execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行SQL
     *
     * @param sql
     */
    public static void execute(String sql) throws SQLException {

        @Cleanup Connection conn = getConnection();
        @Cleanup PreparedStatement ps = conn.prepareStatement(sql);
        if(Application.DEBUG) L.d("Execute SQL : " + flatSql(sql));
        ps.execute();

    }

    /**
     * 执行SQL查询
     *
     * @param sql
     * @param args
     * @return
     */
    public static List<JSONObject> executeQuery(String sql, Object... args) throws SQLException {
        List<JSONObject> res = new ArrayList<>();

        @Cleanup Connection conn = getConnection();
        @Cleanup PreparedStatement ps = prepareSql(conn, sql, args);
        @Cleanup ResultSet rs = ps.executeQuery();
        if(Application.DEBUG) L.d("Execute SQL Query : " + flatSql(sql));
        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            JSONObject item = new JSONObject();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                item.put(meta.getColumnLabel(i), rs.getObject(i));
            }
            res.add(item);
        }
        return res;
    }

    /**
     * 执行SQL
     *
     * @param sql
     * @param args
     * @return
     */
    public static int executeUpdate(String sql, Object... args) throws SQLException {
        List<JSONObject> res = new ArrayList<>();

        @Cleanup Connection conn = getConnection();
        @Cleanup PreparedStatement ps = prepareSql(conn, sql, args);
        int num = ps.executeUpdate();
        if(Application.DEBUG) L.d("Execute SQL Update : " + flatSql(sql));
        return num;

    }

    private static String flatSql(String sql) {
        return sql.replace("\n","").replace("\t","");
    }

    private static PreparedStatement prepareSql(Connection conn, String sql, Object[] args) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        if (args != null) {
            for (int i = 1; i <= args.length; i++) {
                ps.setObject(i, args[i - 1]);
            }
        }
        return ps;
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        if (pool == null) {
            init();
        }
        return pool.getConnection();
    }
}
