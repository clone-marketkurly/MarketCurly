package com.clone.marketcurly.repository;

import com.clone.marketcurly.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    // Page<Product> findByNameLike(String searchWord, Pageable pageable);
    Page<Product> findBySearchWord(String searchWord, Pageable pageable);
    Optional<Product> findById(Long productId);
}
