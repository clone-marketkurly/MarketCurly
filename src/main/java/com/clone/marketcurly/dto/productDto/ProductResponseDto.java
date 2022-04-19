package com.clone.marketcurly.dto.productDto;

import com.clone.marketcurly.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

//상세 페이지에서 사용
public class ProductResponseDto {
    private String brand;
    private String name;
    //private Long productId;
    private String imgUrl;
    private int price;
    private String info;

    public ProductResponseDto(Product product) {
        this.brand = product.getBrand();
        this.name= product.getName();
        this.price=product.getPrice();
        this.imgUrl= product.getImgUrl();
    }
}
