package com.asdeire.autorent.appui;

import com.asdeire.autorent.persistence.entity.impl.Review;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.util.UUID;

/**
 * The {@code ReviewView} class represents the user interface for leaving reviews on vehicles in the car rental application.
 * It provides methods for rendering the review prompt, obtaining user input, and handling the process of leaving a review.
 *
 * @author Asdeire
 * @version 1.0
 */
public class ReviewView {

    private final User user;
    private final ReviewRepository reviewRepository;
    private final CategoryView categoryView;

    /**
     * Constructs a new instance of {@code ReviewView} with the specified user, review repository, and category view.
     *
     * @param user             The user leaving the review.
     * @param reviewRepository The repository for managing reviews.
     * @param categoryView     The category view associated with the review.
     */
    public ReviewView(User user, ReviewRepository reviewRepository, CategoryView categoryView) {
        this.user = user;
        this.reviewRepository = reviewRepository;
        this.categoryView = categoryView;
    }

    /**
     * Renders the review prompt for the specified vehicle.
     *
     * @param vehicle The vehicle for which the user is leaving a review.
     * @throws IOException If an I/O error occurs during rendering.
     */
    public void render(Vehicle vehicle) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createInputPrompt()
            .name("choice")
            .message("Бажаєте залишити відгук?(Y/N): ")
            .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
        var resultInput = (InputResult) result.get("choice");
        String confirmation = resultInput.getInput().toUpperCase();

        if (confirmation.equalsIgnoreCase("Y")) {
            leaveReview(vehicle);
        } else {
            clearScreen();
            AuthView.printWelcome();
            CategoryView category = categoryView.getCategoryView();
            category.render();
        }
    }

    /**
     * Initiates the process of leaving a review for the specified vehicle.
     *
     * @param vehicle The vehicle for which the user is leaving a review.
     * @throws IOException If an I/O error occurs during the review process.
     */
    private void leaveReview(Vehicle vehicle) throws IOException{
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createInputPrompt()
            .name("feedback")
            .message("Введіть ваш відгук: ")
            .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
        var resultInput = (InputResult) result.get("feedback");
        String feedback = resultInput.getInput();

        Review review = new Review(UUID.randomUUID(), vehicle.getName(), user.getUsername(),
            feedback);
        reviewRepository.add(review);
        clearScreen();
        System.out.println("Ваш відгук успішно залишено!\n");
        categoryView.render();
    }

    /**
     * Clears the console screen.
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
