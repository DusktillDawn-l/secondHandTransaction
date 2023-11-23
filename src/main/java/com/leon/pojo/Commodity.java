package com.leon.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {
    public Integer commodityId;
    public Integer sellerId;
    public String sellerName;
    public String commodityName;
    public String description;
    public double price;
    public String imagePath;
}
