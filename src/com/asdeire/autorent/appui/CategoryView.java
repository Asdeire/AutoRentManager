package com.asdeire.autorent.appui;

import static com.asdeire.autorent.appui.CategoryView.CategoryMenu.EXIT;
import static com.asdeire.autorent.appui.CategoryView.CategoryMenu.HATCHBACK;
import static com.asdeire.autorent.appui.CategoryView.CategoryMenu.SEDAN;
import static com.asdeire.autorent.appui.CategoryView.CategoryMenu.SUV;
import static com.asdeire.autorent.persistence.entity.impl.Vehicle.readVehiclesFromJsonFile;

import com.asdeire.autorent.Startup;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.Vehicle;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import org.fusesource.jansi.AnsiConsole;

/**
 * The {@code CategoryView} class represents the user interface for viewing and selecting vehicle categories in the car rental application.
 * It provides methods for rendering the category menu, displaying details of vehicles in a selected category, and handling user input
 * to initiate the vehicle rental process.
 *
 * @author Asdeire
 * @version 1.0
 */
public class CategoryView implements Rendarable {

    Path filePath = Path.of("data/vehicles.json");
    List<Vehicle> vehicles = readVehiclesFromJsonFile(filePath);
    private User user;
    private ReviewRepository reviewRepository;
    private CategoryView categoryView;

    public CategoryView() {
    }

    public void setUserCategory(User user) {
        this.user = user;
    }

    public void setCategoryView(CategoryView categoryView){this.categoryView = categoryView;}

    public CategoryView getCategoryView() {
        return categoryView;
    }

    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Processes the selected item from the category menu and displays details of vehicles in the selected category.
     *
     * @param selectedItem   The selected item from the category menu.
     * @param inputCategory  The category selected by the user.
     * @throws IOException  If an I/O error occurs.
     */
    private void process(CategoryMenu selectedItem, String inputCategory) throws IOException {
        switch (selectedItem) {
            case SUV -> inputCategory = "SUV";
            case HATCHBACK -> inputCategory = "Hatchback";
            case SEDAN -> inputCategory = "Sedan";
            case EXIT -> Startup.init();
        }
        clearScreen();
        AuthView.printWelcome();
        System.out.println("Автомобілі категорії " + inputCategory);

        int vehicleNumber = 1;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getCategory().getName().equalsIgnoreCase(inputCategory)) {
                System.out.println(vehicleNumber + ". ");
                displayVehicleDetails(vehicle);
                vehicleNumber++;
            }
        }

        getCarByNumber(vehicleNumber, inputCategory, selectedItem);
    }

    /**
     * Renders the category menu for selecting a vehicle category.
     *
     * @throws IOException If an I/O error occurs during rendering.
     */
    @Override
    public void render() throws IOException {
        AnsiConsole.systemInstall();

        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createListPrompt()
            .name("category-menu")
            .message("Вибір категорії")
            .newItem(SEDAN.toString()).text(SEDAN.getName()).add()
            .newItem(SUV.toString()).text(SUV.getName()).add()
            .newItem(HATCHBACK.toString()).text(HATCHBACK.getName()).add()
            .newItem(EXIT.toString()).text(EXIT.getName()).add()
            .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
        ListResult resultItem = (ListResult) result.get("category-menu");

        CategoryMenu selectedItem = CategoryMenu.valueOf(resultItem.getSelectedId());
        String inputCategory = null;
        process(selectedItem, inputCategory);
    }

    /**
     * Displays details of a given vehicle.
     *
     * @param vehicle  The vehicle for which details will be displayed.
     */
    private void displayVehicleDetails(Vehicle vehicle) {
        System.out.println("Name: " + vehicle.getName());
        System.out.println("Price: " + vehicle.getPrice() + "$/day");
        System.out.println("Description: " + vehicle.getDescription());
        System.out.println("Category: " + vehicle.getCategory().getName());
        System.out.println("--------------");
    }

    /**
     * Initiates the vehicle rental process based on the user's selected vehicle number.
     *
     * @param vehicleNumber  The total number of vehicles in the selected category.
     * @param inputCategory  The category selected by the user.
     * @param selectedItem   The selected item from the category menu.
     * @throws IOException  If an I/O error occurs.
     */
    private void getCarByNumber(int vehicleNumber, String inputCategory, CategoryMenu selectedItem)
        throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        promptBuilder.createInputPrompt()
            .name("car")
            .message("Виберіть автомобіль (введіть номер):")
            .addPrompt();

        HashMap<String, ? extends PromtResultItemIF> carResult = null;
        try {
            carResult = prompt.prompt(promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var carInput = (InputResult) carResult.get("car");
        try {
            int selectedVehicleNumber = Integer.parseInt(carInput.getInput());

            if (selectedVehicleNumber >= 1 && selectedVehicleNumber < vehicleNumber) {
                Vehicle selectedVehicle = getVehicleByNumber(inputCategory, selectedVehicleNumber);

                VehicleRentView vehicleRentView = new VehicleRentView(user, reviewRepository, categoryView);
                vehicleRentView.render(selectedVehicle);
            } else {
                System.out.println("Невірний номер автомобіля. Будь ласка, оберіть знову.");
                process(selectedItem, inputCategory);
            }
        }catch (NumberFormatException e){
            System.err.println("Потрібно ввести номер!");
            process(selectedItem, inputCategory);
        }
    }


    /**
     * Retrieves a vehicle based on its number in the specified category.
     *
     * @param category               The category of vehicles.
     * @param selectedVehicleNumber The selected number of the vehicle.
     * @return                       The selected vehicle.
     */
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

    /**
     * Clears the console screen.
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Enumeration representing the category menu options.
     */
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
