package com.openclassrooms.starterjwt.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testFindById_shouldReturn_userCorresponding() throws Exception {
        this.mockMvc.perform(get("/api/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("userDBLastName"))
                .andExpect(jsonPath("$.firstName").value("userDBFirstName"))
                .andExpect(jsonPath("$.admin").value("true"))
                .andExpect(jsonPath("$.email").value("emailDB@mail.com"))
                .andExpect(jsonPath("$.createdAt").value("2024-08-17T17:33:46.105399"))
                .andExpect(jsonPath("$.updatedAt").value("2025-01-17T17:33:46.105399"));
    }

    @Test
    @WithMockUser
    public void testFindById_shouldThrow_responseEntityNotFound() throws Exception {
        this.mockMvc.perform(get("/api/user/3"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testFindById_shouldThrow_responseBadRequest() throws Exception {
        this.mockMvc.perform(get("/api/user/text"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "emailDB@mail.com")
    public void testDelete_shouldDelete_correspondingUser() throws Exception {
        this.mockMvc.perform(delete("/api/user/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "emailDB@mail.com")
    public void testDelete_whenUserNotCorresponding_shouldUnauthorized() throws Exception {
        this.mockMvc.perform(delete("/api/user/2"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "emailDB@mail.com")
    public void testDelete_whenUserDoesNotExist_shouldNotFound() throws Exception {
        this.mockMvc.perform(delete("/api/user/8"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "emailDB@mail.com")
    public void testDelete_whenUsingString_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(delete("/api/user/text"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
