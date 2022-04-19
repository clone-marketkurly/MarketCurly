package com.clone.marketcurly.exception.service;

import com.clone.marketcurly.dto.productDto.ProductResponseDto;
import com.clone.marketcurly.model.Product;
import com.clone.marketcurly.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Transactional
    public ProductResponseDto getProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new IllegalArgumentException("제품이 존재하지 않습니다.")
        );
        ProductResponseDto responseDto = new ProductResponseDto(product);
        responseDto.setInfo("맛있고 신선한 제품입니다. 다양한 조리법으로 맛있는 식사를 즐기실 수 있습니다.");

        return responseDto;
    }


}
