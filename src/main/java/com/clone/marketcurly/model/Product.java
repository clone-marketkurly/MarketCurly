package com.clone.marketcurly.model;

import com.clone.marketcurly.dto.productDto.ProductDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String brand;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private int price;

    @Column
    private String searchWord;

    public Product(ProductDto productDto, String searchWord) {
        this.brand=productDto.getBrand();
        this.name=productDto.getName();
        this.imgUrl= productDto.getImgUrl();
        this.price= productDto.getPrice();
        this.searchWord = searchWord;
    }

    //@OneToMany(mappedBy = "product")
    // @JsonIgnore
    //private List<Review> reviews;
}