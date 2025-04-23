package com.reg.proc.users;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserDtoConverter userDtoConverter;


    public UserController(UserService userService, UserDtoConverter userDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        User user = userService.creteUser(userDtoConverter.toUser(userDto));
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
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        var userToUpdate = new User(
                userId,
                userDto.name(),
                userDto.email(),
                userDto.age(),
                userDto.pets()
        );
        var updatedUser = userService.updateUser(userToUpdate);
        return ResponseEntity.ok(userDtoConverter.toDto(updatedUser));
    }

}
