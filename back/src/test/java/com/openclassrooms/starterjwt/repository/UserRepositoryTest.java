package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.SpringBootSecurityJwtApplication;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SpringBootSecurityJwtApplication.class)
public class UserRepositoryTest {
//
//    @Autowired
//    UserRepository userRepositoryUnderTest;
//
//    User myUser;
//    String myUserFromDBEmail;
//
//    @BeforeEach
//    public void init() {
//        myUserFromDBEmail = "barneygumbel@mail.com";
//        myUser = User.builder()
//                .id(2L)
//                .email("barneygumble@mail.com")
//                .lastName("Gumble")
//                .firstName("Barney")
//                .password("passwd")
//                .admin(false)
//                .createdAt(LocalDateTime.of(2024,11,7,17,33,46,105399))
//                .updatedAt(LocalDateTime.of(2024,11,7,17,33,46,105399))
//                .build();
//    }
//
//    @Test
//    public void testFindByEmail() {
//        // Arrange already done in the data.sql file
//
//        // Act
//        User actualUser = userRepositoryUnderTest.findByEmail(myUserFromDBEmail).orElseThrow(NotFoundException::new);
//        System.out.println(actualUser);
//
//        // Assert
//        assertThat(actualUser).isEqualTo(myUser);
//    }
//
//    @Test
//    public void testByEmails_shouldReturnTrue_whenExist() {
//        // Arrange already done in the data.sql file
//
//        // Act
//        Boolean exist = userRepositoryUnderTest.existsByEmail(myUserFromDBEmail);
//
//        // Assert
//        assertThat(exist).isTrue();
//    }
//
//    @Test
//    public void testByEmails_shouldReturnFalse_whenDoesNotExist() {
//        // Arrange already done in the data.sql file
//
//        // Act
//        Boolean exist = userRepositoryUnderTest.existsByEmail("notExistingEmail");
//
//        // Assert
//        assertThat(exist).isFalse();
//    }
}
