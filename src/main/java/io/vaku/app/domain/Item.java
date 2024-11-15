package io.vaku.app.domain;

import java.math.BigDecimal;

public record Item(Long id, String title, BigDecimal price) {

}
