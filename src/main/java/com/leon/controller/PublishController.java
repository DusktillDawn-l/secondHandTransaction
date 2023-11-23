package com.leon.controller;

import com.leon.mapper.CommodityMapper;
import com.leon.pojo.Commodity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class PublishController {
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    HttpSession httpSession;

    //跳转到
    @RequestMapping("/publish")
    public String publishItem (){
        String loginUser = (String) httpSession.getAttribute("loginUser");
    return "publish";
    }

    //上传闲置
    @PostMapping("/upload")
    public String upload(
            @RequestParam("commodityName") String commodityName,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            MultipartFile file, HttpSession session, Model model){
        String sellerName = (String) session.getAttribute("loginUser");
        int sellerId = (int) session.getAttribute("loginUserId");
        //图片上传
        if(file.isEmpty()){
            return "图片上传失败";
        }
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
        commodity.setSellerId(sellerId);
        commodity.setSellerName(sellerName);
        commodity.setCommodityName(commodityName);
        commodity.setDescription(description);
        commodity.setPrice(Double.parseDouble(price));
        commodity.setImagePath(fileName);
        commodityMapper.addCommodity(commodity);
        //将当前上传的闲置物品回显到success
        model.addAttribute("newCommodity",commodity);
        return "success";
    }
}
