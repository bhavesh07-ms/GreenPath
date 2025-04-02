package com.codeflowdb.payloads;

import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
//    private Set<Role> roles;
//    private Set<Permission> permissions;
}
