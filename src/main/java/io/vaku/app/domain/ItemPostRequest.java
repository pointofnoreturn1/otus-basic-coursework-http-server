package io.vaku.app.domain;

import java.math.BigDecimal;

public class ItemPostRequest {
    private String title;
    private BigDecimal price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ItemPostRequest(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }
}
