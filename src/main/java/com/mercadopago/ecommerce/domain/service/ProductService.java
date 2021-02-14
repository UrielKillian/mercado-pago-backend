package com.mercadopago.ecommerce.domain.service;

import com.mercadopago.ecommerce.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface ProductService  {
    Page<Product>getAllProduct(Pageable pageable);
    ResponseEntity<?>deleteProduct(Long productId);
    Product updateProduct(Long productId, Product productRequest);
    Product createProduct( Product product);
    Product getProductById(Long productId);
    Product getProductByTitle(String title);

}
