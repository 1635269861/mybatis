package com.blog4java.jdbc;

import com.blog4java.common.DbUtils;
import com.blog4java.common.IOUtils;
import org.junit.Test;

import java.sql.*;

/**
 * @author liyongqi.0501
 * @date 2024/1/18 11:57 PM
 * @description
 */
public class PrepareStateExample {

    @Test
    public void testPrepareStateExample() {
        DbUtils.initData();
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            // 获取Connection对象
            Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis",
                    "sa", "");
            String sql = "select * from user where id = ?";
            // PreparedStatement这个类和普通的Statement来对：提供了占位符的功能，防止sql注入
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "0");
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columCount = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columCount; i++) {
                    String columName = metaData.getColumnName(i);
                    String columVal = resultSet.getString(columName);
                    System.out.println(columName + ":" + columVal);
                }
                System.out.println("--------------------------------------");
            }
            IOUtils.closeQuietly(stmt);
            IOUtils.closeQuietly(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
