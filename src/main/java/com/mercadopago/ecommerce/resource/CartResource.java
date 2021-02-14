package com.mercadopago.ecommerce.resource;

import com.mercadopago.ecommerce.domain.model.AuditModel;
import com.mercadopago.ecommerce.domain.model.Cart;
import com.mercadopago.ecommerce.domain.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CartResource extends AuditModel {
    private Long Id;
    private List<Cart>cartList;
    private User user;
}
