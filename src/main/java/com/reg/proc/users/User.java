package com.reg.proc.users;

import com.reg.proc.pets.PetDTO;

import java.util.List;

public record User(
        Long id,
        String name,
        String email,
        Integer age,
        List<PetDTO> pets
) {
}
