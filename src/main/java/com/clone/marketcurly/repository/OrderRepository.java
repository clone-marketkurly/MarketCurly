package com.clone.marketcurly.repository;

import com.clone.marketcurly.model.Order;
import com.clone.marketcurly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByIdAndUser(Long orderId, User user);
//    void deleteAllByProductInCartId(Long productInCartId);
}
