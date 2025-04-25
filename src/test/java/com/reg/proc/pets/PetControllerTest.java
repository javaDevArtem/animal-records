package com.reg.proc.pets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reg.proc.users.User;
import com.reg.proc.users.UserService;
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
class PetControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PetService petsService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldCreateNewUser() throws Exception {
        var user = userService.createUser(new User(
                null,
                "Pavel",
                "mail@mail.ru",
                25,
                List.of()
        ));

        var petDto = new PetDTO(null, "Vasya", user.id());
        String newPetDto = objectMapper.writeValueAsString(petDto);

        var jsonResponse = mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPetDto))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var petDtoResponse = objectMapper.readValue(jsonResponse, PetDTO.class);

        Assertions.assertEquals(petDto.name(), petDtoResponse.name());
        Assertions.assertEquals(petDto.userId(), petDtoResponse.userId());
        Assertions.assertNotNull(petDtoResponse.id());

        Assertions.assertDoesNotThrow(() -> petsService.getPet(petDtoResponse.id()));

        var userWithPet = userService.getUser(user.id());
        Assertions.assertEquals(1, userWithPet.pets().size());
        Assertions.assertEquals(petDtoResponse.id(), userWithPet.pets().get(0).id());
    }
}