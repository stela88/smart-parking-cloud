package com.unipu.smart_parksystem.dto;

import java.math.BigDecimal;


public class PriceDto {
    private BigDecimal price;

    public PriceDto(BigDecimal price) {
        this.price = price;
    }

    public PriceDto() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
