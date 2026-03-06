package com.tienda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateCustomerRequest {

    @NotBlank (message = "Name cannot be blank")
    private String name;

    @NotBlank (message = "Type cannot be blank")
    @Pattern(regexp = "PREMIUM|REGULAR", message = "Type must be PREMIUM or REGULAR")
    private String type;

    public CreateCustomerRequest () {}

    public String getName () {
        return name;
    }

    public String getType () {
        return type;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setType (String type) {
        this.type = type;
    }
}
