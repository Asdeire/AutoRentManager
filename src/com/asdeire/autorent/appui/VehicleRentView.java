package com.asdeire.autorent.appui;

import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;

public class VehicleRentView {

    private final User user;
    private final ReviewRepository reviewRepository;
    private final CategoryView categoryView;


    public VehicleRentView(User user, ReviewRepository reviewRepository, CategoryView categoryView) {
        this.user = user;
        this.reviewRepository = reviewRepository;
        this.categoryView = categoryView;
    }

    public void render(Vehicle vehicle) throws IOException {
        clearScreen();
        AuthView.printWelcome();
        System.out.println("Ви вибрали:");
        displayVehicleDetails(vehicle);
        System.out.println("Ваш баланс: " + user.getBalance() + "$");
        getNumberOfDays(vehicle);
    }

    private void getNumberOfDays(Vehicle vehicle) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createInputPrompt()
            .name("day-number")
            .message("Введіть на скільки днів ви бажаєте орендувати авто:")
            .addPrompt();

        var dayResult = prompt.prompt(promptBuilder.build());

        var dayInput = (InputResult) dayResult.get("day-number");

        try {
            int rentalDays = Integer.parseInt(dayInput.getInput());

            int totalCost = vehicle.getPrice() * rentalDays;

            if (user.getBalance() >= totalCost) {
                System.out.println("Загальна сума: $" + totalCost);
                confirmChoice(totalCost, vehicle);
            } else {
                System.out.println("Недостатньо коштів.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Потрібно ввести номер!");
            getNumberOfDays(vehicle);
        }
    }

    private void confirmChoice(int totalCost, Vehicle vehicle) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createInputPrompt()
            .name("choice")
            .message("Підтвердити?(Y/N): ")
            .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
        var resultInput = (InputResult) result.get("choice");
        String confirmation = resultInput.getInput().toUpperCase();

        if (confirmation.equalsIgnoreCase("Y")) {
            user.setBalance(user.getBalance() - totalCost);
            System.out.println("Підтверджено. Ваш залишок на балансі: $" + user.getBalance());
            ReviewView reviewView = new ReviewView(user, reviewRepository, categoryView);
            reviewView.render(vehicle);
        } else {
            clearScreen();
            AuthView.printWelcome();
            System.out.println("Оренду відмінено.");
            CategoryView category = categoryView.getCategoryView();
            category.render();
        }
    }

    private void displayVehicleDetails(Vehicle vehicle) {
        System.out.println("Name: " + vehicle.getName());
        System.out.println("Price: " + vehicle.getPrice());
        System.out.println("Description: " + vehicle.getDescription());
        System.out.println("Category: " + vehicle.getCategory().getName());
        System.out.println("--------------");
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
