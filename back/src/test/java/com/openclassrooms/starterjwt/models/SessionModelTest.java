package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SessionModelTest {

    Session sessionBuilder;
    Session sessionSetAttribute;
    Session sessionConstructor;

    @BeforeEach
    public void init() {
        sessionBuilder = Session.builder()
                .id(0L)
                .name("Morning Session")
                .date(new Date(1737058385177L))
                .description("Yoga in the morning.")
                .teacher(new Teacher())
                .users(Collections.singletonList(new User()))
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .updatedAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .build();

        sessionSetAttribute = new Session();
        sessionSetAttribute.setId(0L);
        sessionSetAttribute.setName("Morning Session");
        sessionSetAttribute.setDate(new Date(1737058385177L));
        sessionSetAttribute.setDescription("Yoga in the morning.");
        sessionSetAttribute.setTeacher(new Teacher());
        sessionSetAttribute.setUsers(Collections.singletonList(new User()));
        sessionSetAttribute.setCreatedAt(LocalDateTime.of(1968,7,11,3,35,20,9));
        sessionSetAttribute.setUpdatedAt(LocalDateTime.of(1968,7,11,3,35,20,9));

        sessionConstructor = new Session(
                0L,
                "Morning Session",
                new Date(1737058385177L),
                "Yoga in the morning.",
                new Teacher(),
                Collections.singletonList(new User()),
                LocalDateTime.of(1968,7,11,3,35,20,9),
                LocalDateTime.of(1968,7,11,3,35,20,9)
        );
    }

    @Test
    public void testCreateSession_shouldBeSame() {
        // Arrange & Act

        // Assert
        assertThat(sessionBuilder)
                .extracting("id", "name", "description", "date", "teacher", "users", "createdAt", "updatedAt")
                .containsExactly(
                        sessionSetAttribute.getId(),
                        sessionSetAttribute.getName(),
                        sessionSetAttribute.getDescription(),
                        sessionSetAttribute.getDate(),
                        sessionSetAttribute.getTeacher(),
                        sessionSetAttribute.getUsers(),
                        sessionSetAttribute.getCreatedAt(),
                        sessionSetAttribute.getUpdatedAt()
                );
        assertThat(sessionSetAttribute)
                .extracting("id", "name", "description", "date", "teacher", "users", "createdAt", "updatedAt")
                .containsExactly(
                        sessionConstructor.getId(),
                        sessionConstructor.getName(),
                        sessionConstructor.getDescription(),
                        sessionConstructor.getDate(),
                        sessionConstructor.getTeacher(),
                        sessionConstructor.getUsers(),
                        sessionConstructor.getCreatedAt(),
                        sessionConstructor.getUpdatedAt()
                );
    }

    @Test
    public void testSameHashcode_whenSameId() {
        // Arrange

        // Act
        int hashCodeBuilder = sessionBuilder.hashCode();
        int hashCodeConstructor = sessionConstructor.hashCode();

        // Assert
        assertThat(hashCodeBuilder).isEqualTo(hashCodeConstructor);
    }

    @Test
    public void testDifferentHashcode_whenDifferentId() {
        // Arrange
        sessionBuilder.setId(1L);

        // Act
        int hashCodeBuilder = sessionBuilder.hashCode();
        int hashCodeConstructor = sessionConstructor.hashCode();

        // Assert
        assertThat(hashCodeBuilder).isNotEqualTo(hashCodeConstructor);
    }

    @Test
    public void testSameHashcode_withEqualsMethod() {
        // Arrange

        // Act
        Boolean result = sessionBuilder.equals(sessionConstructor);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void testDifferentHashcode_withEqualsMethod() {
        // Arrange
        sessionBuilder.setId(1L);

        // Act
        Boolean result = sessionBuilder.equals(sessionConstructor);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testToString_shouldReturn_stringOfSession() {
        // Arrange

        // Act
        String sessionBuilderToString =  sessionBuilder.toString();

        // Assert
        assertThat(sessionBuilderToString).isEqualTo(
                "Session(id=0, name=Morning Session, date=Thu Jan 16 21:13:05 CET 2025, description=Yoga in the morning., teacher=Teacher(id=null, lastName=null, firstName=null, createdAt=null, updatedAt=null), users=[User(id=null, email=null, lastName=null, firstName=null, password=null, admin=false, createdAt=null, updatedAt=null)], createdAt=1968-07-11T03:35:20.000000009, updatedAt=1968-07-11T03:35:20.000000009)"
        );
    }

    @Test
    public void testBuilderToString_shouldReturn_stringOfSession() {
        // Arrange & Act
        String sessionBuilderToString = Session.builder()
                .id(0L)
                .name("Morning Session")
                .date(new Date(1737058385177L))
                .description("Yoga in the morning.")
                .teacher(new Teacher())
                .users(Collections.singletonList(new User()))
                .createdAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .updatedAt(LocalDateTime.of(1968,7,11,3,35,20,9))
                .build()
                .toString();

        // Assert
        assertThat(sessionBuilderToString).isEqualTo(
                "Session(id=0, name=Morning Session, date=Thu Jan 16 21:13:05 CET 2025, description=Yoga in the morning., teacher=Teacher(id=null, lastName=null, firstName=null, createdAt=null, updatedAt=null), users=[User(id=null, email=null, lastName=null, firstName=null, password=null, admin=false, createdAt=null, updatedAt=null)], createdAt=1968-07-11T03:35:20.000000009, updatedAt=1968-07-11T03:35:20.000000009)"
        );
    }

    @Test
    public void testBuilderIndividualToString_shouldReturn_stringOfSession() {
        // Arrange

        // Act
        String sessionBuilderIndividualToString = "Session(" +
                "id=" + sessionBuilder.getId().toString() + ", " +
                "name=" + sessionBuilder.getName() + ", " +
                "date=" + sessionBuilder.getDate().toString() + ", " +
                "description=" + sessionBuilder.getDescription() + ", " +
                "teacher=" + sessionBuilder.getTeacher().toString() + ", " +
                "users=" + sessionBuilder.getUsers().toString() + ", " +
                "createdAt=" + sessionBuilder.getCreatedAt().toString() + ", " +
                "updatedAt=" + sessionBuilder.getUpdatedAt().toString() + ")";

        // Assert
        assertThat(sessionBuilderIndividualToString).isEqualTo(
                "Session(id=0, name=Morning Session, date=Thu Jan 16 21:13:05 CET 2025, description=Yoga in the morning., teacher=Teacher(id=null, lastName=null, firstName=null, createdAt=null, updatedAt=null), users=[User(id=null, email=null, lastName=null, firstName=null, password=null, admin=false, createdAt=null, updatedAt=null)], createdAt=1968-07-11T03:35:20.000000009, updatedAt=1968-07-11T03:35:20.000000009)"
        );
    }

    @Test
    public void testBuilderToString() {
        // Arrange

        // Act
        String sessionBuilderToString = sessionBuilder.toString();

        // Assert
        assertThat(sessionBuilderToString).isEqualTo(
                "Session(id=0, name=Morning Session, date=Thu Jan 16 21:13:05 CET 2025, description=Yoga in the morning., teacher=Teacher(id=null, lastName=null, firstName=null, createdAt=null, updatedAt=null), users=[User(id=null, email=null, lastName=null, firstName=null, password=null, admin=false, createdAt=null, updatedAt=null)], createdAt=1968-07-11T03:35:20.000000009, updatedAt=1968-07-11T03:35:20.000000009)"
        );
    }
}
