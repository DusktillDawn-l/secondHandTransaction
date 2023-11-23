package com.leon.mapper;

import com.leon.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommodityMapper {
    List<Commodity> queryAllCommodityList();
    List<Commodity> queryCommodityList(int userId);
    Commodity queryCommodityById(int commodityId);
    List<Commodity> queryCommodityByName(String commodityName);
    int addCommodity(Commodity commodity);
    int deleteCommodity(int commodityId);
    int updateCommodity(Commodity commodity);
}
