package com.clone.marketcurly.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Order extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OrderCart")
    private List<OrderCart> orderCartList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String address;

    @Column
    private int totalPrice;

    public Order(User user, String address, List<OrderCart> finalOrder, int totalPrice) {
        this.user = user;
        this.address = address;
        this.orderCartList = finalOrder;
        this.totalPrice = totalPrice;
    }
}