package com.reg.proc.pets;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final PetDtoConverter petDtoConverter;


    public PetController(PetService petService, PetDtoConverter petDtoConverter) {
        this.petService = petService;
        this.petDtoConverter = petDtoConverter;
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(@RequestBody @Validated PetDTO petDTO) {
        Pet pet = petService.cretePet(petDtoConverter.toPet(petDTO));
        return ResponseEntity.ok(petDtoConverter.toDto(pet));
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable() Long petId) {
        var pet = petService.getPet(petId);
        return ResponseEntity.ok(petDtoConverter.toDto(pet));

    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable() Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetDTO> updatePet(@RequestBody @Validated PetDTO petDTO, @PathVariable Long petId) {
        var petToUpdate = new Pet(
                petId,
                petDTO.name(),
                petDTO.userId()
        );
        var updatedPet = petService.updatePet(petToUpdate);
        return ResponseEntity.ok(petDtoConverter.toDto(updatedPet));
    }

}
