package com.example.demo.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "order_product")
@IdClass(OrderProductPK.class)
public class OrderProduct  implements Serializable {
 
    @Id
    @Column(name = "order_id")
    private int orderId;
    
    @Id
    @Column(name = "product_id")
    private int productId;
 
//    @EmbeddedId
//    private OrderProductPK orderProductPK;
    
    private int amount;
}
