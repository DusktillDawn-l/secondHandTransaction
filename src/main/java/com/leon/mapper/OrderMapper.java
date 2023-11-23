package com.leon.mapper;

import com.leon.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderMapper {
    List<Order> queryOrderList();

    List<Order> queryOrderByCommodityName(String commodityName);
    List<Order> queryMyBought(int buyerId);
    List<Order> queryMySold(int sellerId);
    int newOrder(Order order);
    int deleteOrder(int orderId);

}
