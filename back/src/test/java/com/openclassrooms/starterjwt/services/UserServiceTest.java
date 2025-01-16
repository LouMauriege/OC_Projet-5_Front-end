package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    UserService userServiceUnderTest;

    User myUser;

    @BeforeEach
    public void init() {
        userServiceUnderTest = new UserService(userRepository);
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
    }

    @Test
    public void testFindById_shouldReturnTheUser_whenExist() {
        // Arrange
        User expectedUser = myUser;
        when(userRepository.findById(0L)).thenReturn(Optional.ofNullable(myUser));

        // Act
        User actualUser = userServiceUnderTest.findById(0L);

        // Assert
        verify(userRepository).findById(0L);
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void testFindById_shouldReturnNull_whenDoesNotExist() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        User actualUser = userServiceUnderTest.findById(1L);

        // Assert
        verify(userRepository).findById(1L);
        assertThat(actualUser).isNull();
    }

    @Test
    public void testDeleteById_shouldCallUserRepoDeleteById() {
        // Arrange

        // Act
        userServiceUnderTest.delete(0L);

        // Assert
        verify(userRepository).deleteById(0L);
    }
}
