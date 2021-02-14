package com.mercadopago.ecommerce.resource;

import com.mercadopago.ecommerce.domain.model.AuditModel;
import lombok.Data;

@Data
public class SaveUserResource extends AuditModel {
    private String username;
    private String password;
    private String email;
    private String telephone;
}
