package com.luispiquinrey.user.Queries;

import lombok.Value;

@Value
public class FindUserByUsernameQuery {
    private final String username;
}
