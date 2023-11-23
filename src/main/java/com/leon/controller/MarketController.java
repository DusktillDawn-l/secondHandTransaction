package com.leon.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class MarketController {
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderMapper orderMapper;


    //查看所有闲置商品
    @RequestMapping("/market")
    public String queryAllCommodity(HttpSession session,Model model){
        List<Commodity> allCommodities = commodityMapper.queryAllCommodityList();
        model.addAttribute("allCommodities",allCommodities);
        return "market";
    }

    //查看具体商品
    @RequestMapping("/market/{id}")
    public String querySelectedCommodity(
            @PathVariable("id") int id
            , Model model){
        Commodity commoditySelected = commodityMapper.queryCommodityById(id);
        model.addAttribute("commoditySelected",commoditySelected);
        return "commodityInfo";
    }

    //购买商品
    @RequestMapping("/market/{id}/buy")
    public String buySelectedCommodity(
            @PathVariable("id") Integer commodityId,
            HttpSession session,
            Model model){
        //查询买家信息
        Integer buyerId = (Integer) session.getAttribute("loginUserId");
        User buyer = userMapper.queryUserById(buyerId);
        //卖家信息
        Commodity commodity = commodityMapper.queryCommodityById(commodityId);
        Integer sellerId = commodity.getSellerId();
        User seller = userMapper.queryUserById(sellerId);

        if(buyer.userId.equals(seller.userId)){
            return "purchaseError";
        }

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setBuyerName(buyer.getUserName());
        order.setBuyerTele(buyer.getPhoneNumber());
        order.setSellerId(sellerId);
        order.setSellerName(seller.getUserName());
        order.setSellerTele(seller.getPhoneNumber());
        order.setCommodityId(commodityId);
        order.setCommodityName(commodity.getCommodityName());
        order.setBuyerAddress(buyer.getAddress());
        order.setSellerAddress(seller.getAddress());
        order.setPrice(commodity.getPrice());
        order.setTransactionTime(new Date());
        orderMapper.newOrder(order);
        model.addAttribute("commodityId",commodityId);
        model.addAttribute("order",order);
        commodityMapper.deleteCommodity(commodityId);
        return "user/orderSuccess";
    }

    @RequestMapping("/search")
    public String searchCommodity(
            @RequestParam("commodityName") String commodityName,
            Model model){
        List<Commodity> allCommodities = commodityMapper.queryCommodityByName(commodityName);
        model.addAttribute("allCommodities",allCommodities);
        return "market";
    }
}
