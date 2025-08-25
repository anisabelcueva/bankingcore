package com.banking.core.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccountStatus {

    ACTIVE_ACCOUNT(1, "Active"),
    INACTIVE_ACCOUNT(2, "Inactive"),
    NONE(0, "None");;

    private final int id;
    private final String name;

    public static AccountStatus getById(int id) {
        return Arrays.stream(values())
                .filter( accountStatus -> id == accountStatus.getId())
                .findAny()
                .orElse(NONE);
    }

}
