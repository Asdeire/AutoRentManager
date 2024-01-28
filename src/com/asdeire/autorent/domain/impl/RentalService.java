package com.asdeire.autorent.domain.impl;

import com.asdeire.autorent.persistence.entity.impl.Review;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import java.util.Scanner;
import java.util.UUID;

public class RentalService {

    private final User user;
    private final Scanner scanner;

    private final ReviewRepository reviewRepository;

    public RentalService(User user, ReviewRepository reviewRepository) {
        this.user = user;
        this.scanner = new Scanner(System.in);
        this.reviewRepository = reviewRepository;
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

    public void leaveReview(Vehicle vehicle) {
        System.out.println("Бажаєте залишити відгук про авто? (Y/N)");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Введіть ваш відгук:");
            String feedback = scanner.nextLine();

            Review review = new Review(UUID.randomUUID() , vehicle.getName(), user.getUsername(), feedback);
            reviewRepository.add(review);

            System.out.println("Ваш відгук успішно залишено!");
        }
        else {System.out.println("Ny i ne treba");}
    }
}
