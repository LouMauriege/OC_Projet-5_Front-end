package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserMapperTest {

    @InjectMocks
    UserMapperImpl userMapperUnderTest;

    User myUser;

    UserDto myUserDto;

    List<User> userList;

    List<UserDto> userDtoList;

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
        userList = Collections.singletonList(myUser);

        myUserDto = new UserDto();
        myUserDto.setId(0L);
        myUserDto.setEmail("nelsonmuntz@mail.com");
        myUserDto.setLastName("Muntz");
        myUserDto.setFirstName("Nelson");
        myUserDto.setPassword("passwd");
        myUserDto.setAdmin(false);
        myUserDto.setCreatedAt(LocalDateTime.of(1968,7,11,3,35,20,9));
        myUserDto.setUpdatedAt(LocalDateTime.of(1968,7,11,3,35,20,9));
        userDtoList = Collections.singletonList(myUserDto);
    }

    @Test
    public void toEntity_shouldReturn_userEntityFromDto() {
        // Arrange

        // Act
        User actualUser = userMapperUnderTest.toEntity(myUserDto);

        // Assert
        assertThat(actualUser).isEqualTo(myUser);
        assertThat(actualUser.getId()).isEqualTo(myUser.getId());
        assertThat(actualUser.getEmail()).isEqualTo(myUser.getEmail());
        assertThat(actualUser.getLastName()).isEqualTo(myUser.getLastName());
        assertThat(actualUser.getFirstName()).isEqualTo(myUser.getFirstName());
        assertThat(actualUser.getPassword()).isEqualTo(myUser.getPassword());
        assertThat(actualUser.isAdmin()).isEqualTo(myUser.isAdmin());
        assertThat(actualUser.getCreatedAt()).isEqualTo(myUser.getCreatedAt());
        assertThat(actualUser.getUpdatedAt()).isEqualTo(myUser.getUpdatedAt());
    }

    @Test
    public void toEntity_shouldReturn_nullEntity() {
        // Arrange

        // Act
        User actualUser = userMapperUnderTest.toEntity((UserDto) null);

        // Assert
        assertThat(actualUser).isNull();
    }

    @Test
    public void toEntityList_shouldReturn_userEntityFromDto() {
        // Arrange

        // Act
        List<User> actualUser = userMapperUnderTest.toEntity(userDtoList);

        // Assert
        assertThat(actualUser.get(0)).isEqualTo(myUser);
        assertThat(actualUser.get(0).getId()).isEqualTo(myUser.getId());
        assertThat(actualUser.get(0).getEmail()).isEqualTo(myUser.getEmail());
        assertThat(actualUser.get(0).getLastName()).isEqualTo(myUser.getLastName());
        assertThat(actualUser.get(0).getFirstName()).isEqualTo(myUser.getFirstName());
        assertThat(actualUser.get(0).getPassword()).isEqualTo(myUser.getPassword());
        assertThat(actualUser.get(0).isAdmin()).isEqualTo(myUser.isAdmin());
        assertThat(actualUser.get(0).getCreatedAt()).isEqualTo(myUser.getCreatedAt());
        assertThat(actualUser.get(0).getUpdatedAt()).isEqualTo(myUser.getUpdatedAt());
    }

    @Test
    public void toEntityList_shouldReturn_nullEntity() {
        // Arrange

        // Act
        List<User> actualUser = userMapperUnderTest.toEntity((List<UserDto>) null);

        // Assert
        assertThat(actualUser).isNull();
    }

    @Test
    public void toDto_shouldReturn_userDtoFromEntity() {
        // Arrange

        // Act
        UserDto actualUserDto = userMapperUnderTest.toDto(myUser);

        // Assert
        assertThat(actualUserDto).isEqualTo(myUserDto);
        assertThat(actualUserDto.getId()).isEqualTo(myUserDto.getId());
        assertThat(actualUserDto.getEmail()).isEqualTo(myUserDto.getEmail());
        assertThat(actualUserDto.getLastName()).isEqualTo(myUserDto.getLastName());
        assertThat(actualUserDto.getFirstName()).isEqualTo(myUserDto.getFirstName());
        assertThat(actualUserDto.getPassword()).isEqualTo(myUserDto.getPassword());
        assertThat(actualUserDto.isAdmin()).isEqualTo(myUserDto.isAdmin());
        assertThat(actualUserDto.getCreatedAt()).isEqualTo(myUserDto.getCreatedAt());
        assertThat(actualUserDto.getUpdatedAt()).isEqualTo(myUserDto.getUpdatedAt());
    }

    @Test
    public void toDto_shouldReturn_nullDto() {
        // Arrange

        // Act
        UserDto actualUserDto = userMapperUnderTest.toDto((User) null);

        // Assert
        assertThat(actualUserDto).isNull();
    }

    @Test
    public void toDtoList_shouldReturn_userDtoFromEntity() {
        // Arrange

        // Act
        List<UserDto> actualUserDtoList = userMapperUnderTest.toDto(userList);

        // Assert
        assertThat(actualUserDtoList.get(0)).isEqualTo(myUserDto);
        assertThat(actualUserDtoList.get(0).getId()).isEqualTo(myUserDto.getId());
        assertThat(actualUserDtoList.get(0).getEmail()).isEqualTo(myUserDto.getEmail());
        assertThat(actualUserDtoList.get(0).getLastName()).isEqualTo(myUserDto.getLastName());
        assertThat(actualUserDtoList.get(0).getFirstName()).isEqualTo(myUserDto.getFirstName());
        assertThat(actualUserDtoList.get(0).getPassword()).isEqualTo(myUserDto.getPassword());
        assertThat(actualUserDtoList.get(0).isAdmin()).isEqualTo(myUserDto.isAdmin());
        assertThat(actualUserDtoList.get(0).getCreatedAt()).isEqualTo(myUserDto.getCreatedAt());
        assertThat(actualUserDtoList.get(0).getUpdatedAt()).isEqualTo(myUserDto.getUpdatedAt());
    }

    @Test
    public void toDtoList_shouldReturn_nullDto() {
        // Arrange

        // Act
        List<UserDto> actualUserDtoList = userMapperUnderTest.toDto((List<User>) null);

        // Assert
        assertThat(actualUserDtoList).isNull();
    }
}
