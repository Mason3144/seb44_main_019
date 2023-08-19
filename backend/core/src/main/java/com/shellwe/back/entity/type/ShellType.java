package com.shellwe.back.entity.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ShellType {
    PRODUCT, TALENT;

    @JsonValue
    public String lowerAtResponse() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static ShellType upperAtRequest(String value) {
        return ShellType.valueOf(value.toUpperCase());
    }
}
