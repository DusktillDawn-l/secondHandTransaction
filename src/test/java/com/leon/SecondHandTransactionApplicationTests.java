package com.leon;

import com.leon.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class SecondHandTransactionApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(dataSource.getClass());
        System.out.println(userMapper);

    }

}
