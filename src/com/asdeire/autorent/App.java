package com.asdeire.autorent;


import com.asdeire.autorent.domain.impl.AuthService;
import com.asdeire.autorent.domain.impl.CategoryService;
import com.asdeire.autorent.domain.impl.SignUpService;
import com.asdeire.autorent.persistence.repository.RepositoryFactory;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import java.util.Scanner;;

public class App {

    public static void main(String[] args) {

        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
            .getRepositoryFactory(RepositoryFactory.JSON);

        UserRepository userRepository = jsonRepositoryFactory.getUserRepository();
        AuthService authService = new AuthService(userRepository);
        SignUpService signUpService = new SignUpService(userRepository);
        CategoryService categoryService = new CategoryService();

        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Авторизація");
        System.out.println("2. Реєстрація");
        int choice = Integer.valueOf(scanner.nextLine());

        switch (choice) {
            case 1 -> authService.openAuthService(authService);
            case 2 -> signUpService.openSignUpService(signUpService);
            default -> {}
        }

        if (authService.isAuthenticated()){
            categoryService.chooseCategory();
        }

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();
    }
}