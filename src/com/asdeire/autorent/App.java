package com.asdeire.autorent;

import static java.lang.System.out;

import com.asdeire.autorent.persistence.repository.RepositoryFactory;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import com.asdeire.autorent.persistence.entity.impl.User;
import java.util.Set;
import java.util.UUID;

public class App {

    public static void main(String[] args){

        Set<User> users = generateUsers(10);

        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
            .getRepositoryFactory(RepositoryFactory.JSON);
        UserRepository userRepository = jsonRepositoryFactory.getUserRepository();

        // Виведемо створених користувачів

        int i = 0;
        for (User user : users) {
            userRepository.add(user);
            if (i == 3) {
                userRepository.remove(user);
            }
            if (i == 5) {
                userRepository.remove(user);
            }
            if (i == 7) {
                userRepository.remove(user);
            }
            i++;
        }

        userRepository.findAll().forEach(out::println);

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();
    }

    public static Set<User> generateUsers(int count) {
        Set<User> users = new HashSet<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID userId = UUID.randomUUID();
            String password = faker.internet().password();
            String email = faker.internet().emailAddress();
            String username = faker.name().username();
            int balance = 10000;

            User user = new User(userId, password, username, email, balance);
            users.add(user);
        }

        return users;
    }
}
