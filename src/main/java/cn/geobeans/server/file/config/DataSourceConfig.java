package cn.geobeans.server.file.config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class DataSourceConfig {

    private AppProperty appProperty;

    public DataSourceConfig(AppProperty appProperty) {
        this.appProperty = appProperty;
    }

    DataSource getDataSource(){
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:" + appProperty.getStorePath()+"/h2data");
        ds.setUser("sa");
        ds.setPassword("sa");

        return ds;
    }
}
