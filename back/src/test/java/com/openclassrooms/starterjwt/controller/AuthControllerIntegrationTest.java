package com.openclassrooms.starterjwt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    LoginRequest loginRequest;
    String loginJson;

    SignupRequest signupRequest;
    String signupJson;

    @BeforeEach
    public void init() throws JsonProcessingException {
        objectMapper = new ObjectMapper();

        loginRequest = new LoginRequest();
        loginRequest.setEmail("emailDB@mail.com");
        loginRequest.setPassword("password");
        loginJson = objectMapper.writeValueAsString(loginRequest);

        signupRequest = new SignupRequest();
        signupRequest.setEmail("signup@mail.com");
        signupRequest.setFirstName("sign");
        signupRequest.setLastName("signLastName");
        signupRequest.setPassword("signuppassword");
        signupJson = objectMapper.writeValueAsString(signupRequest);
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticateUser_withWrongCredentials_shouldReturnUnauthorized() throws Exception {
        this.mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"emailDB@mail.com\",\"password\":\"wrong_password\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegister() throws Exception {
        ResultActions result = this.mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"email\":\"emailDB@mail.com\",\"password\":\"wrong_password\",\"firstName\":\"firstname\",\"lastName\":\"lastname\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertThat(result.andReturn().getResponse().getContentAsString()).contains("Error: Email is already taken!");
    }
}
