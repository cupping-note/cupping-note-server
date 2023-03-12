package com.penguin.cuppingnote.pref.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum PrefResultType {
    BALANCE("balance"),
    AROMA("aroma"),
    ACIDITY("acidity"),
    SWEETNESS("sweetness"),
    BITTERNESS("bitterness"),
    BODY("body");

    @JsonValue
    private final String typeText;

    PrefResultType(String typeText) {
        this.typeText = typeText;
    }
}
