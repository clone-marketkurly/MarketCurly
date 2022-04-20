package com.clone.marketcurly.dto.OrderRequestDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderRequestFinalDto {

    private Long productId;
    private int quantity;
    private boolean checked;
}
