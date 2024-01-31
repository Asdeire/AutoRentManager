package com.asdeire.autorent.domain.impl;

import com.asdeire.autorent.domain.exception.SignUpException;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.User.Role;
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
import java.util.UUID;
import java.util.function.Supplier;
import org.mindrot.bcrypt.BCrypt;

/**
 * The {@code SignUpService} class provides services related to user sign-up and verification in the car rental application.
 * It includes methods for generating and sending verification codes, verifying user input codes, and handling user sign-up.
 *
 * @author Asdeire
 * @version 1.0
 */
public class SignUpService {

    private static final int VERIFICATION_CODE_EXPIRATION_MINUTES = 1;
    private static LocalDateTime codeCreationTime;
    private final UserRepository userRepository;

    /**
     * Constructs a new instance of {@code SignUpService} with the specified user repository.
     *
     * @param userRepository The repository for managing user data.
     */
    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Sends a verification code via email to the specified email address.
     *
     * @param email           The email address to send the verification code.
     * @param verificationCode The generated verification code.
     */
    private static void sendVerificationCodeEmail(String email, String verificationCode) {
        // Властивості для конфігурації підключення до поштового сервера
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port", "2525");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Отримання сесії з автентифікацією
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("f065a06337ce46", "8f09273124df7d");
            }
        });

        try {
            // Створення об'єкта MimeMessage
            Message message = new MimeMessage(session);

            // Встановлення відправника
            message.setFrom(
                new InternetAddress("kaoleksandr7.com")); // Замініть на власну адресу

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

    /**
     * Generates and sends a verification code to the specified email address.
     *
     * @param email The email address for which the verification code is generated and sent.
     * @return The generated verification code.
     */
    public static String generateAndSendVerificationCode(String email) {
        // Генерація 6-значного коду
        String verificationCode = String.valueOf((int) (Math.random() * 900000 + 100000));

        sendVerificationCodeEmail(email, verificationCode);

        codeCreationTime = LocalDateTime.now();

        return verificationCode;
    }

    /**
     * Verifies the input verification code against the generated code and checks for expiration.
     *
     * @param inputCode      The user-input verification code.
     * @param generatedCode  The generated verification code.
     * @throws SignUpException If the code is expired or incorrect.
     */
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

    /**
     * Signs up a new user with the specified details.
     *
     * @param username           The username of the new user.
     * @param password           The password of the new user.
     * @param email              The email address of the new user.
     * @param balance            The initial balance of the new user.
     * @param waitForUserInput   A supplier for waiting for user input.
     */
    public void signUp(String username,
        String password,
        String email,
        int balance,
        Supplier<String> waitForUserInput) {
        signUp(username, password, email, balance, Role.GENERAL, waitForUserInput);
    }

    /**
     * Signs up a new user with the specified details and role.
     *
     * @param username           The username of the new user.
     * @param password           The password of the new user.
     * @param email              The email address of the new user.
     * @param balance            The initial balance of the new user.
     * @param role               The role of the new user.
     * @param waitForUserInput   A supplier for waiting for user input.
     */
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

    /**
     * Validates the password based on certain criteria.
     *
     * @param password The password to be validated.
     * @return {@code true} if the password is valid; {@code false} otherwise.
     */
    public boolean validatedPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

}