package com.application.service.users;


import com.application.model.users.Personal;
import com.application.model.users.Users;
import com.application.repository.users.PersonalRepository;
import com.application.repository.users.UsersRepository;
import com.application.util.security.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PersonalRepository personalRepository;
    @InjectMocks
    private PersonalService personalService;

    @Mock
    private PasswordUtil passwordUtil;

    @InjectMocks
    private UsersService usersService; // Ваш основной сервис, который содержит метод authorizeUser

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authorizeUser_success() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        Users user = new Users();
        user.setUsername(username);
        user.setPassword("hashedpassword");
        user.setDeleted(false);

        Personal personal = new Personal();
        personal.setUser(user);
        personal.setPost("Admin");

        when(usersRepository.findByUsername(username)).thenReturn(user);
        when(passwordUtil.verifyPassword(password, user.getPassword())).thenReturn(true);
        when(personalRepository.findByUser(user)).thenReturn(personal);

        // Act
        String result = usersService.authorizeUser(username, password);

        // Assert
        assertEquals("Admin", result);
        verify(usersRepository, times(1)).findByUsername(username);
        verify(passwordUtil, times(1)).verifyPassword(password, user.getPassword());
        verify(personalRepository, times(1)).findByUser(user);
    }

    @Test
    void authorizeUser_invalidUsernameOrPassword() {
        // Arrange
        String username = "testuser";
        String password = "wrongpassword";
        Users user = new Users();
        user.setUsername(username);
        user.setPassword("hashedpassword");
        user.setDeleted(false);

        when(usersRepository.findByUsername(username)).thenReturn(user);
        when(passwordUtil.verifyPassword(password, user.getPassword())).thenReturn(false);

        // Act
        String result = usersService.authorizeUser(username, password);

        // Assert
        assertEquals("Invalid username or password", result);
        verify(usersRepository, times(1)).findByUsername(username);
        verify(passwordUtil, times(1)).verifyPassword(password, user.getPassword());
    }

    @Test
    void authorizeUser_userDeleted() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        Users user = new Users();
        user.setUsername(username);
        user.setPassword("hashedpassword");
        user.setDeleted(true);

        when(usersRepository.findByUsername(username)).thenReturn(user);

        // Act
        String result = usersService.authorizeUser(username, password);

        // Assert
        assertEquals("Invalid username or password", result);
        verify(usersRepository, times(1)).findByUsername(username);
    }

    @Test
    void authorizeUser_userHasNoRole() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        Users user = new Users();
        user.setUsername(username);
        user.setPassword("hashedpassword");
        user.setDeleted(false);

        when(usersRepository.findByUsername(username)).thenReturn(user);
        when(passwordUtil.verifyPassword(password, user.getPassword())).thenReturn(true);
        when(personalRepository.findByUser(user)).thenReturn(null);

        // Act
        String result = usersService.authorizeUser(username, password);

        // Assert
        assertEquals("User has no role", result);
        verify(usersRepository, times(1)).findByUsername(username);
        verify(passwordUtil, times(1)).verifyPassword(password, user.getPassword());
        verify(personalRepository, times(1)).findByUser(user);
    }

    @Test
    void addUser() {
        // Arrange
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setSurname("TestSurname");
        user.setName("TestName");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        user.setPatronymic("TestPatronymic");
        user.setPhoneNumber("1234567890");
        user.setEmail("testuser@example.com");
        user.setDeleted(false);
        user.setActive(true);
        user.setBlocked(false);

        // Act
        usersService.addUser(user);
        Personal personal = new Personal();
        personal.setPost("Администратор");
        personal.setUser(user);
        personalRepository.save(personal);

        // Assert
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void addUser_existingUser() {
        // Arrange
        Users user = new Users();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setSurname("TestSurname");
        user.setName("TestName");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
        user.setPatronymic("TestPatronymic");
        user.setPhoneNumber("1234567890");
        user.setEmail("testuser@example.com");
        user.setDeleted(false);
        user.setActive(true);
        user.setBlocked(false);

        // Сначала сохраняем пользователя
        usersService.addUser(user);


        // Assert
        verify(usersRepository, times(1)).save(user);
    }
    @Test
    void addUser_existingPersonal() {

        Personal personal = new Personal();
        personal.setId(2L);
        personal.setPost("Employee");
        personalService.addUser(personal);

        personal.setPost("Director");
        personalService.addUser(personal);

        verify(personalRepository, times(2)).save(personal);
        assertEquals("Director", personal.getPost());
    }
}
