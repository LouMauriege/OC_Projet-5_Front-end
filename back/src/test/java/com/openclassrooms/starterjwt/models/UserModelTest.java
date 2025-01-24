package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserModelTest {

    User userBuilder;

    User userSetAttribute;

    @BeforeEach
    public void init() {
        userBuilder = User.builder()
                .id(2L)
                .email("barneygumble@mail.com")
                .lastName("Gumble")
                .firstName("Barney")
                .password("passwd")
                .admin(false)
                .createdAt(LocalDateTime.of(2024, 11, 7, 17, 33, 46, 105399))
                .updatedAt(LocalDateTime.of(2024, 11, 7, 17, 33, 46, 105399))
                .build();

        userSetAttribute = new User();
        userSetAttribute.setId(2L);
        userSetAttribute.setEmail("barneygumble@mail.com");
        userSetAttribute.setLastName("Gumble");
        userSetAttribute.setFirstName("Barney");
        userSetAttribute.setPassword("passwd");
        userSetAttribute.setAdmin(false);
        userSetAttribute.setCreatedAt(LocalDateTime.of(2024, 11, 7, 17, 33, 46, 105399));
        userSetAttribute.setUpdatedAt(LocalDateTime.of(2024, 11, 7, 17, 33, 46, 105399));
    }

    @Test
    public void testSameHash_withSameId() {
        // Arrange

        // Act
        int hashUserBuilder = userBuilder.hashCode();
        int hashUserSetAttribute = userSetAttribute.hashCode();

        // Assert
        assertThat(hashUserBuilder).isEqualTo(hashUserSetAttribute);
    }

    @Test
    public void testDiffHash_withDiffId() {
        // Arrange
        userSetAttribute.setId(1L);

        // Act
        int hashUserBuilder = userBuilder.hashCode();
        int hashUserSetAttribute = userSetAttribute.hashCode();

        // Assert
        assertThat(hashUserBuilder).isNotEqualTo(hashUserSetAttribute);
    }

    @Test
    public void testSameObject_withEqualsMethod() {
        // Arrange

        // Act
        Boolean isEquals = userBuilder.equals(userSetAttribute);

        // Assert
        assertThat(isEquals).isTrue();
    }

    @Test
    public void testDiffObject_withEqualsMethod() {
        // Arrange
        userSetAttribute.setId(1L);

        // Act
        Boolean isEquals = userBuilder.equals(userSetAttribute);

        // Assert
        assertThat(isEquals).isFalse();
    }

    @Test
    public void testRequiredArgConstructorMethod() {
        // Arrange & Act
        User userConstructor = new User(
                "test@mail.com",
                "lastname",
                "firstname",
                "passwd",
                true
        );

        // Assert
        assertThat(userConstructor).isNotNull();
        assertThat(userConstructor.getEmail()).isEqualTo("test@mail.com");
        assertThat(userConstructor.getLastName()).isEqualTo("lastname");
        assertThat(userConstructor.getFirstName()).isEqualTo("firstname");
        assertThat(userConstructor.getPassword()).isEqualTo("passwd");
        assertThat(userConstructor.isAdmin()).isTrue();
    }
}
