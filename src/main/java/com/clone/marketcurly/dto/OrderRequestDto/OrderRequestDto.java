package com.clone.marketcurly.dto.OrderRequestDto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OrderRequestDto {
    //id와 최종수량들어온다.
    private List<OrderRequestFinalDto> orderList=new ArrayList<>();
    private String address;
    private int totalPrice;
}
//