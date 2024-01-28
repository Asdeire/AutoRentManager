package com.asdeire.autorent.domain.impl;

import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import java.util.Scanner;

public class RentalService {

    private final User user;
    private final Scanner scanner;

    public RentalService(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

    public void rentVehicle(Vehicle vehicle) {
        System.out.println("Ви вибрали:");
        displayVehicleDetails(vehicle);
        System.out.println("Ваш баланс: " + user.getBalance() + "$");
        System.out.println("Введіть на скільки днів ви бажаєте орендувати авто:");
        int rentalDays = Integer.parseInt(scanner.nextLine());

        int totalCost = vehicle.getPrice() * rentalDays;

        if (user.getBalance() >= totalCost) {
            System.out.println("Загальна сума: $" + totalCost);
            System.out.println("Підтвердити? (Y/N)");

            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                user.setBalance(user.getBalance() - totalCost);
                System.out.println("Підтверджено. Ваш залишок на балансі: $" + user.getBalance());
            } else {
                System.out.println("Оренду відмінено.");
            }
        } else {
            System.out.println("Недостатньо коштів.");
        }
    }

    private void displayVehicleDetails(Vehicle vehicle) {
        System.out.println("Name: " + vehicle.getName());
        System.out.println("Price: " + vehicle.getPrice());
        System.out.println("Description: " + vehicle.getDescription());
        System.out.println("Category: " + vehicle.getCategory().getName());
        System.out.println("--------------");
    }
}
