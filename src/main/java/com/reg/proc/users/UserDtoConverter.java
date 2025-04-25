package com.reg.proc.users;

import com.reg.proc.pets.PetDtoConverter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    private final PetDtoConverter petDtoConverter;

    public UserDtoConverter(PetDtoConverter petDtoConverter) {
        this.petDtoConverter = petDtoConverter;
    }

    public User toUser(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                userDto.age(),
                userDto.pets().stream().map(petDtoConverter::toPet).toList()

        );
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.id(),
                user.name(),
                user.email(),
                user.age(),
                user.pets().stream().map(petDtoConverter::toDto).toList()

        );
    }
}
