package com.asdeire.autorent.domain.impl;

import com.asdeire.autorent.domain.exception.AuthException;
import com.asdeire.autorent.domain.exception.UserAlreadyAuthException;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import java.util.Scanner;
import org.mindrot.bcrypt.BCrypt;


public class AuthService {

    private final UserRepository userRepository;
    private User user;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        // Перевіряємо, чи вже існує аутентифікований користувач
        if (user != null) {
            throw new UserAlreadyAuthException("Ви вже авторизувалися як: %s"
                .formatted(user.getUsername()));
        }

        User foundedUser = userRepository.findByUsername(username)
            .orElseThrow(AuthException::new);

        if (!BCrypt.checkpw(password, foundedUser.getPassword())) {
            return false;
        }

        user = foundedUser;
        return true;
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void logout() {
        if (user == null) {
            throw new UserAlreadyAuthException("Ви ще не автентифікавані.");
        }
        user = null;
    }

    public void openAuthService(AuthService authService){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть імя користувача:");
        String username = scanner.nextLine();
        System.out.println("Введіть пароль:");
        String password = scanner.nextLine();

        authService.authenticate(username, password);
    }
}
