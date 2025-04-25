package com.reg.proc.pets;

import org.springframework.stereotype.Component;

@Component
public class PetDtoConverter {

    public Pet toPet(PetDTO petDTO) {
        return new Pet(
                petDTO.id(),
                petDTO.name(),
                petDTO.userId()

        );
    }

    public PetDTO toDto(Pet pet) {
        return new PetDTO(
                pet.id(),
                pet.name(),
                pet.userId()
        );
    }
}
