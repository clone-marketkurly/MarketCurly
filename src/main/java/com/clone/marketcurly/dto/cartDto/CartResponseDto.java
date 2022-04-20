package com.clone.marketcurly.dto.cartDto;

import com.clone.marketcurly.dto.productDto.ProductResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {

    private Long id;
    private String brand;
    private boolean checked;
    private String imgUrl;
    private String name;
    private int sum;
    private int price;
    private int quantity;

    public CartResponseDto(Long productId,ProductResponseDto productResponseDto, int quantity , int sum) {
        this.id=productId;
        this.brand = productResponseDto.getBrand();
        this.checked= true;
        this.imgUrl = productResponseDto.getImgUrl();
        this.name = productResponseDto.getName();
        this.sum = sum;
        this.price = productResponseDto.getPrice();
        this.quantity = quantity;
    }
    public void changeCheck(boolean check){
        this.checked=check;
    }
}
