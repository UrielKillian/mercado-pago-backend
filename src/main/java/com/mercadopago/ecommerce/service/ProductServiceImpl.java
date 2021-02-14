package com.mercadopago.ecommerce.service;

import com.mercadopago.ecommerce.domain.model.Product;
import com.mercadopago.ecommerce.domain.repository.ProductRepository;
import com.mercadopago.ecommerce.domain.service.ProductService;
import com.mercadopago.ecommerce.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Id", productId));
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

    @Override
    public Product updateProduct(Long productId, Product productRequest) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "Id", productId));
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setStock(productRequest.getStock());
        return productRepository.save(product);
    }

    @Override
    public Product createProduct( Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "Id", productId));
        return product;
    }

    @Override
    public Product getProductByTitle(String title) {
        return productRepository.findByProductName(title).orElseThrow(()->new ResourceNotFoundException("Product", "Id", title));
    }
}
