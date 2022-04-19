package com.clone.marketcurly.dto.cartDto;

import com.clone.marketcurly.dto.productDto.ProductResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {

    private Long productId;
    private String brand;
    private String imgUrl;
    private String name;
    private int sum;
    private int price;
    private int quantity;

    public CartResponseDto(Long productId,ProductResponseDto productResponseDto, int quantity , int sum) {
        this.productId=productId;
        this.brand = productResponseDto.getBrand();
        this.imgUrl = productResponseDto.getImgUrl();
        this.name = productResponseDto.getName();
        this.sum = sum;
        this.price = productResponseDto.getPrice();
        this.quantity = quantity;
    }
}
