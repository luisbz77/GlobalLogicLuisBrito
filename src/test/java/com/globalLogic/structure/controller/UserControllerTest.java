package com.globalLogic.structure.controller;

import com.globalLogic.domain.model.phone.Phone;
import com.globalLogic.domain.model.user.User;
import com.globalLogic.domain.usescase.user.UserCase;
import com.globalLogic.structure.response.CreateUserRequest;
import com.globalLogic.structure.response.PhoneRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserCase userCase;

    private Gson gson = new GsonBuilder().create();

    @Test
    public void signUpTest() throws Exception {
        Phone phone = Phone
                .builder()
                .number("1234567890")
                .cityCode("123")
                .countryCode("+56")
                .build();
        User user = User
                .builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("securePassword123")
                .phones(Collections.singletonList(phone))
                .build();
        User createdUser = User.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .password(null)
                .phones(Collections.singletonList(phone))
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .modified(Timestamp.valueOf(LocalDateTime.now()))
                .lastLogin(Timestamp.valueOf(LocalDateTime.now()))
                .token("someGeneratedToken")
                .isActive(true)
                .build();

        when(userCase.createUser(user)).thenReturn(createdUser);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("Usuario Global Logic");
        createUserRequest.setEmail("usuarioglobal@globallogic.com");
        createUserRequest.setPassword("Abc1defg");
        createUserRequest.setPhones(Collections.singletonList(new PhoneRequest(123456789, 1, "56")));

        String requestBody = gson.toJson(createUserRequest);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "\"id\": \"e5c6cf84-8860-4c00-91cd-22d3be28904e\",\n" +
                        "\"created\": \"Nov 16, 2021 12:51:43 PM\",\n" +
                        "\"lastLogin\": \"Nov 16, 2021 12:51:43 PM\",\n" +
                        "\"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpb0B0ZXN0...\",\n" +
                        "\"isActive\": true,\n" +
                        "\"name\": \"Julio Gonzalez\",\n" +
                        "\"email\": \"julio@testssw.cl\",\n" +
                        "\"password\": \"a2asfGfdfdf4\",\n" +
                        "\"phones\": [\n" +
                        "{\n" +
                        "\"number\": 87650009,\n" +
                        "\"citycode\": 7,\n" +
                        "\"contrycode\": \"25\"\n" +
                        "}\n" +
                        "]\n" +
                        "}\n"));
    }
}