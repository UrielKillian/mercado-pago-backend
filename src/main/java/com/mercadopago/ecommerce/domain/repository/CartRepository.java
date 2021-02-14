package com.mercadopago.ecommerce.domain.repository;

import com.mercadopago.ecommerce.domain.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Page<Cart> findAll(Pageable pageable);
    Optional<Cart>findByUserId(Cart cart);

}
