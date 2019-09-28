package com.example.copsboot.restservices;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateOfficerParameters {

    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 6, max = 1000)
    private String password;
}
