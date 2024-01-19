package com.blog4java.mybatis.example;

import com.alibaba.fastjson.JSON;
import com.blog4java.common.DbUtils;
import com.blog4java.mybatis.example.entity.UserEntity;
import com.blog4java.mybatis.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @author liyongqi.0501
 * @date 2024/1/18 7:25 PM
 * @description
 */
public class MyExample {

    @Test
    public void testSqlSession() throws IOException {
        DbUtils.initData();
        // 获取Mybatis配置文件输入流，这个里面包含了所有的MyBatis的配置信息，包含enviorment等
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        // 通过SqlSessionFactoryBuilder创建SqlSessionFactory实例
        // 创建SqlSession的需要使用SqlSessionFactory进行创建，这个SqlSessionFactory中包含了Configuration引用
        // 里面包含了全部的配置信息，完成对所有标签的解析

        /*
        将XML文件解析成Configuration类的时候完成的工作：
            1. 注册MapperProxy对象到Configuration中的mapperRegistry属性当中
            2. 解析Mapper中的select|update|delete|insert标签对象，将每一个SQL语句包装成对应的MappedStatement
                MappedStatement属性中包含了SQL的相关的信息，其中sqlSource中会包含完整的sql信息，这个过程也会将<include>标签的sql替换为真实的sql

         */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        // 调用SqlSessionFactory的openSession（）方法，创建SqlSession实例
        // 创建出来的SqlSession用来和数据库进行交互，SqlSession是一个装饰者模式，SqlSession中整合了Executor类
        // Executor用来执行真正的sql,
        SqlSession session = sqlSessionFactory.openSession();
        // 获取Mapper类，Mapper是一个MapperProxy类型的动态代理对象，由Java动态代理创建，真正执行的是MapperProxy的invoke方法
        // Configuration中的MapperRegistry属性保存了所有的配置的Mapper
        // 调用session.getMapper()方法的时候
        UserMapper mapper = session.getMapper(UserMapper.class);
        // 执行真正查询的方法，调用invoke方法
        UserEntity userEntity = mapper.getUserById("0");
        System.out.println(JSON.toJSONString(userEntity));
    }

}
