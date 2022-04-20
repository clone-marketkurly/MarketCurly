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

        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());
        List<OrderCart> finalOrder = new ArrayList<>();

        if (cartList.size() < 1){
            throw new IllegalArgumentException("장바구니에 상품을 등록해주세요.");
        }

        for (Cart cart:cartList){
            Optional<Product> product = productRepository.findById(cart.getProductId());

            //주문하기 눌렀을 시 최종으로 들어오는 quantity 반영하기 위함
            int quantity = cart.getQuantity();//이부분이 새로들어오는 quantity랑 바껴야해
            List<OrderRequestFinalDto> orderList=orderRequestDto.getOrderList();
            for(OrderRequestFinalDto orders:orderList){
                //장바구니에 담긴 상품과 주문된 상품이 같다면
                if(cart.getProductId() == orders.getProductId()){
                    //최종으로 주무된 수량으로 교체해준다.
                    quantity=orders.getQuantity();
                };
            }

            String name = product.get().getName();
//            int quantity = cart.getQuantity();//이부분이 새로들어오는 quantity랑 바껴야해
            String brand = product.get().getBrand();

            if (name == null) {
                throw new IllegalArgumentException("제품이 존재하지 않습니다.");
            }
            if (quantity < 1) {
                throw new IllegalArgumentException("구매할 상품의 갯수를 정해주세요");
            }
            if (user.getId() == null){
                throw new IllegalArgumentException("로그인 후 이용해 주세요");
            }

            OrderCart orderCart = new OrderCart(brand,name,quantity);
            finalOrder.add(orderCart);
            orderCartRepository.save(orderCart);
        }
        Order order = new Order(user,orderRequestDto.getAddress(),finalOrder,orderRequestDto.getTotalPrice());
        orderRepository.save(order);

        for (Cart cart:cartList) {
            Long id = cart.getId();
            if (id < 1) {
                throw new IllegalArgumentException("장바구니를 찾을 수 없습니다.");
            }

            cartRepository.deleteById(id);
        }
        return order.getId();
    }
}
