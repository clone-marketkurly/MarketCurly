package com.clone.marketcurly.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Cart extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Orders")
//    private Order order;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int sum;

    @Column(nullable = false)
    private boolean checked;

    public Cart(User user, Long productId, int quantity, int sum) {
        this.user = user;
        this.productId = productId;
        this.quantity = quantity;
        this.sum = sum;
        this.checked=true;
    }

}
