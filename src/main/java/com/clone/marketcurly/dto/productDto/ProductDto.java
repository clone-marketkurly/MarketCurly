package com.clone.marketcurly.dto.productDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
//게시글조회 검색에서 사용 디비에 저장할 때 필요
public class ProductDto {
    private String brand;
    private String name;
    private String imgUrl;
    private int price;


    public ProductDto(String brand, String name, String imgUrl, int price) {
    this.brand = brand;
    this.name = name;
    this.imgUrl = imgUrl;
    this.price = price;
    }


//    public ProductDto(Product recommend) {
//        this.brand= recommend.getBrand();
//        this.imgUrl= recommend.getImgUrl();
//        this.name= recommend.getName();
//        this.price= recommend.getPrice();
//    }
}
