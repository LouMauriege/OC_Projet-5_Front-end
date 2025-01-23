package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
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

public class SessionMapperTest {

    @InjectMocks
    SessionMapperImpl sessionMapperUnderTest;

    @Mock
    TeacherService teacherService;

    @Mock
    UserService userService;

    Session firstSession;

    SessionDto firstSessionDto;

    List<Session> sessionList;

    List<SessionDto> sessionDtoList;

    User myUser;

    Teacher firstTeacher;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        myUser = User.builder()
                .id(0L)
                .email("nelsonmuntz@mail.com")
                .lastName("Muntz")
                .firstName("Nelson")
                .password("passwd")
                .admin(false)
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .updatedAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .build();
        when(userService.findById(0L)).thenReturn(myUser);

        firstTeacher = Teacher.builder()
                .id(0L)
                .lastName("Krapabelle")
                .firstName("Edna")
                .createdAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .updatedAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .build();
        when(teacherService.findById(0L)).thenReturn(firstTeacher);

        firstSession = Session.builder()
                .id(0L)
                .name("Morning Session")
                .date(new Date(1737058385177L))
                .description("Yoga in the morning.")
                .teacher(firstTeacher)
                .users(Collections.singletonList(myUser))
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .updatedAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .build();
        sessionList = Collections.singletonList(firstSession);

