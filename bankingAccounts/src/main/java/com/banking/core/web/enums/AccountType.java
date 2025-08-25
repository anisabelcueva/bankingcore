package com.banking.core.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum AccountType {

    SAVINGS_ACCOUNT(1, "Saving Account"),
    CHECKING_ACCOUNT(2, "Checking Account"),
    NONE(0, "None");

    private final int id;
    private final String name;

    public static AccountType getById(int id) {
        return Arrays.stream(values())
                .filter( accountType -> id == accountType.getId())
                .findAny()
                .orElse(NONE);
    }
}
