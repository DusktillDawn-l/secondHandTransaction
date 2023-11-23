package com.leon.mapper;

import com.leon.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {
    List<Admin> queryAdminList();
    Admin queryAdminByName(String adminName);
}
