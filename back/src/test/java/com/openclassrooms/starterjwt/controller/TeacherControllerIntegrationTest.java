package com.openclassrooms.starterjwt.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TeacherControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testFindById_shouldReturn_teacherCorresponding() throws Exception {
        this.mockMvc.perform(get("/api/teacher/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("teacherDBLastName"))
                .andExpect(jsonPath("$.firstName").value("teacherDBFirstName"))
                .andExpect(jsonPath("$.createdAt").value("2024-08-17T17:33:46.105399"))
                .andExpect(jsonPath("$.updatedAt").value("2025-01-17T17:33:46.105399"));
    }

    @Test
    @WithMockUser
    public void testFindById_shouldThrow_responseEntityNotFound() throws Exception {
        this.mockMvc.perform(get("/api/teacher/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testFindById_shouldThrow_responseBadRequest() throws Exception {
        this.mockMvc.perform(get("/api/teacher/text"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testFindAll_shouldReturn_anArrayOfTeacher() throws Exception {
        ResultActions result = this.mockMvc.perform(get("/api/teacher"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(result.andReturn().getResponse().getContentAsString()).isEqualTo("[{\"id\":1,\"lastName\":\"teacherDBLastName\",\"firstName\":\"teacherDBFirstName\",\"createdAt\":\"2024-08-17T17:33:46.105399\",\"updatedAt\":\"2025-01-17T17:33:46.105399\"}]");
    }
}
