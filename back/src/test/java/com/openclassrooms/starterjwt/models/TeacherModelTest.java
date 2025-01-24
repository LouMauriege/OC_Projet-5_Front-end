package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TeacherModelTest {

    Teacher teacherBuilder;

    Teacher teacherSetAttribute;

    @BeforeEach
    public void init() {
        teacherBuilder = Teacher.builder()
                .id(0L)
                .lastName("Krapabelle")
                .firstName("Edna")
                .createdAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .createdAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .build();

        teacherSetAttribute = new Teacher();
        teacherSetAttribute.setId(0L);
        teacherSetAttribute.setLastName("Krapabelle");
        teacherSetAttribute.setFirstName("Edna");
        teacherSetAttribute.setCreatedAt(LocalDateTime.of(1989,12,17,13,30,10,5));
        teacherSetAttribute.setUpdatedAt(LocalDateTime.of(1989,12,17,13,30,10,5));
    }

    @Test
    public void testSameHash_withSameId() {
        // Arrange

        // Act
        int hashTeacherBuilder = teacherBuilder.hashCode();
        int hashTeacherSetAttribute = teacherSetAttribute.hashCode();

        // Assert
        assertThat(hashTeacherBuilder).isEqualTo(hashTeacherSetAttribute);
    }

    @Test
    public void testDiffHash_withDiffId() {
        // Arrange
        teacherSetAttribute.setId(1L);

        // Act
        int hashTeacherBuilder = teacherBuilder.hashCode();
        int hashTeacherSetAttribute = teacherSetAttribute.hashCode();

        // Assert
        assertThat(hashTeacherBuilder).isNotEqualTo(hashTeacherSetAttribute);
    }

    @Test
    public void testSameObject_withEqualsMethod() {
        // Arrange

        // Act
        Boolean isEquals = teacherBuilder.equals(teacherSetAttribute);

        // Assert
        assertThat(isEquals).isTrue();
    }

    @Test
    public void testDiffObject_withEqualsMethod() {
        // Arrange
        teacherSetAttribute.setId(1L);

        // Act
        Boolean isEquals = teacherBuilder.equals(teacherSetAttribute);

        // Assert
        assertThat(isEquals).isFalse();
    }
}
