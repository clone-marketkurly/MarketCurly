package com.clone.marketcurly.service;


import com.clone.marketcurly.dto.OrderRequestDto.OrderRequestDto;
import com.clone.marketcurly.dto.OrderRequestDto.OrderRequestFinalDto;
import com.clone.marketcurly.model.*;
import com.clone.marketcurly.repository.CartRepository;
import com.clone.marketcurly.repository.OrderCartRepository;
import com.clone.marketcurly.repository.OrderRepository;
import com.clone.marketcurly.repository.ProductRepository;
import com.clone.marketcurly.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderCartRepository orderCartRepository;

    @Transactional
    public Long saveOrder(OrderRequestDto orderRequestDto, User user){

        //한 유저의 모든 장바구니 상품을 보여줌
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());
        List<OrderCart> finalOrder = new ArrayList<>();

        //주문하기를 눌렀을 때 최종값 받아줌
        List<OrderRequestFinalDto> orderList=orderRequestDto.getOrderList();

        if (cartList.size() < 1){
            throw new IllegalArgumentException("장바구니에 상품을 등록해주세요.");
        }
       // 수정
        if (orderList.size() < 1) {
            throw new IllegalArgumentException("주문할 상품을 등록해주세요.");
        }

        for (OrderRequestFinalDto orderRequestFinalDto : orderList){

            Cart cart=cartRepository.findByUserIdAndProductId(user.getId(),orderRequestFinalDto.getId());
            if(cart ==null){
                throw new NullPointerException("장바구니에 없는 상품이 주문되었습니다.");
            }
            //checked가 true일 경우
            if(orderRequestFinalDto.isChecked()) {
                Optional<Product> product = productRepository.findById(orderRequestFinalDto.getId());

                int quantity = orderRequestFinalDto.getQuantity();
                String name = product.get().getName();
//           int quantity = cart.getQuantity();//이부분이 새로들어오는 quantity랑 바껴야해
                String brand = product.get().getBrand();

                if (name == null) {
                    throw new IllegalArgumentException("제품이 존재하지 않습니다.");
                }
                if (quantity < 1) {
                    throw new IllegalArgumentException("구매할 상품의 갯수를 정해주세요");
                }
                if (user.getId() == null) {
                    throw new IllegalArgumentException("로그인 후 이용해 주세요");
                }

                OrderCart orderCart = new OrderCart(brand, name, quantity);
                finalOrder.add(orderCart);
                orderCartRepository.save(orderCart);
            }
            //checked가 false일 경우
            else{
                System.out.println(orderRequestFinalDto.isChecked());
                int quantity=orderRequestFinalDto.getQuantity();
                boolean check=orderRequestFinalDto.isChecked();
                for(Cart carts:cartList){
                    if(carts.getProductId()==orderRequestFinalDto.getId()){
                        carts.setChecked(check);
                        carts.setQuantity(quantity);
                        cartRepository.save(carts);
                    }
                }
            }
        }
        if(finalOrder.size()==0){
            throw new IllegalArgumentException("구매할 상품을 장바구니에서 선택해주세요");
        }


        Order order = new Order(user,orderRequestDto.getAddress(),finalOrder,orderRequestDto.getTotalPrice());
        orderRepository.save(order);

        for (OrderRequestFinalDto orderRequestFinalDto : orderList) {
            if(orderRequestFinalDto.isChecked()){
            Long id = orderRequestFinalDto.getId();
            cartRepository.deleteByProductIdAndUser(id, user);
            }
        }

//                for (Cart checkCart : cartList){
//            if (checkCart.getProductId() != orderRequestFinalDto.getId()) {
//                throw new IllegalArgumentException("잘못 된 구매요청입니다.");
//            }
//        }

//        for (OrderRequestFinalDto orderRequestFinalDto : orderList) {
//            Long id = orderRequestFinalDto.getId();
//            if(orderRequestFinalDto.isChecked()){
//                cartRepository.deleteByProductIdAndUser(id, user);
//            }
//            else{
//                Optional<Cart> foundCart = cartRepository.findById(id);
//                Cart cart = foundCart.get();
//                cart.setQuantity(orderRequestFinalDto.getQuantity());
//                cart.setChecked(false);
//                cartRepository.save(cart);
//            }
//        }
        return order.getId();
    }
}
