package com.clone.marketcurly.controller;


import com.clone.marketcurly.dto.cartDto.CartFromNonToLoginUserRequestDto;
import com.clone.marketcurly.dto.cartDto.CartRequestDto;
import com.clone.marketcurly.dto.cartDto.CartResponseDto;
import com.clone.marketcurly.model.User;
import com.clone.marketcurly.security.UserDetailsImpl;
import com.clone.marketcurly.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class CartController {
    private final CartService cartService;

    //장바구니 담기 POST
    @PostMapping("/api/mybucket/{productId}")
    public void saveCart(@PathVariable Long productId,
                         @RequestBody CartRequestDto cartRequestDto,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        cartService.saveCart(productId, cartRequestDto, userDetails);
    }

    /*비회원이 담아놓은 장바구니를 로그인한 회원 장바구니로 보내기*/
    @PostMapping("/api/mybucket/logincart")
    public void saveCartToLoginUser(
            @RequestBody List<CartFromNonToLoginUserRequestDto> cartListDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
             System.out.println("비회원 장바구니가 로그인 시 들어온다.");

            cartService.saveCartToLoginUser(cartListDto, userDetails);
    }


    //장바구니 조회
    @GetMapping("/api/mybucket")
    public List<CartResponseDto> showCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.showCart(userDetails);
    }

    //장바구니 삭제
    @DeleteMapping("/api/mybucket/{productId}")
    public void deleteCart(@PathVariable Long productId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cartService.deleteCart(productId, userDetails);
    }

}
