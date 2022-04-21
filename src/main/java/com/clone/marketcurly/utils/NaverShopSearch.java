package com.clone.marketcurly.utils;


import com.clone.marketcurly.dto.productDto.ProductDto;
import com.clone.marketcurly.dto.productDto.ProductRequestDto;
import com.clone.marketcurly.model.Product;
import com.clone.marketcurly.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class NaverShopSearch {
    int display = 12;
    //int start = 0;
    private final ProductRepository productRepository;

    //@Transactional
    public Page<Product> getProducts(ProductRequestDto requestDto){
        int page = requestDto.getPage()-1;
        requestDto.setPage(page);
        String searchWord = requestDto.getSearchWord();
        Pageable pageable = PageRequest.of(page,display);
        //Page<Product> products = productRepository.findByNameLike("%"+searchWord+"%", pageable);
        Page<Product> products = productRepository.findBySearchWord(searchWord, pageable);

        if (products.isEmpty()){
            System.out.println("결과 없음");
            String result = productsNaverApi(requestDto);
            List<ProductDto> productDtos = showproducts(result);
            List<Product> productList = new ArrayList<>();

            for(ProductDto productDto:productDtos){
                Product product =  new Product(productDto,searchWord);
                productList.add(product);
            }
            //List를 페이지로 만듦 List,pageable,size
            products =  new PageImpl<>(productList.subList(0, 12), pageable,display);

            System.out.println(products.getContent());
            productRepository.saveAll(productList);
        }
        else System.out.println("결과 있음");
        return products;
    }
    //네이버 API접근
    public String productsNaverApi(ProductRequestDto requestDto) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "ThUUXDfoPmoDSi7bPgN5");
        headers.add("X-Naver-Client-Secret", "qxLMa6ivjD");
        String body = "";

        //변경 필요
        String word = requestDto.getSearchWord();
        int start = requestDto.getPage();

        start = start * display+1;

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query="+word+"&display="+display+"&start="+start+"&sort=sim", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        //System.out.println(requestEntity);
        String response = responseEntity.getBody();
        //System.out.println("Response status: " + status);
        //System.out.println(response);

        return response;
    }

    //json 형식으로 변환
    public List<ProductDto> showproducts(String result) {
        List<ProductDto> productDtoList = new ArrayList<>();
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");
        for (int i=0;i< items.length();i++){
            JSONObject itemJson = items.getJSONObject(i);
            String brand = itemJson.getString("brand");
            String name = itemJson.getString("title");
            name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
            //Long productId = itemJson.getLong("productId");
            String imgUrl = itemJson.getString("image");
            int price = itemJson.getInt("lprice");

            ProductDto responseDto = new ProductDto(brand, name, imgUrl, price);

            productDtoList.add(responseDto);
        }

        return productDtoList;

    }
}