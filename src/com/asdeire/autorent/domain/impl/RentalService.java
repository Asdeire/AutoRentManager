package com.asdeire.autorent.domain.impl;

import com.asdeire.autorent.persistence.entity.impl.Category;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import java.util.List;
import java.util.Scanner;

public class RentalService {

    public static void displayCategories(List<Vehicle> vehicles) {
        System.out.println("Available Categories:");
        for (Vehicle vehicle : vehicles) {
            Category category = vehicle.getCategory();
            System.out.println(category.getName());
        }
    }

    public static void displayDataForCategory(List<Vehicle> vehicles, Category selectedCategory) {
        System.out.println("Vehicles in Category: " + selectedCategory.getName());
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getCategory().equals(selectedCategory)) {
                System.out.println("Name: " + vehicle.getName());
                System.out.println("Price: " + vehicle.getPrice());
                System.out.println("Description: " + vehicle.getDescription());
                System.out.println();
            }
        }
    }

    public static Category selectCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the category: ");
        String categoryName = scanner.nextLine();
        return new Category(categoryName);
    }
}
