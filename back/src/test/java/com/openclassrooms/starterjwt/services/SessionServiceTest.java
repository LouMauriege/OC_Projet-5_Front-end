package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SessionServiceTest {
    @Mock
    SessionRepository sessionRepository;

    @Mock
    UserRepository userRepository;

    SessionService sessionServiceUnderTest;

    Stream<Session> sessionStream;

    Session firstSession;
    Session secondSession;
    Session thirdSession;

    User myUser;

    Teacher firstTeacher;

    @BeforeEach
    public void init() {
        sessionServiceUnderTest = new SessionService(sessionRepository, userRepository);

        myUser = User.builder()
                .id(0L)
                .email("nelsonmuntz@mail.com")
                .lastName("Muntz")
                .firstName("Nelson")
                .password("passwd")
                .admin(false)
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .build();

        firstTeacher = Teacher.builder()
                .id(0L)
                .lastName("Krapabelle")
                .firstName("Edna")
                .createdAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .createdAt(LocalDateTime.of(1989,12,17,13,30,10,5))
                .build();

        firstSession = Session.builder()
                .id(0L)
                .name("Morning Session")
                .date(new Date(1737058385177L))
                .description("Yoga in the morning.")
                .teacher(firstTeacher)
                .users(Collections.singletonList(myUser))
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .build();

        secondSession = Session.builder()
                .id(1L)
                .name("Afternoon Session")
                .date(new Date(1737058385987L))
                .description("Yoga in the afternoon.")
                .teacher(firstTeacher)
                .users(new ArrayList<User>())
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .build();
        thirdSession = new Session();

        sessionStream = Stream.of(
                firstSession,
                secondSession,
                thirdSession
        );
    }

    @Test
    public void testCreate() {
        // Arrange
        when(sessionRepository.save(firstSession)).thenReturn(firstSession);

        // Act
        Session createdSession = sessionServiceUnderTest.create(firstSession);

        // Assert
        verify(sessionRepository).save(firstSession);
        assertThat(createdSession).isEqualTo(firstSession);
    }

    @Test
    public void testDelete() {
        // Arrange

        // Act
        sessionServiceUnderTest.delete(firstSession.getId());

        // Assert
        verify(sessionRepository).deleteById(firstSession.getId());
    }

    @Test
    public void testFindAll() {
        // Arrange
        List<Session> expectedSessionList = sessionStream.collect(Collectors.toList());
        when(sessionRepository.findAll()).thenReturn(expectedSessionList);

        // Act
        List<Session> actualSessionList = sessionServiceUnderTest.findAll();

        // Assert
        verify(sessionRepository).findAll();
        assertThat(actualSessionList).isEqualTo(expectedSessionList);
    }

    @Test
    public void testGetById() {
        // Arrange
        Session expectedSession = firstSession;
        when(sessionRepository.findById(0L)).thenReturn(Optional.ofNullable(expectedSession));

        // Act
        Session actualSession = sessionServiceUnderTest.getById(0L);

        // Assert
        verify(sessionRepository).findById(0L);
        assertThat(actualSession).isEqualTo(expectedSession);
    }

    @Test
    public void testUpdate() {
        // Arrange
        when(sessionRepository.save(firstSession)).thenReturn(firstSession);

        // Act
        Session updatedSession = sessionServiceUnderTest.update(0L, firstSession);

        // Assert
        verify(sessionRepository).save(firstSession);
        assertThat(updatedSession).isEqualTo(firstSession);
    }

    @Test
    public void testParticipate_shouldAddUser_whenExist() {
        // Arrange
        when(sessionRepository.findById(secondSession.getId())).thenReturn(Optional.ofNullable(secondSession));
        when(sessionRepository.save(secondSession)).thenReturn(secondSession);
        when(userRepository.findById(myUser.getId())).thenReturn(Optional.ofNullable(myUser));

        // Act
        sessionServiceUnderTest.participate(secondSession.getId(), myUser.getId());

        // Assert
        verify(sessionRepository).findById(secondSession.getId());
        verify(sessionRepository).save(secondSession);
        verify(userRepository).findById(myUser.getId());
        assertThat(secondSession.getUsers()).contains(myUser);
    }

    @Test
    public void testParticipate_shouldThrowNotFoundException_whenUserDoesNotExist() {
        // Arrange
        when(sessionRepository.findById(secondSession.getId())).thenReturn(Optional.ofNullable(secondSession));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> {
            sessionServiceUnderTest.participate(secondSession.getId(), 1L);
        }).isInstanceOf(NotFoundException.class);
        verify(sessionRepository, never()).save(any(Session.class));
    }

    @Test
    public void testParticipate_shouldThrowNotFoundException_whenSessionDoesNotExist() {
        // Arrange
        when(sessionRepository.findById(2L)).thenReturn(Optional.empty());
        when(userRepository.findById(myUser.getId())).thenReturn(Optional.of(myUser));

        // Act & Assert
        assertThatThrownBy(() -> {
            sessionServiceUnderTest.participate(2L, myUser.getId());
        }).isInstanceOf(NotFoundException.class);
        verify(sessionRepository, never()).save(any(Session.class));
    }

    @Test
    public void testParticipate_shouldThrowBadRequest_whenUserAlreadyParticipate() {
        // Arrange
        when(sessionRepository.findById(firstSession.getId())).thenReturn(Optional.of(firstSession));
        when(userRepository.findById(myUser.getId())).thenReturn(Optional.of(myUser));

        // Act & Assert
        assertThatThrownBy(() -> {
            sessionServiceUnderTest.participate(firstSession.getId(), myUser.getId());
        }).isInstanceOf(BadRequestException.class);
        verify(sessionRepository, never()).save(any(Session.class));
    }

    @Test
    public void testNoLongerParticipate_shouldRemoveUser_whenExist() {
        // Arrange
        when(sessionRepository.findById(firstSession.getId())).thenReturn(Optional.ofNullable(firstSession));
        when(sessionRepository.save(firstSession)).thenReturn(firstSession);

        // Act
        sessionServiceUnderTest.noLongerParticipate(firstSession.getId(), myUser.getId());

        // Assert
        verify(sessionRepository).findById(firstSession.getId());
        verify(sessionRepository).save(firstSession);
        assertThat(firstSession.getUsers()).doesNotContain(myUser);
    }

    @Test
    public void testNoLongerParticipate_shouldThrowNotFound_whenSessionDoesNotExist() {
        // Arrange
        when(sessionRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> {
            sessionServiceUnderTest.noLongerParticipate(2L, myUser.getId());
        }).isInstanceOf(NotFoundException.class);
        verify(sessionRepository, never()).save(any(Session.class));
    }

    @Test
    public void testNoLongerParticipate_shouldThrowNotFound_whenUserAlreadyUnparticipate() {
        // Arrange
        when(sessionRepository.findById(secondSession.getId())).thenReturn(Optional.of(secondSession));

        // Act & Assert
        assertThatThrownBy(() -> {
            sessionServiceUnderTest.noLongerParticipate(secondSession.getId(), myUser.getId());
        }).isInstanceOf(BadRequestException.class);
        verify(sessionRepository, never()).save(any(Session.class));
    }
}
