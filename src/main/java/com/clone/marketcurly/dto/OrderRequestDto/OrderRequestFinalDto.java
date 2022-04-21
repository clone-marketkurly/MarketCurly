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

    //수정
    private Long id;
    private int quantity;
    private boolean checked;
}
