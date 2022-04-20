package com.clone.marketcurly.controller;


import com.clone.marketcurly.dto.cartDto.CartRequestDto;
import com.clone.marketcurly.dto.cartDto.CartResponseDto;
import com.clone.marketcurly.security.UserDetailsImpl;
import com.clone.marketcurly.exception.service.CartService;
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
