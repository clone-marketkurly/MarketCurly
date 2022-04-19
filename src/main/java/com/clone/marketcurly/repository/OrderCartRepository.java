package com.clone.marketcurly.repository;


import com.clone.marketcurly.model.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {

}
