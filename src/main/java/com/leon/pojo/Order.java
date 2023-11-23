package com.leon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class Order {
    public Integer orderId;
    public Integer sellerId;
    public String sellerName;
    public String sellerTele;
    public String sellerAddress;
    public Integer buyerId;
    public String buyerName;
    public String buyerTele;
    public String buyerAddress;
    public Integer commodityId;
    public String commodityName;
    public double price;
    public Date transactionTime;
    
    public Order(Integer orderId, Integer sellerId, String sellerName, String sellerTele, String sellerAddress, Integer buyerId, String buyerName, String buyerTele, String buyerAddress, Integer commodityId, String commodityName, double price, Date transactionTime) {
        this.orderId = orderId;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerTele = sellerTele;
        this.sellerAddress = sellerAddress;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerTele = buyerTele;
        this.buyerAddress = buyerAddress;
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.price = price;
        this.transactionTime = new Date();
    }
}
