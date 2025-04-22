package com.reg.proc.users;

public record UserDto(
        Long id;
        String name;
        String email;
        Integer age;
        List<PetDTO> pets;
) {
}
