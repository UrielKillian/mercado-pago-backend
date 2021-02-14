package com.mercadopago.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercadopago.core.annotations.validation.Size;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Product extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String productName;

    @NotNull
    @Size(max = 250)
    private String description;

    @JsonIgnore
    private Integer stock;
}
