package com.clone.marketcurly.repository;

import com.clone.marketcurly.model.Cart;
import com.clone.marketcurly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(Long userId);

    List<Cart> findAllByUserIdAndProductId(Long id, Long productId);

    Cart findByUserIdAndProductId(Long id, Long productId);

    void deleteCartsByUser(User user);
}
