package com.leon.mapper;

import com.leon.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    List<User> queryUserList();
    User queryUserByName(String userName);
    User queryUserById(int userId);
    String queryUserNameById(int userId);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
}
