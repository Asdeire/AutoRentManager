package com.asdeire.autorent.domain.impl;

import static com.asdeire.autorent.persistence.entity.impl.Vehicle.readVehiclesFromJsonFile;

import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class CategoryService {
    Path filePath = Path.of("data/vehicles.json");
    List<Vehicle> vehicles = readVehiclesFromJsonFile(filePath);

    public void chooseCategory() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть категорію для фільтрації:");
        String inputCategory = scanner.nextLine();

        System.out.println("Результати фільтрації за категорією " + inputCategory + ":");
        for (
            Vehicle vehicle : vehicles) {
            if (vehicle.getCategory().getName().equalsIgnoreCase(inputCategory)) {
                System.out.println("Name: " + vehicle.getName());
                System.out.println("Price: " + vehicle.getPrice());
                System.out.println("Description: " + vehicle.getDescription());
                System.out.println("Category: " + vehicle.getCategory().getName());
                System.out.println("--------------");
            }
        }
    }
}

