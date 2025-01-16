package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TeacherServiceTest {
    TeacherService teacherServiceUnderTest;

    @Mock
    private TeacherRepository teacherRepository;

    Stream<Teacher> teachers;

    Teacher firstTeacher;
    Teacher secondTeacher;
    Teacher thirdTeacher;

    @BeforeEach
    public void init() {
        teacherServiceUnderTest = new TeacherService(teacherRepository);
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
    public void testFindAll() {
        // Arrange
        List<Teacher> expectedTeacherList = teachers.collect(Collectors.toList());
        when(teacherRepository.findAll()).thenReturn(expectedTeacherList);

        // Act
        List<Teacher> actualTeacherList = teacherServiceUnderTest.findAll();

        // Assert
        verify(teacherRepository).findAll();
        assertThat(actualTeacherList).isEqualTo(expectedTeacherList);
    }

    @Test
    public void testFindById_ShouldReturnTheTeacher_WhenExist() {
        // Arrange
        Teacher expectedTeacher = firstTeacher;
        when(teacherRepository.findById(0L)).thenReturn(Optional.ofNullable(expectedTeacher));

        // Act
        Teacher actualTeacherList = teacherServiceUnderTest.findById(0L);

        // Assert
        verify(teacherRepository).findById(0L);
        assertThat(actualTeacherList).isEqualTo(expectedTeacher);
    }

    @Test
    public void testFindById_ShouldReturnNull_WhenDoesNotExist() {
        // Arrange
        when(teacherRepository.findById(3L)).thenReturn(Optional.empty());

        // Act
        Teacher actualTeacherList = teacherServiceUnderTest.findById(3L);

        // Assert
        verify(teacherRepository).findById(3L);
        assertThat(actualTeacherList).isNull();
    }
}
