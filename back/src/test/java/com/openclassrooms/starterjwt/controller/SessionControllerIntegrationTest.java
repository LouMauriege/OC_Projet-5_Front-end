package com.openclassrooms.starterjwt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SessionControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    SessionDto sessionDto;
    String sessionJson;

    UserDto userDto;
    String userJson;

    @BeforeEach
    public void init() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Session From Dto");
        sessionDto.setDate(new Date(124, Calendar.JUNE, 4));
        sessionDto.setDescription("Session From Data Transfer Object");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Collections.singletonList(2L));
        sessionDto.setCreatedAt(LocalDateTime.of(2024, 8, 17, 17, 33, 46, 105399));
        sessionDto.setUpdatedAt(LocalDateTime.of(2025, 1, 17, 17, 33, 46, 105399));
        sessionJson = objectMapper.writeValueAsString(sessionDto);

        userDto = new UserDto();
        userDto.setId(3L);
        userDto.setEmail("emailDB@mail.com");
        userDto.setFirstName("userDBFirstName");
        userDto.setLastName("userDBLastName");
        userDto.setAdmin(true);
        userDto.setCreatedAt(LocalDateTime.of(2024, 8, 17, 17, 33, 46, 105399));
        userDto.setUpdatedAt(LocalDateTime.of(2025, 1, 17, 17, 33, 46, 105399));
        userJson = objectMapper.writeValueAsString(userDto);
    }

    @Test
    @WithMockUser
    public void testFindById_shouldReturn_sessionCorresponding() throws Exception {
        this.mockMvc.perform(get("/api/session/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Session From DB"))
                .andExpect(jsonPath("$.date").value("2024-06-03T22:00:00.000+00:00"))
                .andExpect(jsonPath("$.teacher_id").value(1))
                .andExpect(jsonPath("$.description").value("Session From Database"))
                .andExpect(jsonPath("$.users").value(2))
                .andExpect(jsonPath("$.createdAt").value("2024-08-17T17:33:46.105399"))
                .andExpect(jsonPath("$.updatedAt").value("2025-01-17T17:33:46.105399"));
    }

    @Test
    @WithMockUser
    public void testFindById_shouldThrow_responseEntityNotFound() throws Exception {
        this.mockMvc.perform(get("/api/session/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testFindById_shouldThrow_responseBadRequest() throws Exception {
        this.mockMvc.perform(get("/api/session/text"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testFindAll() throws Exception {
        ResultActions result = this.mockMvc.perform(get("/api/session"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(result.andReturn().getResponse().getContentAsString()).isEqualTo("[{\"id\":1,\"name\":\"Session From DB\",\"date\":\"2024-06-03T22:00:00.000+00:00\",\"teacher_id\":1,\"description\":\"Session From Database\",\"users\":[2],\"createdAt\":\"2024-08-17T17:33:46.105399\",\"updatedAt\":\"2025-01-17T17:33:46.105399\"}]");
    }

    @Test
    @WithMockUser
    public void testCreate() throws Exception {
        this.mockMvc.perform(post("/api/session/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Session From Dto"))
                .andExpect(jsonPath("$.date").value("2024-06-03T22:00:00.000+00:00"))
                .andExpect(jsonPath("$.teacher_id").value(1))
                .andExpect(jsonPath("$.description").value("Session From Data Transfer Object"))
                .andExpect(jsonPath("$.users").value(2))
                .andExpect(jsonPath("$.createdAt").value("2024-08-17T17:33:46.000105399"))
                .andExpect(jsonPath("$.updatedAt").value("2025-01-17T17:33:46.000105399"));
    }

    @Test
    @WithMockUser
    public void testUpdate() throws Exception {
        this.mockMvc.perform(put("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Session From Dto"))
                .andExpect(jsonPath("$.date").value("2024-06-03T22:00:00.000+00:00"))
                .andExpect(jsonPath("$.teacher_id").value(1))
                .andExpect(jsonPath("$.description").value("Session From Data Transfer Object"))
                .andExpect(jsonPath("$.users").value(2))
                .andExpect(jsonPath("$.createdAt").value("2024-08-17T17:33:46.000105399"))
                .andExpect(jsonPath("$.updatedAt").value("2025-01-17T17:33:46.000105399"));
    }

    @Test
    @WithMockUser
    public void testUpdate_shouldThrow_responseBadRequest() throws Exception {
        this.mockMvc.perform(put("/api/session/text")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testDelete() throws Exception {
        this.mockMvc.perform(delete("/api/session/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDelete_whenSessionDoesNotExist_shouldNotFound() throws Exception {
        this.mockMvc.perform(delete("/api/session/8"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testDelete_whenUsingString_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(delete("/api/session/text"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testParticipate() throws Exception {
        this.mockMvc.perform(post("/api/session/1/participate/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testParticipate_whenUsingString_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post("/api/session/text/participate/1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testUnparticipate() throws Exception {
        this.mockMvc.perform(delete("/api/session/1/participate/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testUnparticipate_whenUsingString_shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(delete("/api/session/text/participate/1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}