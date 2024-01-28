package com.asdeire.autorent.domain.impl;

import static com.asdeire.autorent.persistence.entity.impl.Vehicle.readVehiclesFromJsonFile;

import com.asdeire.autorent.Startup;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class CategoryService {

    Path filePath = Path.of("data/vehicles.json");
    List<Vehicle> vehicles = readVehiclesFromJsonFile(filePath);
    private User user;

    private ReviewRepository reviewRepository;

    public void setUserCategory(User user){
        this.user = user;
    }

    public void setReviewRepository(
        ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void chooseCategory() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Виберіть категорію:");
        System.out.println("1. SUV:");
        System.out.println("2. Hatchback:");
        System.out.println("3. Sedan:");
        System.out.println("4. EXIT");

        String inputCategory = "Sedan";

        try {
            int choice = Integer.valueOf(scanner.nextLine());
            switch (choice) {
                case 1 -> inputCategory = "SUV";
                case 2 -> inputCategory = "Hatchback";
                case 3 -> inputCategory = "Sedan";
                case 4 -> Startup.init();
                default -> {
                    System.out.print("\033[H\033[2J");
                    System.out.println("Виберіть один із пунктів меню");
                    Startup.init();
                }
            }
            System.out.print("\033[H\033[2J");
            System.out.println("Автомобілі категорії " + inputCategory);

            int vehicleNumber = 1;
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getCategory().getName().equalsIgnoreCase(inputCategory)) {
                    System.out.println(vehicleNumber + ". ");
                    displayVehicleDetails(vehicle);
                    vehicleNumber++;
                }
            }
            System.out.println("Виберіть автомобіль (введіть номер):");

            int selectedVehicleNumber = Integer.parseInt(scanner.nextLine());

            if (selectedVehicleNumber >= 1 && selectedVehicleNumber < vehicleNumber) {
                Vehicle selectedVehicle = getVehicleByNumber(inputCategory, selectedVehicleNumber);
                displayVehicleDetails(selectedVehicle);

                RentalService rentalService = new RentalService(user, reviewRepository);
                rentalService.rentVehicle(selectedVehicle);
                rentalService.leaveReview(selectedVehicle);
            } else {
                System.out.println("Невірний номер автомобіля. Будь ласка, оберіть знову.");
            }

        } catch (NumberFormatException e) {
            System.out.print("\033[H\033[2J");
            System.out.println("Виберіть один із пунктів меню");
            chooseCategory();
        }


    }

    private Vehicle getVehicleByNumber(String category, int selectedVehicleNumber) {
        int count = 1;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getCategory().getName().equalsIgnoreCase(category)) {
                if (count == selectedVehicleNumber) {
                    return vehicle;
                }
                count++;
            }
        }
        return null;
    }

    private void displayVehicleDetails(Vehicle vehicle) {
        System.out.println("Name: " + vehicle.getName());
        System.out.println("Price: " + vehicle.getPrice() + "$/day");
        System.out.println("Description: " + vehicle.getDescription());
        System.out.println("Category: " + vehicle.getCategory().getName());
        System.out.println("--------------");
    }
}

