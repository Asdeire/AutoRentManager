package com.asdeire.autorent.appui;

import static com.asdeire.autorent.appui.CategoryView.CategoryMenu.EXIT;
import static com.asdeire.autorent.appui.CategoryView.CategoryMenu.HATCHBACK;
import static com.asdeire.autorent.appui.CategoryView.CategoryMenu.SEDAN;
import static com.asdeire.autorent.appui.CategoryView.CategoryMenu.SUV;
import static com.asdeire.autorent.persistence.entity.impl.Vehicle.readVehiclesFromJsonFile;

import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.fusesource.jansi.AnsiConsole;

public class CategoryView implements Rendarable{

    Path filePath = Path.of("data/vehicles.json");
    List<Vehicle> vehicles = readVehiclesFromJsonFile(filePath);
    private User user;
    private ReviewRepository reviewRepository;

    public CategoryView(){}

    public void setUserCategory(User user){
        this.user = user;
    }

    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    private void process(CategoryMenu selectedItem) throws IOException {
        switch (selectedItem) {
            case SEDAN -> {}
            case SUV -> {}
            case HATCHBACK -> {}
            case EXIT -> {}
            default -> {}
        }
    }

    @Override
    public void render() throws IOException {
        AnsiConsole.systemInstall();

        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createListPrompt()
            .name("category-menu")
            .message("\nОренда автомобілів")
            .newItem(SEDAN.toString()).text(SEDAN.getName()).add()
            .newItem(SUV.toString()).text(SUV.getName()).add()
            .newItem(HATCHBACK.toString()).text(HATCHBACK.getName()).add()
            .newItem(EXIT.toString()).text(EXIT.getName()).add()
            .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
        ListResult resultItem = (ListResult) result.get("category-menu");

        CategoryMenu selectedItem = CategoryMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    enum CategoryMenu {
        SEDAN("Sedan"),
        SUV("SUV"),
        HATCHBACK("Hatchback"),
        EXIT("Вихід");

        private final String name;

        CategoryMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
