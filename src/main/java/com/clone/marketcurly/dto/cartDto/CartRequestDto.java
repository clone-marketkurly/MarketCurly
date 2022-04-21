package com.clone.marketcurly.dto.cartDto;


import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CartRequestDto {

    private int quantity;
    private int sum;

    public CartRequestDto (int quantity, int sum) {
        this.quantity = quantity;
        this.sum = sum;
    }

}
