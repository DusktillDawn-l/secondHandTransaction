package com.leon.pojo;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public Integer userId;
    public String password;
    public String userName;
    public String phoneNumber;
    public String address;
}
