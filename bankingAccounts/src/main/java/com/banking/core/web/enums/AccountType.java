package com.banking.core.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum AccountType {

    SAVINGS_ACCOUNT(1, "SAVINGS_ACCOUNT"),
    CHECKING_ACCOUNT(2, "CHECKING_ACCOUNT"),
    NONE(0, "None");

    private final int id;
    private final String name;

    public static AccountType getByName(String name) {
        return Arrays.stream(values())
                .filter( accountType -> Objects.equals(name, accountType.getName()))
                .findAny()
                .orElse(NONE);
    }
}
