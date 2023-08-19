package com.shellwe.back.entity.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    ACTIVE, INACTIVE;

    @JsonValue
    public String lowerAtResponse() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static Status upperAtRequest(String value) {
        return Status.valueOf(value.toUpperCase());
    }
}
