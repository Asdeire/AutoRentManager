package com.asdeire.autorent.appui;

import com.asdeire.autorent.persistence.entity.impl.Review;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class ReviewView {

    private final User user;
    private final ReviewRepository reviewRepository;
    private final CategoryView categoryView;

    public ReviewView(User user, ReviewRepository reviewRepository, CategoryView categoryView) {
        this.user = user;
        this.reviewRepository = reviewRepository;
        this.categoryView = categoryView;
    }

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

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
