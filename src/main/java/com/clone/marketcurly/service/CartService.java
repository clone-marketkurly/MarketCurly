package com.clone.marketcurly.service;


import com.clone.marketcurly.dto.cartDto.CartResponseDto;
import com.clone.marketcurly.dto.productDto.ProductResponseDto;
import com.clone.marketcurly.model.Cart;
import com.clone.marketcurly.dto.cartDto.CartRequestDto;
import com.clone.marketcurly.model.Product;
import com.clone.marketcurly.model.User;
import com.clone.marketcurly.repository.CartRepository;
import com.clone.marketcurly.repository.ProductRepository;
import com.clone.marketcurly.repository.UserRepository;
import com.clone.marketcurly.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;


    // 장바구니 담기
    @Transactional
    public void saveCart(Long productId, CartRequestDto cartRequestDto, UserDetailsImpl userDetails) {

        // 한개 장바구니 갯수(수량)
        int quantity = cartRequestDto.getQuantity();
        System.out.println(quantity);

        // 한개 장바구니 상품의 종합가격
        int sum = cartRequestDto.getSum();
        System.out.println(sum);

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("제품이 존재하지 않습니다.")
        );

        // 장바구니 제품 갯수 유효성검사
        if (quantity == 0) {
            throw new IllegalArgumentException("장바구니에 담을 갯수를 정해주세요");
        }

        List<Cart> cartCheck = cartRepository.findAllByUserIdAndProductId(userDetails.getUser().getId(),productId);
        System.out.println(cartCheck);

        // 유저에 대한 유효성검사
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
        System.out.println(user.getUsername());


        if (cartCheck.size() > 0) {
            //이미 동일한 제품을 담은 경우 수량과 가격이 더해짐
            for(Cart carts : cartCheck) {
                carts.setQuantity(carts.getQuantity() + quantity);
                carts.setSum(carts.getSum() + sum);
                cartRepository.save(carts);
            }
        } else {
            //처음담는 상품일 경우 생성
            Cart cart = new Cart(user , productId, quantity, sum);
//            cart.setUser(user);
//            cart.setProductId(productId);
//            cart.setQuantity(quantity);
            //  cart.setSum(sum);
            cartRepository.save(cart);
        }
    }

    public List<CartResponseDto> showCart(UserDetailsImpl userDetails) {
        // cartResponsDto에 brand, imgUrl, name, price, quntity를 보내줌
        List<CartResponseDto> cartResponseDtolist = new ArrayList<>();

//        // 로그인한 유저의 1개 장바구니 찾기
//        Cart cart = userDetails.getUser().getCart();

        // 장바구니 전체를 불러와 리스트에 저장한다.
        List<Cart> cartList = cartRepository.findAllByUserId(userDetails.getUser().getId());

        //for문을 돌면서 각 정보를 추거해
        for (Cart eachCart : cartList) {

            // productId를 이용해서 제품을 찾기
            Long productId = eachCart.getProductId();
            Product product = productRepository.findById((productId))
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
            ProductResponseDto productResponseDto = new ProductResponseDto(product);

            // 수량 찾기
            int quantity = eachCart.getQuantity();
            //총가격
            int sum = eachCart.getSum();
            // CartResponseDto에 넣기
            CartResponseDto cartResponseDto = new CartResponseDto(productId,productResponseDto, quantity, sum);

            // 반환할 리스트에 하나씩 넣어준다.
            cartResponseDtolist.add(cartResponseDto);
        }
        return cartResponseDtolist;
    }

    // 장바구니 삭제
    @Transactional
    public void deleteCart(Long productId, UserDetailsImpl userDetails) {
        Cart cart = cartRepository.findByUserIdAndProductId(userDetails.getUser().getId(), productId);
        if (cart == null){
            throw new IllegalArgumentException("장바구니에서 상품을 찾을 수 없습니다.");
        }
        // 장바구니 삭제
        cartRepository.delete(cart);
    }


}


