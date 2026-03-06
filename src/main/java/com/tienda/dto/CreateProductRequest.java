package com.tienda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class CreateProductRequest {

    @NotBlank (message = "Name cannot be blank")
    private String name;

    @NotNull (message = "Price cannot be null")
    @Positive (message = "Price must be positive")
    private BigDecimal price;

    public CreateProductRequest () {}

    public String getName () {
        return name;
    }

    public BigDecimal getPrice () {
        return price;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setPrice (BigDecimal price) {
        this.price = price;
    }
}
