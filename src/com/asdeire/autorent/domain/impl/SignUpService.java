package com.asdeire.autorent.domain.impl;

import com.asdeire.autorent.domain.exception.SignUpException;
import com.asdeire.autorent.persistence.entity.ErrorTemplates;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.User.Role;
import com.asdeire.autorent.persistence.exception.EntityArgumentException;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.mindrot.bcrypt.BCrypt;

public class SignUpService {

    private static final int VERIFICATION_CODE_EXPIRATION_MINUTES = 1;
    private static LocalDateTime codeCreationTime;
    private final UserRepository userRepository;

    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // відправлення на пошту
    private static void sendVerificationCodeEmail(String email, String verificationCode) {
        // Властивості для конфігурації підключення до поштового сервера
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Замініть на власний
        properties.put("mail.smtp.port", "2525"); // Замініть на власний SMTP порт
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Отримання сесії з автентифікацією
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("9dfa2cf39bd542", "c6558f50b691d5");
            }
        });

        try {
            // Створення об'єкта MimeMessage
            Message message = new MimeMessage(session);

            // Встановлення відправника
            message.setFrom(
                new InternetAddress("oleksandr20032006@gmail.com")); // Замініть на власну адресу

            // Встановлення отримувача
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Встановлення теми
            message.setSubject("Код підтвердження");

            // Встановлення тексту повідомлення
            message.setText("Ваш код підтвердження: " + verificationCode);

            // Відправлення повідомлення
            Transport.send(message);

            System.out.println("Повідомлення успішно відправлено.");

        } catch (MessagingException e) {
            throw new RuntimeException(
                "Помилка при відправці електронного листа: " + e.getMessage());
        }
    }

    public static String generateAndSendVerificationCode(String email) {
        // Генерація 6-значного коду
        String verificationCode = String.valueOf((int) (Math.random() * 900000 + 100000));

        sendVerificationCodeEmail(email, verificationCode);

        codeCreationTime = LocalDateTime.now();

        return verificationCode;
    }

    // Перевірка введеного коду
    public static void verifyCode(String inputCode, String generatedCode) {
        LocalDateTime currentTime = LocalDateTime.now();
        long minutesElapsed = ChronoUnit.MINUTES.between(codeCreationTime, currentTime);

        if (minutesElapsed > VERIFICATION_CODE_EXPIRATION_MINUTES) {
            throw new SignUpException("Час верифікації вийшов. Спробуйте ще раз.");
        }

        if (!inputCode.equals(generatedCode)) {
            throw new SignUpException("Невірний код підтвердження.");
        }

        // Скидання часу створення коду
        codeCreationTime = null;
    }

    public void signUp(String username,
        String password,
        String email,
        int balance,
        Supplier<String> waitForUserInput) {
        signUp(username, password, email, balance, Role.GENERAL, waitForUserInput);
    }

    public void signUp(String username,
        String password,
        String email,
        int balance,
        Role role,
        Supplier<String> waitForUserInput) {
        try {
            String verificationCode = generateAndSendVerificationCode(email);
            String userInputCode = waitForUserInput.get();

            verifyCode(userInputCode, verificationCode);

            // Додаємо користувача, якщо перевірка успішна
            userRepository.add(
                new User(UUID.randomUUID(),
                    BCrypt.hashpw(password, BCrypt.gensalt()),
                    email,
                    username,
                    balance,
                    role)
            );

        } catch (Exception e) {
            throw new SignUpException("Помилка при збереженні користувача: %s"
                .formatted(e.getMessage()));
        }
    }

    private String validatedPassword(String password) {
        final String templateName = "паролю";

        if (password.length() < 8) {
            System.out.println("Пароль має містити мінімум 8 символів");
        }
        if (password.length() > 32) {
            System.out.println("Пароль має містити не більше 32 символів");
        }
        if (password.isEmpty()) {
            System.out.println("Будь ласка введіть пароль");
        }

        return password;
    }

    public void openSignUpService(SignUpService signUpService) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть імя користувача:");
        String username = scanner.nextLine();
        System.out.println("Введіть пароль:");
        String password = scanner.nextLine();
        System.out.println("Введіть email:");
        String email = scanner.nextLine();

        //String correctPassword = validatedPassword(password);

        try {
            signUpService.signUp(username, password, email, 1000,
                () -> {
                    System.out.print("Введіть код підтвердження: ");
                    return scanner.nextLine();
                });
        } catch (SignUpException e) {
            System.out.print("\033[H\033[2J");
            System.err.println(e.getMessage());
            openSignUpService(signUpService);
        }
    }
}