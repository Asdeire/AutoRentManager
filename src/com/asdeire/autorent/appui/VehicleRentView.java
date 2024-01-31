package com.asdeire.autorent.appui;

import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;

/**
 * The {@code VehicleRentView} class represents the user interface for renting vehicles in the car rental application.
 * It provides methods for rendering the vehicle rental view, obtaining user input for the rental duration, confirming
 * the rental choice, and displaying vehicle details.
 *
 * @author Asdeire
 * @version 1.0
 */
public class VehicleRentView {

    private final User user;
    private final ReviewRepository reviewRepository;
    private final CategoryView categoryView;

    /**
     * Constructs a new instance of {@code VehicleRentView} with the specified user, review repository, and category view.
     *
     * @param user             The user renting the vehicle.
     * @param reviewRepository The repository for managing reviews.
     * @param categoryView     The category view associated with the rental.
     */
    public VehicleRentView(User user, ReviewRepository reviewRepository, CategoryView categoryView) {
        this.user = user;
        this.reviewRepository = reviewRepository;
        this.categoryView = categoryView;
    }

    /**
     * Renders the vehicle rental view for the specified vehicle.
     *
     * @param vehicle The vehicle selected for rental.
     * @throws IOException If an I/O error occurs during rendering.
     */
    public void render(Vehicle vehicle) throws IOException {
        clearScreen();
        AuthView.printWelcome();
        System.out.println("Ви вибрали:");
        displayVehicleDetails(vehicle);
        System.out.println("Ваш баланс: " + user.getBalance() + "$");
        getNumberOfDays(vehicle);
    }

    /**
     * Obtains the number of rental days from the user and calculates the total cost.
     *
     * @param vehicle The vehicle selected for rental.
     * @throws IOException If an I/O error occurs during input.
     */
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

    /**
     * Confirms the user's choice to rent the vehicle and adjusts the user's balance accordingly.
     *
     * @param totalCost The total cost of the rental.
     * @param vehicle   The vehicle selected for rental.
     * @throws IOException If an I/O error occurs during input.
     */
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

    /**
     * Displays details of the selected vehicle.
     *
     * @param vehicle The vehicle for which details will be displayed.
     */
    private void displayVehicleDetails(Vehicle vehicle) {
        System.out.println("Name: " + vehicle.getName());
        System.out.println("Price: " + vehicle.getPrice());
        System.out.println("Description: " + vehicle.getDescription());
        System.out.println("Category: " + vehicle.getCategory().getName());
        System.out.println("--------------");
    }

    /**
     * Clears the console screen.
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
