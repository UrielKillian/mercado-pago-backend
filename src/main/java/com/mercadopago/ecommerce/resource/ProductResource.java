package com.mercadopago.ecommerce.resource;

import com.mercadopago.ecommerce.domain.model.AuditModel;
import lombok.Data;

@Data
public class ProductResource  extends AuditModel {
    private Long id;
    private String productName;
    private String description;
    private String stock;
}
