package com.asdeire.autorent;

import static com.asdeire.autorent.util.FileHandler.writeUsersToJsonFile;
import static java.lang.System.out;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.asdeire.autorent.persistence.entity.impl.User;
import java.util.UUID;

public class App {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        List<User> users = generateUsers(10);

        // Виведемо створених користувачів
        for (User user : users) {
            out.println(user);
        }

        writeUsersToJsonFile(users, "data/users.json");
    }

    public static List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>();
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
