package com.logistics.repository;

import com.logistics.model.*;
import com.logistics.service.UserRepository;
import com.logistics.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//Mockito
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    //Mock DB
    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    @Test
    void createAndSaveUser_ShouldCreateUserWithFinancesAndSave() {
        // 1. ARRANGE
        String testName = "Тарас";
        String testSurname = "Шевченко";
        User mockSavedUser = new User(testName, testSurname);
        mockSavedUser.setFinances(new Finances());

        when(userRepository.save(any(User.class))).thenReturn(mockSavedUser);

        // 2. ACT
        User result = userService.CreateAndSaveUser(testName, testSurname);

        // 3. ASSERT
        assertNotNull(result, "Користувач не має бути null");
        assertEquals(testName, result.getName(), "Ім'я має співпадати");
        assertNotNull(result.getFinances(), "При створенні юзеру мають видаватися фінанси");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // 1. ARRANGE
        User user1 = new User("Іван", "Франко");
        User user2 = new User("Леся", "Українка");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // 2. ACT
        List<User> result = userService.getAllUsers();

        // 3. ASSERT
        assertEquals(2, result.size(), "Має повернутися 2 користувачі");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getCurrentUserFinances_WhenUserExists_ShouldReturnFinances() {
        // 1. ARRANGE
        User testUser = new User("Степан", "Бандера");
        Finances testFinances = new Finances();
        testUser.setFinances(testFinances);

        // Симулюємо, що користувач залогінився
        userService.setCurrentUser(testUser);

        // 2. ACT
        Finances result = userService.getCurrentUserFinances();

        // 3. ASSERT
        assertEquals(testFinances, result, "Мають повернутися фінанси поточного користувача");
    }

    @Test
    void getCurrentUserFinances_WhenUserIsNull_ShouldThrowException() {
        // 1. ARRANGE
        userService.setCurrentUser(null); // Спеціально робимо null, щоб зайти в гілку if

        // 2 & 3. ACT & ASSERT
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.getCurrentUserFinances();
        });

        assertEquals("The user doesn't exist!", exception.getMessage());
    }

    @Test
    void saveProgress_WhenUserExists_ShouldCallRepositorySave() {
        // 1. ARRANGE
        User testUser = new User("Test", "User");
        userService.setCurrentUser(testUser);

        // 2. ACT
        userService.saveProgress();

        // 3. ASSERT
        verify(userRepository, times(1)).save(testUser);
    }
}