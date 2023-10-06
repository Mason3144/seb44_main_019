package com.shellwe.back.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ShellCategory {
    P_ALL, P_DEVICE, P_FOOD, P_HEALTH, P_OFFICE, P_LIFE, P_ETC,
    T_ALL, T_TECH, T_HEALTH, T_HOME, T_ART, T_LANG, T_ETC;

    @JsonValue
    public String lowerAtResponse() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static ShellCategory upperAtRequest(String value) {
        return ShellCategory.valueOf(value.toUpperCase());
    }
}
