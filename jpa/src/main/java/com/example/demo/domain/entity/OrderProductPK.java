package com.example.demo.domain.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrderProductPK implements Serializable {
    private int orderId;
    private int productId;
}
