package com.mercadopago.ecommerce.domain.service;

import com.mercadopago.ecommerce.domain.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CartService {
    Page<Cart>getAllCarts(Pageable pageable);
    Cart addProductsToCart(Long productId, Long cartId);
}
