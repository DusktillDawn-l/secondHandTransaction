package com.leon.controller;

import com.leon.mapper.CommodityMapper;
import com.leon.mapper.OrderMapper;
import com.leon.pojo.Commodity;
import com.leon.pojo.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MyCommodityController {
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    OrderMapper orderMapper;

    //查看自己目前正在出售的闲置商品
    @RequestMapping("/myCommodity")
    public String queryMyCommodity(HttpSession session, Model model){
        int userId = (int) session.getAttribute("loginUserId");
        List<Commodity> commodities = commodityMapper.queryCommodityList(userId);
        if (commodities.isEmpty()){
            model.addAttribute("msg","您目前没有出售闲置商品");
        }
        else {
            model.addAttribute("commodities",commodities);
        }

        return "myCommodity";
    }

    //编辑选中的闲置物品
    @RequestMapping("/myCommodity/{id}")
    public String update(@PathVariable("id") Integer id,Model model){
        Commodity commoditySelected = commodityMapper.queryCommodityById(id);
        model.addAttribute("commoditySelected",commoditySelected);
        return "user/update";
    }

    //保存更改的闲置信息
    @RequestMapping("/myCommodity/update/{id}")
    public String submitUpdate(
            @PathVariable("id") Integer commodityId,
            @RequestParam("commodityName") String commodityName,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            MultipartFile file,Model model){
            String originalFilename = file.getOriginalFilename();//原来图片名
            String ext ="."+originalFilename.split("\\.")[1];//获取文件后缀
            //基于图片一个随机的文件名
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = uuid+ext;
            //上传图片
            ApplicationHome applicationHome = new ApplicationHome(this.getClass());
            String pre = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() +
                    "\\src\\main\\resources\\static\\images\\";
            //程序运行在target下的classes目录中，所以需要dir获取classes路径，其上级目录为target，再上级目录为主程序目录，获取其绝对路径，再拼接上\src\main\resources\static\images\
            String path = pre + fileName;
            try {
                file.transferTo(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //存储商品到数据库
            Commodity commodity = new Commodity();
            commodity.setCommodityId(commodityId);
            commodity.setCommodityName(commodityName);
            commodity.setDescription(description);
            commodity.setPrice(Double.parseDouble(price));
            commodity.setImagePath(fileName);
            commodityMapper.updateCommodity(commodity);
            Commodity newCommodity = commodityMapper.queryCommodityById(commodityId);
            model.addAttribute("newCommodity",newCommodity);
            return "/user/updateSuccess";
        }

    @RequestMapping("/myCommodity/delete/{id}")
    public String deleteCommodity(@PathVariable("id") Integer commodityId){
        commodityMapper.deleteCommodity(commodityId);
        return "/user/delete";
    }

    @RequestMapping("/sold")
    public String queryMySold(HttpSession session,Model model){
        List<Order> mySold = orderMapper.queryMySold((Integer) session.getAttribute("loginUserId"));
        model.addAttribute("mySold",mySold);
        return "user/sold";
    }

    @RequestMapping("/bought")
    public String queryMyBought(HttpSession session,Model model){
        List<Order> myBought = orderMapper.queryMyBought((Integer) session.getAttribute("loginUserId"));
        model.addAttribute("myBought",myBought);
        return "user/bought";
    }

}
