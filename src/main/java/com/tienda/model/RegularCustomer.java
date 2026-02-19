package com.tienda.model;

import com.tienda.discount.DiscountStrategy;
import com.tienda.discount.RegularDiscount;

import java.math.BigDecimal;

public class RegularCustomer extends Customer {

    public RegularCustomer (String id, String name) {
        super(id, name);
    }

    @Override
    public DiscountStrategy getDiscountStrategy () {
        return new RegularDiscount();
    }

}
