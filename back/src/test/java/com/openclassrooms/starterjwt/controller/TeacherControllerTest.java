package com.openclassrooms.starterjwt.controller;

import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TeacherControllerTest {

    @Autowired
    TeacherController teacherControllerUnderTest;

    @Mock
    TeacherMapper teacherMapper;

    @Mock
    TeacherService teacherService;

    Stream<Teacher> teachers;

    Teacher firstTeacher;
    Teacher secondTeacher;
    Teacher thirdTeacher;

    @BeforeEach
    public void init() {
        teacherControllerUnderTest = new TeacherController(teacherService, teacherMapper);

        firstTeacher = Teacher.builder()
                .id(0L)
                .lastName("Krapabelle")
                .firstName("Edna")
                .createdAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .createdAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .build();
        secondTeacher = Teacher.builder()
                .id(1L)
                .lastName("Skinner")
                .firstName("Seymour")
                .createdAt(LocalDateTime.of(2024,1,7,3,35,20,9))
                .createdAt(LocalDateTime.of(2024,1,7,3,35,20,9))
                .build();
        thirdTeacher = Teacher.builder()
                .id(2L)
                .lastName("Powell")
                .firstName("Herbert")
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .build();
        teachers = Stream.of(
                firstTeacher,
                secondTeacher,
                thirdTeacher
        );

    }

    @Test
    public void findById_shouldReturnOkStatus_whenExist() {
        // Arrange
        when(teacherService.findById(firstTeacher.getId())).thenReturn(firstTeacher);

        // Act
        ResponseEntity<?> actualResponse = teacherControllerUnderTest.findById(String.valueOf(firstTeacher.getId()));

        // Assert
        verify(teacherService).findById(firstTeacher.getId());
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void findById_shouldReturnNotFound_whenDoesNotExist() {
        // Arrange
        when(teacherService.findById(5L)).thenReturn(null);

        // Act
        ResponseEntity<?> actualResponse = teacherControllerUnderTest.findById(String.valueOf(5L));

        // Assert
        verify(teacherService).findById(5L);
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findById_shouldReturnBadRequest_whenRequestWithoutId() {
        // Arrange
        when(teacherService.findById(5L)).thenThrow(NumberFormatException.class);

        // Act
        ResponseEntity<?> actualResponse = teacherControllerUnderTest.findById(String.valueOf(5L));

        // Assert
        verify(teacherService).findById(5L);
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void findAll_shouldReturnOkStatus() {
        // Arrange
        List<Teacher> expectedTeacherList = teachers.collect(Collectors.toList());
        when(teacherService.findAll()).thenReturn(expectedTeacherList);

        // Act
        ResponseEntity<?> actualResponse = teacherControllerUnderTest.findAll();

        // Assert
        verify(teacherService).findAll();
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
