package com.reg.proc.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void shouldCreateNewUser() throws Exception {
        var userDto = new UserDto(null, "Pavel", "test@example.com", 25, List.of());
        String newUserJson = objectMapper.writeValueAsString(userDto);

        var jsonResponse = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var userDtoResponse = objectMapper.readValue(jsonResponse, UserDto.class);

        Assertions.assertEquals(userDto.name(), userDtoResponse.name());
        Assertions.assertEquals(userDto.age(), userDtoResponse.age());
        Assertions.assertEquals(userDto.email(), userDtoResponse.email());
        Assertions.assertEquals(userDto.pets(), userDtoResponse.pets());

        Assertions.assertDoesNotThrow(() -> userService.getUser(userDtoResponse.id()));
    }
}