package com.reg.proc.users;

import com.reg.proc.pets.PetDTO;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDto(
        Long id,
        @NotBlank(message = "Name must be not empty")
        String name,
        @Email(message = "Email must be valid")
        String email,
        @Min(value = 0, message = "Age must be more than 0")
        Integer age,
        @Nonnull
        List<PetDTO> pets
) {
}