package com.mercadopago.ecommerce.resource;

import lombok.Data;

@Data
public class SaveProductResource {
    private String productName;
    private String description;
    private String stock;
}
