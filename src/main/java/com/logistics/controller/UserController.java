package com.logistics.controller;

import com.logistics.model.User;
import com.logistics.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 2. Display all existing users (для вибору в меню)
    // GET http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    // 1. Create a new user
    // POST http://localhost:8080/api/users
    // Тіло запиту (JSON): {"name": "Ivan", "surname": "Franko"}
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
        if (request.getName() == null || request.getSurname() == null) {
            return ResponseEntity.badRequest().body("Name and surname are required!");
        }

        User newUser = userService.CreateAndSaveUser(request.getName(), request.getSurname());

        // Встановлюємо поточного юзера, як у вашому консольному меню
        userService.setCurrentUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("The user " + newUser.getName() + " " + newUser.getSurname() + " was successfully created and logged in!");
    }

    // 2. Choose an existing user (Логін)
    // POST http://localhost:8080/api/users/login/{userId}
    @PostMapping("/login/{userId}")
    public ResponseEntity<String> loginUser(@PathVariable Long userId) {
        List<User> users = userService.getAllUsers();

        // Шукаємо юзера за ID серед тих, що є в базі
        User selectedUser = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElse(null);

        if (selectedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found! Please create a new one.");
        }

        // Встановлюємо активного юзера
        userService.setCurrentUser(selectedUser);

        // Тут у вашому консольному коді також було підтягування флоту та фінансів у менеджери
        // vehicleManager.setFleet(selectedUser.getFleet());
        // і т.д. Це варто додати у UserService при виклику setCurrentUser.

        return ResponseEntity.ok("The logging in was executed successfully! Welcome back, " + selectedUser.getName() + "!");
    }

    // Внутрішній DTO для приймання даних створення користувача
    static class UserRequest {
        private String name;
        private String surname;

        public UserRequest() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }
    }
}