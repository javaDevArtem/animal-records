package com.reg.proc.users;

import com.reg.proc.pets.PetDtoConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final PetDtoConverter petDtoConverter;


    public UserController(UserService userService,
                          UserDtoConverter userDtoConverter,
                          PetDtoConverter petDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.petDtoConverter = petDtoConverter;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Validated UserDto userDto) {
        User user = userService.createUser(userDtoConverter.toUser(userDto));
        return ResponseEntity.ok(userDtoConverter.toDto(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable() Long userId) {
        var user = userService.getUser(userId);
        return ResponseEntity.ok(userDtoConverter.toDto(user));

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable() Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Validated UserDto userDto, @PathVariable Long userId) {
        var userToUpdate = new User(
                userId,
                userDto.name(),
                userDto.email(),
                userDto.age(),
                userDto.pets().stream().map(petDtoConverter::toPet).toList()

        );
        var updatedUser = userService.updateUser(userToUpdate);
        return ResponseEntity.ok(userDtoConverter.toDto(updatedUser));
    }

}
