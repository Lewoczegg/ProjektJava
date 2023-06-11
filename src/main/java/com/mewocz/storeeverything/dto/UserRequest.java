package com.mewocz.storeeverything.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest
{
    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "^[A-Z][A-Za-z]{2,19}", message = "First letter has to be uppercase, length 3-20 letters ")
    private String firstName;
    @NotEmpty(message = "Surname should not be empty")
    @Pattern(regexp = "^[A-Z][A-Za-z]{2,49}", message = "First letter has to be uppercase, length 3-50 letters ")
    private String lastName;
    @NotEmpty(message = "Login should not be empty")
    @Pattern(regexp = "^[a-z]{3,20}", message = "Only lowercase letters of length 3-20 allowed")
    private String login;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 5, message = "Has to be at least 5 characters/digit long")
    private String password;

    @Min(value = 18, message = "Minimal age is 18")
    private int age;
}