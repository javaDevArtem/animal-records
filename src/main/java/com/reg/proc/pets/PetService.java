package com.reg.proc.pets;

import com.reg.proc.users.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PetService {

    private final UserService userService;
    private final AtomicLong idCounter;

    public PetService(UserService userService) {
        this.userService = userService;
        this.idCounter = new AtomicLong();
    }

    public Pet cretePet(Pet pet) {
        if (pet.id() != null) {
            throw new IllegalArgumentException("Id for pet should not be provided");
        }
        var petToSave = new Pet(idCounter.incrementAndGet(), pet.name(), pet.userId());
        userService.getUser(pet.userId())
                .pets().add(petToSave);
        return petToSave;
    }

    public void deletePet(Long id) {
        Pet pet = findPetById(id)
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(id)));
        var user = userService.getUser(pet.id());
        user.pets().remove(pet);

    }

    public Pet updatePet(Pet pet) {
        if (pet.id() == null) {
            throw new IllegalArgumentException("No pet id passed");
        }
        Pet foundPet = findPetById(pet.id())
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(pet.id())));
        var updatedPet = new Pet(foundPet.id(), pet.name(), foundPet.userId());
        var user = userService.getUser(pet.id());
        user.pets().remove(foundPet);
        user.pets().add(updatedPet);
        return updatedPet;
    }

    public Pet getPet(Long id) {
        return findPetById(id)
                .orElseThrow(() -> new NoSuchElementException("No such pet with id=%s".formatted(id)));
    }


    public Optional<Pet> findPetById(Long id) {
        return userService.getAllUsers().stream()
                .flatMap(u -> u.pets().stream())
                .filter(p -> p.id().equals(id))
                .findAny();
    }

}
