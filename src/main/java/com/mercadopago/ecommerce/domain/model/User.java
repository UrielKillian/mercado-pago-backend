package com.mercadopago.ecommerce.domain.model;

import com.mercadopago.core.annotations.validation.Size;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Data
public class User extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 15)
    @NaturalId
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(max = 15, min = 5)
    private String password;

    @NotNull
    @Size(max = 25)
    private String email;

    @NotNull
    @Size(max = 25)
    private String telephone;

}
