package com.mercadopago.ecommerce.controller;

import com.mercadopago.ecommerce.domain.model.Product;
import com.mercadopago.ecommerce.domain.service.ProductService;
import com.mercadopago.ecommerce.resource.ProductResource;
import com.mercadopago.ecommerce.resource.SaveProductResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name="product", description = "product API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ProductController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public Page<ProductResource> getAllProducts(Pageable pageable){
        Page<Product> productPage = productService.getAllProduct(pageable);
        List<ProductResource> resourceList = productPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<ProductResource>(resourceList, pageable, resourceList.size());
    }

    @PostMapping("/product")
    public ProductResource createProduct(@Valid @RequestBody SaveProductResource resource){
        Product product = convertToEntity(resource);
        return convertToResource(productService.createProduct(product));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?>deleteProduct(@PathVariable(name = "id") Long productId){
        return productService.deleteProduct(productId);
    }

    @PutMapping("/product/{id}")
    public ProductResource updateProduct(@PathVariable(name = "id") Long productId, @Valid @RequestBody SaveProductResource resource){
        Product product = convertToEntity(resource);
        return convertToResource(productService.updateProduct(productId, product));
    }


    private Product convertToEntity(SaveProductResource resource){
        return mapper.map(resource, Product.class);
    }

    private ProductResource convertToResource(Product entity){
        return mapper.map(entity, ProductResource.class);
    }

}
