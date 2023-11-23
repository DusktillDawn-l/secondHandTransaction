package com.leon.controller;

import com.leon.mapper.UserMapper;
import com.leon.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    UserMapper userMapper;
    User user  = new User();
    @RequestMapping("/register")
    public String register(
            @RequestParam("regUserName") String username,
            @RequestParam("regPwd")String pwd,
            @RequestParam("regPhoneNumber") String phoneNumber,
            @RequestParam("regAddress") String address, Model model){
        if (userMapper.queryUserByName(username)!=null){
            model.addAttribute("msg","用户名已存在");
            return "register";
        }
        else {
            user.setUserName(username);
            user.setPassword(pwd);
            user.setPhoneNumber(phoneNumber);
            user.setAddress(address);
            userMapper.addUser(user);
            model.addAttribute("msg","注册成功");
            return "register";
        }
    }
}
