package com.leon.controller;

import com.leon.mapper.AdminMapper;
import com.leon.mapper.UserMapper;
import com.leon.pojo.Admin;
import com.leon.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AdminMapper adminMapper;
    //处理用户登录
    //从前段收到username和password，然后与数据库中的查找是否匹配，若匹配，则跳转到个人界面，将用户名存放到session中
    //若不匹配，则跳转回主页并提示登录失败
    @RequestMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,Model model, HttpSession httpSession){
        User user = userMapper.queryUserByName(username);
        Admin admin = adminMapper.queryAdminByName(username);

        if (user==null&&admin==null) {
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        } else if (user!=null&&username.equals(user.getUserName())&&password.equals(user.getPassword())) {
            httpSession.setAttribute("loginUser",username);
            httpSession.setAttribute("loginUserId",user.getUserId());
            httpSession.setAttribute("user",user);
            return "redirect:market";
        } else if (admin!=null&&username.equals(admin.getAdminName())&&password.equals(admin.getPassword())) {
            httpSession.setAttribute("loginUser",username);
            httpSession.setAttribute("loginUserId",admin.getId());
            httpSession.setAttribute("Admin",admin);
            return "redirect:orderManagement";
        } else{
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:index.html";
    }

}
