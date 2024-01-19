package com.blog4java.mybatis.sqlsession;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class SqlSessionExample {

    @Test
    public void testSqlSession() throws IOException {
        // 获取Mybatis配置文件输入流，这个里面包含了所有的MyBatis的配置信息，包含enviorment等
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        // 通过SqlSessionFactoryBuilder创建SqlSessionFactory实例
        // 创建SqlSession的需要使用SqlSessionFactory进行创建，这个SqlSessionFactory中包含了Configuration引用
        // 里面包含了全部的配置信息，完成对所有标签的解析
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        // 调用SqlSessionFactory的openSession（）方法，创建SqlSession实例
        // 创建出来的SqlSession用来和数据库进行交互，SqlSession是一个装饰者模式，SqlSession中整合了Executor类
        // Executor用来执行真正的sql
        SqlSession session = sqlSessionFactory.openSession();
    }
}
