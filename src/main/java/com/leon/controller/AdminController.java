package com.leon.controller;
import com.leon.mapper.AdminMapper;
import com.leon.mapper.CommodityMapper;
import com.leon.mapper.OrderMapper;
import com.leon.mapper.UserMapper;
import com.leon.pojo.Commodity;
import com.leon.pojo.Order;
import com.leon.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommodityMapper commodityMapper;

    @RequestMapping("/orderManagement")
    public String orderManagement(Model model){
        List<Order> orders = orderMapper.queryOrderList();
        model.addAttribute("orders",orders);
        return "admin/orderManagement";
    }

    @RequestMapping("/userManagement")
    public String userManagement(Model model){
        List<User> users = userMapper.queryUserList();
        model.addAttribute("users",users);
        return "admin/userManagement";
    }

    @RequestMapping("/commodityManagement")
    public String queryAllCommodity(HttpSession session, Model model) {
        List<Commodity> allCommodities = commodityMapper.queryAllCommodityList();
        List<User> allUsers = new ArrayList<>();
        for (Commodity commodity : allCommodities) {
            allUsers.add(userMapper.queryUserById(commodity.getSellerId()));
        }
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("allCommodities", allCommodities);
        return "admin/commodityManagement";
    }
    @RequestMapping("/commoditySearch")
    public String commoditySearch(
            @RequestParam("commodityName") String commodityName,
            Model model){
        List<Commodity> allCommodities = commodityMapper.queryCommodityByName(commodityName);
        if(CollectionUtils.isEmpty(allCommodities)){
            model.addAttribute("msg","未查找到相应商品");
            return "admin/commodityManagement";
        }
        model.addAttribute("allCommodities",allCommodities);
        return "admin/commodityManagement";
    }

    @RequestMapping("/orderSearch")
    public String orderSearch(
            @RequestParam("commodityName") String commodityName,
            Model model){
        List<Order> orders = orderMapper.queryOrderByCommodityName(commodityName);
        if(CollectionUtils.isEmpty(orders)){
            model.addAttribute("msg","未查找到相应订单");
            return "admin/orderManagement";
        }
        model.addAttribute("orders",orders);
        return "admin/orderManagement";
    }

    @RequestMapping("/userSearch")
    public String userSearch(
            @RequestParam("userName") String userName,
            Model model){
        User user = userMapper.queryUserByName(userName);
        if(user == null)
        {
            model.addAttribute("msg","未查找到指定用户");
            return "admin/userManagement";
        }
        List<User> users = new ArrayList<>();
        users.add(user);
        model.addAttribute("users",users);
        return "admin/userManagement";
    }
}
