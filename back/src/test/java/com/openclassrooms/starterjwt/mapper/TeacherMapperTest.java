package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class TeacherMapperTest {

    @InjectMocks
    TeacherMapperImpl teacherMapperUnderTest;

    @Mock
    TeacherService teacherService;

    Teacher firstTeacher;

    TeacherDto firstTeacherDto;

    List<Teacher> teacherList;

    List<TeacherDto> teacherDtoList;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        firstTeacher = Teacher.builder()
                .id(0L)
                .lastName("Krapabelle")
                .firstName("Edna")
                .createdAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .updatedAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .build();
        teacherList = Collections.singletonList(firstTeacher);

        firstTeacherDto = new TeacherDto();
        firstTeacherDto.setId(0L);
        firstTeacherDto.setLastName("Krapabelle");
        firstTeacherDto.setFirstName("Edna");
        firstTeacherDto.setCreatedAt(LocalDateTime.of(1989,12,17,13,30,10,5));
        firstTeacherDto.setUpdatedAt(LocalDateTime.of(1989,12,17,13,30,10,5));
        teacherDtoList = Collections.singletonList(firstTeacherDto);
    }

    @Test
    public void toEntity_shouldReturn_teacherEntityFromDto() {
        // Arrange

        // Act
        Teacher actualTeacher = teacherMapperUnderTest.toEntity(firstTeacherDto);

        // Assert
        assertThat(actualTeacher).isEqualTo(firstTeacher);
        assertThat(actualTeacher.getId()).isEqualTo(firstTeacher.getId());
        assertThat(actualTeacher.getLastName()).isEqualTo(firstTeacher.getLastName());
        assertThat(actualTeacher.getFirstName()).isEqualTo(firstTeacher.getFirstName());
        assertThat(actualTeacher.getCreatedAt()).isEqualTo(firstTeacher.getCreatedAt());
        assertThat(actualTeacher.getUpdatedAt()).isEqualTo(firstTeacher.getUpdatedAt());
    }

    @Test
    public void toEntity_shouldReturn_nullEntity() {
        // Arrange

        // Act
        Teacher actualTeacherNull = teacherMapperUnderTest.toEntity((TeacherDto) null);

        // Assert
        assertThat(actualTeacherNull).isNull();
    }

    @Test
    public void toEntityList_shouldReturn_teacherEntityFromDto() {
        // Arrange

        // Act
        List<Teacher> actualTeacherList = teacherMapperUnderTest.toEntity(teacherDtoList);

        // Assert
        assertThat(actualTeacherList.get(0)).isEqualTo(firstTeacher);
        assertThat(actualTeacherList.get(0).getId()).isEqualTo(firstTeacher.getId());
        assertThat(actualTeacherList.get(0).getLastName()).isEqualTo(firstTeacher.getLastName());
        assertThat(actualTeacherList.get(0).getFirstName()).isEqualTo(firstTeacher.getFirstName());
        assertThat(actualTeacherList.get(0).getCreatedAt()).isEqualTo(firstTeacher.getCreatedAt());
        assertThat(actualTeacherList.get(0).getUpdatedAt()).isEqualTo(firstTeacher.getUpdatedAt());
    }

    @Test
    public void toEntityList_shouldReturn_nullEntity() {
        // Arrange

        // Act
        List<Teacher> actualTeacherList = teacherMapperUnderTest.toEntity((List<TeacherDto>) null);

        // Assert
        assertThat(actualTeacherList).isNull();
    }

    @Test
    public void toDto_shouldReturn_teacherDtoFromEntity() {
        // Arrange

        // Act
        TeacherDto actualTeacherDto = teacherMapperUnderTest.toDto(firstTeacher);

        // Assert
        assertThat(actualTeacherDto).isEqualTo(firstTeacherDto);
        assertThat(actualTeacherDto.getId()).isEqualTo(firstTeacherDto.getId());
        assertThat(actualTeacherDto.getLastName()).isEqualTo(firstTeacherDto.getLastName());
        assertThat(actualTeacherDto.getFirstName()).isEqualTo(firstTeacherDto.getFirstName());
        assertThat(actualTeacherDto.getCreatedAt()).isEqualTo(firstTeacherDto.getCreatedAt());
        assertThat(actualTeacherDto.getUpdatedAt()).isEqualTo(firstTeacherDto.getUpdatedAt());
    }

    @Test
    public void toDto_shouldReturn_nullDto() {
        // Arrange

        // Act
        TeacherDto actualTeacherDto = teacherMapperUnderTest.toDto((Teacher) null);

        // Assert
        assertThat(actualTeacherDto).isNull();
    }

    @Test
    public void toDtoList_shouldReturn_teacherDtoFromEntity() {
        // Arrange

        // Act
        List<TeacherDto> actualTeacherDtoList = teacherMapperUnderTest.toDto(teacherList);

        // Assert
        assertThat(actualTeacherDtoList.get(0)).isEqualTo(firstTeacherDto);
        assertThat(actualTeacherDtoList.get(0).getId()).isEqualTo(firstTeacherDto.getId());
        assertThat(actualTeacherDtoList.get(0).getLastName()).isEqualTo(firstTeacherDto.getLastName());
        assertThat(actualTeacherDtoList.get(0).getFirstName()).isEqualTo(firstTeacherDto.getFirstName());
        assertThat(actualTeacherDtoList.get(0).getCreatedAt()).isEqualTo(firstTeacherDto.getCreatedAt());
        assertThat(actualTeacherDtoList.get(0).getUpdatedAt()).isEqualTo(firstTeacherDto.getUpdatedAt());
    }

    @Test
    public void toDtolist_shouldreturn_nullDto() {
        // Arrange

        // Act
        List<TeacherDto> actualTeacherDtoList = teacherMapperUnderTest.toDto((List<Teacher>) null);

        // Assert
        assertThat(actualTeacherDtoList).isNull();
    }
}
