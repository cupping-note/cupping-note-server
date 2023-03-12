package com.penguin.cuppingnote.coffeebean.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Flavor {
    AROMA("aroma"),
    ACIDITY("acidity"),
    SWEETNESS("sweetness"),
    BITTERNESS("bitterness"),
    BODY("body");

    @JsonValue
    private final String typeText;

    Flavor(String typeText) {
        this.typeText = typeText;
    }
}
