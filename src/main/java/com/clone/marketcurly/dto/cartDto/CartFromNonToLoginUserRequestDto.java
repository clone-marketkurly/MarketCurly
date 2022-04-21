package com.clone.marketcurly.dto.cartDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartFromNonToLoginUserRequestDto {

    //productId를 의미
    private Long id;
    private int quantity;
    private int sum;
}
