package com.clone.marketcurly.controller;

import com.clone.marketcurly.dto.productDto.ProductRequestDto;
import com.clone.marketcurly.dto.productDto.ProductResponseDto;
import com.clone.marketcurly.model.Product;
import com.clone.marketcurly.exception.service.ProductService;
import com.clone.marketcurly.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final NaverShopSearch naverShopSearch;
    private final ProductService productService;

    //메인 화면 및 검색
    @GetMapping("/api/board")
    public Page<Product> getProduct(@RequestParam(defaultValue="식자재") String searchWord, @RequestParam(defaultValue="1")int page) {
        ProductRequestDto requestDto = new ProductRequestDto();
        //form data로 불러오기
        requestDto.setPage(page);
        requestDto.setSearchWord(searchWord.trim());

        return naverShopSearch.getProducts(requestDto);
    }


    //추천
    @GetMapping("/api/board/recommend")
    public List<Product> getProduct() {
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setPage(1);
        requestDto.setSearchWord("음료");

        Page<Product> recommends =naverShopSearch.getProducts(requestDto);
        List<Product> productList = new ArrayList<>();
        for(Product recommend : recommends){
            productList.add(recommend);
        }
        return productList;
    }

    //상세 페이지
    @GetMapping("/api/detail/{postId}")
    public ProductResponseDto getDetail(@PathVariable Long postId){
        return productService.getProduct(postId);
    }
}