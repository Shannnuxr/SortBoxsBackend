package com.example.SortBoxs.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ResetPasswrodRequest(


        @Email(message = "Email should be valid")
        @NotNull(message = "Email shouldn't be null")
        @Length(min = 3, message = "email length should be more than 10 ")
        String email
) {
}
