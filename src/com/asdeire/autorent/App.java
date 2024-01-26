package com.asdeire.autorent;

import static com.asdeire.autorent.persistence.entity.impl.Vehicle.readVehiclesFromJsonFile;
import static java.lang.System.out;

import com.asdeire.autorent.domain.exception.SignUpException;
import com.asdeire.autorent.domain.impl.SignUpService;
import com.asdeire.autorent.persistence.entity.impl.Category;
import com.asdeire.autorent.persistence.entity.impl.User.Role;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.RepositoryFactory;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import com.asdeire.autorent.persistence.entity.impl.User;
import java.util.Set;
import java.util.UUID;

public class App {

    public static void main(String[] args) {
        /*RepositoryFactory jsonRepositoryFactory = RepositoryFactory
            .getRepositoryFactory(RepositoryFactory.JSON);
        UserRepository userRepository = jsonRepositoryFactory.getUserRepository();
        SignUpService signUpService = new SignUpService(userRepository);
        try {
            signUpService.signUp("User", "Pass1-word", "example@gmail.com", 1000,
                () -> {
                    System.out.print("Введіть код підтвердження: ");
                    Scanner scanner = new Scanner(System.in);
                    return scanner.nextLine();
                });
        } catch (SignUpException e) {
            System.err.println(e.getMessage());
        }

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();*/


        Path filePath = Path.of("data/vehicles.json");
        List<Vehicle> vehicles = readVehiclesFromJsonFile(filePath);

        if (vehicles != null) {
            // Виведення отриманих даних
            for (Vehicle vehicle : vehicles) {
                System.out.println("Name: " + vehicle.getName());
                System.out.println("Price: " + vehicle.getPrice());
                System.out.println("Description: " + vehicle.getDescription());
                System.out.println("Category: " + vehicle.getCategory().getName());
                System.out.println("--------------");
            }
        }
    }

}