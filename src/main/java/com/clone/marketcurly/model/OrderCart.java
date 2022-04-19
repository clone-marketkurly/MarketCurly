package com.clone.marketcurly.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class OrderCart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //이건 없어도 될 듯...?if
//    @ManyToOne
//    private Order order;

    @Column
    private String brand;

    @Column
    private String name;

    @Column
    private int quantity;

    public OrderCart(String brand, String name, int quantity) {
        this.brand=brand;
        this.name=name;
        this.quantity=quantity;
    }
}
