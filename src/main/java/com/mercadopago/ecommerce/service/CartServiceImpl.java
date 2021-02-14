package com.mercadopago.ecommerce.service;

import com.mercadopago.ecommerce.domain.model.Cart;
import com.mercadopago.ecommerce.domain.model.Product;
import com.mercadopago.ecommerce.domain.repository.CartRepository;
import com.mercadopago.ecommerce.domain.repository.ProductRepository;
import com.mercadopago.ecommerce.domain.service.CartService;
import com.mercadopago.ecommerce.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Cart> getAllCarts(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    @Override
    public Cart addProductsToCart(Long productId, Long cartId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "Id", productId));
        return cartRepository.findById(cartId).map(cart -> {
            if(!cart.getProductList().contains(product)){
                cart.getProductList().add(product);
                return cartRepository.save(cart);
            }
            return cart;
        }).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
    }
}
