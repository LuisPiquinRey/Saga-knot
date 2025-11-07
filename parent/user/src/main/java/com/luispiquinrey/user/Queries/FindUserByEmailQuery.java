package com.luispiquinrey.user.Queries;

import lombok.Value;

@Value
public class FindUserByEmailQuery {
    private final String email;
}
