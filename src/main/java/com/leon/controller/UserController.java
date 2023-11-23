package com.leon.controller;

import com.leon.mapper.UserMapper;
import com.leon.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/checkUserInfo")
    public String checkUserInfo(HttpSession session, Model model){
        Integer userId = (Integer) session.getAttribute("loginUserId");
        if (userId == null){
            return "index";
        }
        User user = userMapper.queryUserById(userId);
        model.addAttribute("user",user);
        return "user/changeUserInfo";
    }

    @RequestMapping("/changeUserInfo")
    public String changeUserInfo(
            HttpSession session,
            @RequestParam("pwd") String pwd,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("address") String address) {
        User user = (User) session.getAttribute("user");
        user.setPassword(pwd);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        userMapper.updateUser(user);
        return "user/changeUserInfoSuccess";
    }
}
