package com.openclassrooms.starterjwt.controller;

import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserController userControllerUnderTest;

    @Mock
    UserMapper userMapper;

    @Mock
    UserService userService;

    User myUser;

    @BeforeEach
    public void init() {
        userControllerUnderTest = new UserController(userService, userMapper);

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
    public void findById_shouldReturnOkStatus_whenExist() {
        // Arrange
        when(userService.findById(myUser.getId())).thenReturn(myUser);

        // Act
        ResponseEntity<?> actualResponse = userControllerUnderTest.findById(String.valueOf(myUser.getId()));

        // Assert
        verify(userService).findById(myUser.getId());
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void findById_shouldReturnNotFound_whenDoesNotExist() {
        // Arrange
        when(userService.findById(5L)).thenReturn(null);

        // Act
        ResponseEntity<?> actualResponse = userControllerUnderTest.findById(String.valueOf(5L));

        // Assert
        verify(userService).findById(5L);
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findById_shouldReturnBadRequest_whenDoesNotExist() {
        // Arrange
        when(userService.findById(5L)).thenThrow(NumberFormatException.class);

        // Act
        ResponseEntity<?> actualResponse = userControllerUnderTest.findById(String.valueOf(5L));

        // Assert
        verify(userService).findById(5L);
        assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