        firstSessionDto = new SessionDto(
                0L,
                "Morning Session",
                new Date(1737058385177L),
                firstTeacher.getId(),
                "Yoga in the morning.",
                Collections.singletonList(myUser.getId()),
                LocalDateTime.of(1968,7,11,3,35,20,9),
                LocalDateTime.of(1968,7,11,3,35,20,9)
        );
        sessionDtoList = Collections.singletonList(firstSessionDto);
    }

    @Test
    public void toEntity_shouldReturn_entityFromDto() {
        // Arrange
        when(teacherService.findById(0L)).thenReturn(firstTeacher);
        firstSessionDto.setTeacher_id(0L);

        // Act
        Session actualSession = sessionMapperUnderTest.toEntity(firstSessionDto);

        // Assert
        verify(teacherService).findById(firstSessionDto.getTeacher_id());
        assertThat(actualSession).isEqualTo(firstSession);
        assertThat(actualSession.getId()).isEqualTo(firstSession.getId());
        assertThat(actualSession.getName()).isEqualTo(firstSession.getName());
        assertThat(actualSession.getDate()).isEqualTo(firstSession.getDate());
        assertThat(actualSession.getTeacher()).isEqualTo(firstSession.getTeacher());
        assertThat(actualSession.getDescription()).isEqualTo(firstSession.getDescription());
        assertThat(actualSession.getUsers()).isEqualTo(firstSession.getUsers());
        assertThat(actualSession.getCreatedAt()).isEqualTo(firstSession.getCreatedAt());
        assertThat(actualSession.getUpdatedAt()).isEqualTo(firstSession.getUpdatedAt());
    }

    @Test
    public void toEntity_shouldReturn_nullEntity() {
        // Arrange

        // Act
        Session actualSessionNull = sessionMapperUnderTest.toEntity((SessionDto) null);

        // Assert
        assertThat(actualSessionNull).isNull();
    }

    @Test
    public void toEntity_shouldReturn_nullTeacher() {
        // Arrange
        firstSession.setTeacher(null);
        firstSessionDto.setTeacher_id(null);

        // Act
        Session actualSessionWithoutTeacher = sessionMapperUnderTest.toEntity(firstSessionDto);

        // Assert
        verify(teacherService, never()).findById(anyLong());
        assertThat(actualSessionWithoutTeacher).isEqualTo(firstSession);
        assertThat(actualSessionWithoutTeacher.getId()).isEqualTo(firstSession.getId());
        assertThat(actualSessionWithoutTeacher.getName()).isEqualTo(firstSession.getName());
        assertThat(actualSessionWithoutTeacher.getDate()).isEqualTo(firstSession.getDate());
        assertThat(actualSessionWithoutTeacher.getTeacher()).isNull();
        assertThat(actualSessionWithoutTeacher.getDescription()).isEqualTo(firstSession.getDescription());
        assertThat(actualSessionWithoutTeacher.getUsers()).isEqualTo(firstSession.getUsers());
        assertThat(actualSessionWithoutTeacher.getCreatedAt()).isEqualTo(firstSession.getCreatedAt());
        assertThat(actualSessionWithoutTeacher.getUpdatedAt()).isEqualTo(firstSession.getUpdatedAt());
    }

    @Test
    public void toEntityList_shouldReturn_entityFromDto() {
        // Arrange

        // Act
        List<Session> actualSessionList = sessionMapperUnderTest.toEntity(sessionDtoList);

        // Assert
        verify(teacherService).findById(sessionDtoList.get(0).getTeacher_id());
        assertThat(actualSessionList.get(0)).isEqualTo(sessionList.get(0));
        assertThat(actualSessionList.get(0).getId()).isEqualTo(sessionList.get(0).getId());
        assertThat(actualSessionList.get(0).getName()).isEqualTo(sessionList.get(0).getName());
        assertThat(actualSessionList.get(0).getDate()).isEqualTo(sessionList.get(0).getDate());
        assertThat(actualSessionList.get(0).getTeacher()).isEqualTo(sessionList.get(0).getTeacher());
        assertThat(actualSessionList.get(0).getDescription()).isEqualTo(sessionList.get(0).getDescription());
        assertThat(actualSessionList.get(0).getUsers()).isEqualTo(sessionList.get(0).getUsers());
        assertThat(actualSessionList.get(0).getCreatedAt()).isEqualTo(sessionList.get(0).getCreatedAt());
        assertThat(actualSessionList.get(0).getUpdatedAt()).isEqualTo(sessionList.get(0).getUpdatedAt());
    }

    @Test
    public void toEntityList_shouldReturn_nullEntity() {
        // Arrange

        // Act
        List<Session> actualSessionListNull = sessionMapperUnderTest.toEntity((List<SessionDto>) null);

        // Assert
        assertThat(actualSessionListNull).isNull();
    }

    @Test
    public void toEntityList_shouldReturn_nullTeacher() {
        // Arrange
        sessionList.get(0).setTeacher(null);
        sessionDtoList.get(0).setTeacher_id(null);

        // Act
        List<Session> actualSessionListWithoutTeacher = sessionMapperUnderTest.toEntity(sessionDtoList);

        // Assert
        verify(teacherService, never()).findById(anyLong());
        assertThat(actualSessionListWithoutTeacher.get(0)).isEqualTo(sessionList.get(0));
        assertThat(actualSessionListWithoutTeacher.get(0).getId()).isEqualTo(sessionList.get(0).getId());
        assertThat(actualSessionListWithoutTeacher.get(0).getName()).isEqualTo(sessionList.get(0).getName());
        assertThat(actualSessionListWithoutTeacher.get(0).getDate()).isEqualTo(sessionList.get(0).getDate());
        assertThat(actualSessionListWithoutTeacher.get(0).getTeacher()).isNull();
        assertThat(actualSessionListWithoutTeacher.get(0).getDescription()).isEqualTo(sessionList.get(0).getDescription());
        assertThat(actualSessionListWithoutTeacher.get(0).getUsers()).isEqualTo(sessionList.get(0).getUsers());
        assertThat(actualSessionListWithoutTeacher.get(0).getCreatedAt()).isEqualTo(sessionList.get(0).getCreatedAt());
        assertThat(actualSessionListWithoutTeacher.get(0).getUpdatedAt()).isEqualTo(sessionList.get(0).getUpdatedAt());
    }

    @Test
    public void toDto_shouldReturn_dtoFromEntity() {
        // Arrange

        // Act
        SessionDto actualSessionDto = sessionMapperUnderTest.toDto(firstSession);

        // Assert
        assertThat(actualSessionDto).isEqualTo(firstSessionDto);
        assertThat(actualSessionDto.getId()).isEqualTo(firstSessionDto.getId());
        assertThat(actualSessionDto.getName()).isEqualTo(firstSessionDto.getName());
        assertThat(actualSessionDto.getDate()).isEqualTo(firstSessionDto.getDate());
        assertThat(actualSessionDto.getTeacher_id()).isEqualTo(firstSessionDto.getTeacher_id());
        assertThat(actualSessionDto.getDescription()).isEqualTo(firstSessionDto.getDescription());
        assertThat(actualSessionDto.getUsers()).isEqualTo(firstSessionDto.getUsers());
        assertThat(actualSessionDto.getCreatedAt()).isEqualTo(firstSessionDto.getCreatedAt());
        assertThat(actualSessionDto.getUpdatedAt()).isEqualTo(firstSessionDto.getUpdatedAt());
    }

    @Test
    public void toDto_shouldReturn_nullDto() {
        // Arrange

        // Act
        SessionDto actualSessionDtoNull = sessionMapperUnderTest.toDto((Session) null);

        // Assert
        assertThat(actualSessionDtoNull).isNull();
    }

    @Test
    public void toDto_shouldReturn_nullTeacher() {
        // Arrange
        firstSession.setTeacher(null);
        firstSessionDto.setTeacher_id(null);

        // Act
        SessionDto actualSessionDtoWithoutTeacher = sessionMapperUnderTest.toDto(firstSession);

        // Assert
        assertThat(actualSessionDtoWithoutTeacher).isEqualTo(firstSessionDto);
        assertThat(actualSessionDtoWithoutTeacher.getId()).isEqualTo(firstSessionDto.getId());
        assertThat(actualSessionDtoWithoutTeacher.getName()).isEqualTo(firstSessionDto.getName());
        assertThat(actualSessionDtoWithoutTeacher.getDate()).isEqualTo(firstSessionDto.getDate());
        assertThat(actualSessionDtoWithoutTeacher.getTeacher_id()).isNull();
        assertThat(actualSessionDtoWithoutTeacher.getDescription()).isEqualTo(firstSessionDto.getDescription());
        assertThat(actualSessionDtoWithoutTeacher.getUsers()).isEqualTo(firstSessionDto.getUsers());
        assertThat(actualSessionDtoWithoutTeacher.getCreatedAt()).isEqualTo(firstSessionDto.getCreatedAt());
        assertThat(actualSessionDtoWithoutTeacher.getUpdatedAt()).isEqualTo(firstSessionDto.getUpdatedAt());
    }

    @Test
    public void toDtoList_shouldReturn_dtoFromEntity() {
        // Arrange

        // Act
        List<SessionDto> actualSessionDtoList = sessionMapperUnderTest.toDto(sessionList);

        // Assert
        assertThat(actualSessionDtoList.get(0)).isEqualTo(sessionDtoList.get(0));
        assertThat(actualSessionDtoList.get(0).getId()).isEqualTo(sessionDtoList.get(0).getId());
        assertThat(actualSessionDtoList.get(0).getName()).isEqualTo(sessionDtoList.get(0).getName());
        assertThat(actualSessionDtoList.get(0).getDate()).isEqualTo(sessionDtoList.get(0).getDate());
        assertThat(actualSessionDtoList.get(0).getTeacher_id()).isEqualTo(sessionDtoList.get(0).getTeacher_id());
        assertThat(actualSessionDtoList.get(0).getDescription()).isEqualTo(sessionDtoList.get(0).getDescription());
        assertThat(actualSessionDtoList.get(0).getUsers()).isEqualTo(sessionDtoList.get(0).getUsers());
        assertThat(actualSessionDtoList.get(0).getCreatedAt()).isEqualTo(sessionDtoList.get(0).getCreatedAt());
        assertThat(actualSessionDtoList.get(0).getUpdatedAt()).isEqualTo(sessionDtoList.get(0).getUpdatedAt());
    }

    @Test
    public void toDtoList_shouldReturn_nullDto() {
        // Arrange

        // Act
        List<SessionDto> actualSessionDtoListNull = sessionMapperUnderTest.toDto((List<Session>) null);

        // Assert
        assertThat(actualSessionDtoListNull).isNull();
    }

    @Test
    public void toDtoList_shouldReturn_nullTeacher() {
        // Arrange
        sessionList.get(0).setTeacher(null);
        sessionDtoList.get(0).setTeacher_id(null);

        // Act
        List<SessionDto> actualSessionDtoListWithoutTeacher = sessionMapperUnderTest.toDto(sessionList);

        // Assert
        assertThat(actualSessionDtoListWithoutTeacher.get(0)).isEqualTo(sessionDtoList.get(0));
        assertThat(actualSessionDtoListWithoutTeacher.get(0).getId()).isEqualTo(sessionDtoList.get(0).getId());
        assertThat(actualSessionDtoListWithoutTeacher.get(0).getName()).isEqualTo(sessionDtoList.get(0).getName());
        assertThat(actualSessionDtoListWithoutTeacher.get(0).getDate()).isEqualTo(sessionDtoList.get(0).getDate());
        assertThat(actualSessionDtoListWithoutTeacher.get(0).getTeacher_id()).isNull();
        assertThat(actualSessionDtoListWithoutTeacher.get(0).getDescription()).isEqualTo(sessionDtoList.get(0).getDescription());
        assertThat(actualSessionDtoListWithoutTeacher.get(0).getUsers()).isEqualTo(sessionDtoList.get(0).getUsers());
        assertThat(actualSessionDtoListWithoutTeacher.get(0).getCreatedAt()).isEqualTo(sessionDtoList.get(0).getCreatedAt());
        assertThat(actualSessionDtoListWithoutTeacher.get(0).getUpdatedAt()).isEqualTo(sessionDtoList.get(0).getUpdatedAt());
    }
}
