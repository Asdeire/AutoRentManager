package com.asdeire.autorent.appui;

import static com.asdeire.autorent.appui.AuthView.AuthMenu.EXIT;
import static com.asdeire.autorent.appui.AuthView.AuthMenu.SIGN_IN;
import static com.asdeire.autorent.appui.AuthView.AuthMenu.SIGN_UP;

import com.asdeire.autorent.Startup;
import com.asdeire.autorent.domain.exception.AuthException;
import com.asdeire.autorent.domain.exception.SignUpException;
import com.asdeire.autorent.domain.impl.AuthServiceImpl;
import com.asdeire.autorent.domain.impl.CategoryService;
import com.asdeire.autorent.domain.impl.SignUpService;
import com.asdeire.autorent.persistence.repository.RepositoryFactory;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Supplier;
import org.fusesource.jansi.AnsiConsole;


public class AuthView implements Rendarable {

    private final AuthServiceImpl authServiceImpl;
    private final CategoryView categoryView;
    private final SignUpService signUpService;

    public AuthView(AuthServiceImpl authServiceImpl,
        CategoryView categoryView,
        SignUpService signUpService) {
        this.authServiceImpl = authServiceImpl;
        this.categoryView = categoryView;
        this.signUpService = signUpService;
    }

    public static void printWelcome() {
        String art = "    ___         __        ____             __ \n"
            + "   /   | __  __/ /_____  / __ \\___  ____  / /_\n"
            + "  / /| |/ / / / __/ __ \\/ /_/ / _ \\/ __ \\/ __/\n"
            + " / ___ / /_/ / /_/ /_/ / _, _/  __/ / / / /_  \n"
            + "/_/  |_\\__,_/\\__/\\____/_/ |_|\\___/_/ /_/\\__/  \n"
            + "\n";

        for (int i = 0; i < art.length(); i++) {
            System.out.print(art.charAt(i));
            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Supplier<String> getCodeFromUserInput() {
        return () -> {
            ConsolePrompt prompt = new ConsolePrompt();
            PromptBuilder promptBuilder = prompt.getPromptBuilder();
            promptBuilder.createInputPrompt()
                .name("code")
                .message("Введіть код підтвердження")
                .addPrompt();

            HashMap<String, ? extends PromtResultItemIF> userResult = null;
            try {
                userResult = prompt.prompt(promptBuilder.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            var codeInput = (InputResult) userResult.get("code");
            return codeInput.getInput();
        };
    }

    private void process(AuthMenu selectedItem) throws IOException {


        switch (selectedItem) {
            case SIGN_IN -> openAuth();
            case SIGN_UP -> openSignUp();
            case EXIT -> {}
            default -> {}
        }
    }

    @Override
    public void render() throws IOException {
        AnsiConsole.systemInstall();

        printWelcome();

        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createListPrompt()
            .name("auth-menu")
            .message("Оренда автомобілів")
            .newItem(SIGN_IN.toString()).text(SIGN_IN.getName()).add()
            .newItem(SIGN_UP.toString()).text(SIGN_UP.getName()).add()
            .newItem(EXIT.toString()).text(EXIT.getName()).add()
            .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
        ListResult resultItem = (ListResult) result.get("auth-menu");

        AuthMenu selectedItem = AuthMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void openAuth() {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        promptBuilder.createInputPrompt()
            .name("username")
            .message("Впишіть ваш логін: ")
            .addPrompt();
        promptBuilder.createInputPrompt()
            .name("password")
            .message("Впишіть ваш пароль: ")
            .mask('*')
            .addPrompt();

        HashMap<String, ? extends PromtResultItemIF> result = null;
        try {
            result = prompt.prompt(promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var usernameInput = (InputResult) result.get("username");
        var passwordInput = (InputResult) result.get("password");

        try {
            boolean authenticate = authServiceImpl.authenticate(
                usernameInput.getInput(),
                passwordInput.getInput()
            );
            clearScreen();
            printWelcome();
            System.out.printf(authServiceImpl.getUser().getUsername());
            categoryView.render();
        } catch (AuthException e) {
            clearScreen();
            System.err.println("Невірно введений пароль чи логін!");
            openAuth();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openSignUp() {
        clearScreen();
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        promptBuilder.createInputPrompt()
            .name("username")
            .message("Впишіть ваш логін: ")
            .addPrompt();
        promptBuilder.createInputPrompt()
            .name("password")
            .message("Впишіть ваш пароль: ")
            .mask('*')
            .addPrompt();
        promptBuilder.createInputPrompt()
            .name("email")
            .message("Впишіть ваш email: ")
            .addPrompt();

        HashMap<String, ? extends PromtResultItemIF> result = null;
        try {
            result = prompt.prompt(promptBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var usernameInput = (InputResult) result.get("username");
        var passwordInput = (InputResult) result.get("password");
        var emailInput = (InputResult) result.get("email");

        String username = usernameInput.getInput();
        String password = passwordInput.getInput();
        String email = emailInput.getInput();

        if(!signUpService.validatedPassword(password)){
            clearScreen();
            System.err.println("Пароль не відповдіє вимогам, спробуйте знову!");
            openAuth();
        }
        else {
            System.out.println("На вашу пошту надіслано код підтвердження");

            try {
                signUpService.signUp(username, password, email, 1000, getCodeFromUserInput());
                RepositoryFactory jsonRepositoryFactory = RepositoryFactory
                    .getRepositoryFactory(RepositoryFactory.JSON);
                jsonRepositoryFactory.commit();
                clearScreen();
                System.out.println("Користувача було успішно додано!");
                Startup.init();
            } catch (SignUpException e) {
                System.err.println("Пароль, має містити мінімум 8 символів, та масимум 32!");
                clearScreen();
                openSignUp();
            }
        }
    }

    enum AuthMenu {
        SIGN_IN("Авторизація"),
        SIGN_UP("Стоврити обліковий аккаунт"),
        EXIT("Вихід");

        private final String name;

        AuthMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
