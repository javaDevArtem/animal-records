package com.reg.proc.pets;

import jakarta.validation.constraints.NotBlank;
import jakarta.annotation.Nonnull;

public record PetDTO(
        Long id,
        @NotBlank
        String name,
        @Nonnull
        Long userId
) {
}
