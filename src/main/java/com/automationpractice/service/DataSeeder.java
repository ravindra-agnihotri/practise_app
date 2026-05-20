package com.automationpractice.service;

import com.automationpractice.model.*;
import com.automationpractice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ContactRepository contactRepository;
    private final TodoRepository todoRepository;

    @Override
    public void run(String... args) {
        seedUsers();
        seedProducts();
        seedContacts();
        seedTodos();
        log.info("✅ Sample data seeded successfully!");
    }

    private void seedUsers() {
        if (userRepository.count() > 0) return;
        List<AppUser> users = List.of(
            AppUser.builder().firstName("Alice").lastName("Johnson").email("alice@example.com")
                .username("alicej").phoneNumber("+14155552671").dateOfBirth(LocalDate.of(1990, 3, 15))
                .gender(AppUser.Gender.FEMALE).country("USA").city("San Francisco").bio("QA Engineer passionate about automation.").active(true).newsletterSubscribed(true).build(),
            AppUser.builder().firstName("Bob").lastName("Smith").email("bob@example.com")
                .username("bobs").phoneNumber("+447911123456").dateOfBirth(LocalDate.of(1985, 7, 22))
                .gender(AppUser.Gender.MALE).country("UK").city("London").bio("Senior developer and automation enthusiast.").active(true).newsletterSubscribed(false).build(),
            AppUser.builder().firstName("Carol").lastName("Williams").email("carol@example.com")
                .username("carolw").phoneNumber("+919876543210").dateOfBirth(LocalDate.of(1995, 11, 8))
                .gender(AppUser.Gender.FEMALE).country("India").city("Pune").bio("Full-stack developer.").active(true).newsletterSubscribed(true).build(),
            AppUser.builder().firstName("David").lastName("Brown").email("david@example.com")
                .username("davidb").phoneNumber("+61412345678").dateOfBirth(LocalDate.of(1988, 5, 30))
                .gender(AppUser.Gender.MALE).country("Australia").city("Sydney").bio("Test architect.").active(false).newsletterSubscribed(false).build(),
            AppUser.builder().firstName("Eva").lastName("Martinez").email("eva@example.com")
                .username("evam").phoneNumber("+34612345678").dateOfBirth(LocalDate.of(1993, 9, 12))
                .gender(AppUser.Gender.FEMALE).country("Spain").city("Barcelona").bio("Mobile test engineer.").active(true).newsletterSubscribed(true).build()
        );
        userRepository.saveAll(users);
        log.info("Seeded {} users", users.size());
    }

    private void seedProducts() {
        if (productRepository.count() > 0) return;
        List<Product> products = List.of(
            Product.builder().name("Selenium WebDriver Pro").description("Advanced Selenium WebDriver framework for end-to-end testing. Includes parallel execution, cross-browser testing, and detailed reporting.").price(new BigDecimal("49.99")).stock(150).category(Product.Category.ELECTRONICS).rating(4.8).featured(true).available(true).imageUrl("https://picsum.photos/seed/selenium/400/300").build(),
            Product.builder().name("Cypress Testing Suite").description("Modern JavaScript-based end-to-end testing framework. Fast, reliable, and easy to debug.").price(new BigDecimal("39.99")).stock(200).category(Product.Category.ELECTRONICS).rating(4.7).featured(true).available(true).imageUrl("https://picsum.photos/seed/cypress/400/300").build(),
            Product.builder().name("Test Automation Handbook").description("Comprehensive guide covering UI automation, API testing, performance testing and CI/CD integration.").price(new BigDecimal("29.99")).stock(500).category(Product.Category.BOOKS).rating(4.9).featured(false).available(true).imageUrl("https://picsum.photos/seed/book1/400/300").build(),
            Product.builder().name("API Testing Masterclass").description("Learn REST API testing with Postman, RestAssured and Newman. Includes OAuth, JWT and GraphQL testing.").price(new BigDecimal("24.99")).stock(350).category(Product.Category.BOOKS).rating(4.6).featured(true).available(true).imageUrl("https://picsum.photos/seed/book2/400/300").build(),
            Product.builder().name("Automation Laptop Stand").description("Ergonomic aluminum laptop stand perfect for your home QA lab setup.").price(new BigDecimal("34.99")).stock(75).category(Product.Category.HOME_GARDEN).rating(4.3).featured(false).available(true).imageUrl("https://picsum.photos/seed/stand/400/300").build(),
            Product.builder().name("Mechanical Keyboard for Testers").description("Tactile mechanical keyboard with programmable macros for faster test script writing.").price(new BigDecimal("89.99")).stock(60).category(Product.Category.ELECTRONICS).rating(4.5).featured(true).available(true).imageUrl("https://picsum.photos/seed/keyboard/400/300").build(),
            Product.builder().name("QA Engineer T-Shirt").description("Comfortable cotton T-shirt with 'It works on my machine' print. Available in multiple sizes.").price(new BigDecimal("19.99")).stock(300).category(Product.Category.CLOTHING).rating(4.2).featured(false).available(true).imageUrl("https://picsum.photos/seed/tshirt/400/300").build(),
            Product.builder().name("Stress Ball - Bug Squisher").description("Relieve test failure stress with this squishy bug-shaped stress ball.").price(new BigDecimal("9.99")).stock(0).category(Product.Category.TOYS).rating(4.0).featured(false).available(false).imageUrl("https://picsum.photos/seed/stress/400/300").build(),
            Product.builder().name("JMeter Performance Kit").description("Complete JMeter setup guide with scripts for performance and load testing.").price(new BigDecimal("19.99")).stock(120).category(Product.Category.ELECTRONICS).rating(4.4).featured(false).available(true).imageUrl("https://picsum.photos/seed/jmeter/400/300").build(),
            Product.builder().name("Playwright Advanced Course").description("Master Playwright for modern web testing. Covers TypeScript, network interception, and visual testing.").price(new BigDecimal("44.99")).stock(180).category(Product.Category.BOOKS).rating(4.8).featured(true).available(true).imageUrl("https://picsum.photos/seed/playwright/400/300").build()
        );
        productRepository.saveAll(products);
        log.info("Seeded {} products", products.size());
    }

    private void seedContacts() {
        if (contactRepository.count() > 0) return;
        List<Contact> contacts = List.of(
            Contact.builder().name("John Doe").email("john@test.com").phone("+1234567890").subject("Test Framework Help").message("I need help setting up my first Selenium project. Can you point me to the right resources?").priority(Contact.Priority.HIGH).status(Contact.Status.NEW).build(),
            Contact.builder().name("Jane Smith").email("jane@test.com").phone("+0987654321").subject("API Testing Question").message("How do I handle OAuth 2.0 authentication in RestAssured? I'm struggling with the token refresh flow.").priority(Contact.Priority.MEDIUM).status(Contact.Status.IN_PROGRESS).build(),
            Contact.builder().name("Mike Wilson").email("mike@test.com").subject("Bug Report").message("Found a defect in the login page. The password field accepts empty spaces.").priority(Contact.Priority.URGENT).status(Contact.Status.RESOLVED).build(),
            Contact.builder().name("Sara Lee").email("sara@test.com").phone("+1122334455").subject("Feature Request").message("Would love to see parallel test execution examples in the next tutorial.").priority(Contact.Priority.LOW).status(Contact.Status.CLOSED).build(),
            Contact.builder().name("Tom Harris").email("tom@test.com").subject("General Inquiry").message("What's the best way to start learning test automation from scratch?").priority(Contact.Priority.MEDIUM).status(Contact.Status.NEW).build()
        );
        contactRepository.saveAll(contacts);
        log.info("Seeded {} contacts", contacts.size());
    }

    private void seedTodos() {
        if (todoRepository.count() > 0) return;
        List<TodoItem> todos = List.of(
            TodoItem.builder().title("Write Selenium login tests").description("Cover positive login, negative login (wrong pass, empty fields), and session expiry.").priority(TodoItem.Priority.HIGH).dueDate(LocalDate.now().plusDays(3)).assignedTo("Alice").tags("selenium,login,smoke").completed(false).build(),
            TodoItem.builder().title("API endpoint documentation").description("Document all REST endpoints using Swagger/OpenAPI spec.").priority(TodoItem.Priority.MEDIUM).dueDate(LocalDate.now().plusDays(7)).assignedTo("Bob").tags("api,documentation").completed(true).build(),
            TodoItem.builder().title("Set up CI/CD pipeline").description("Configure Jenkins pipeline to run tests on every pull request.").priority(TodoItem.Priority.CRITICAL).dueDate(LocalDate.now().plusDays(1)).assignedTo("Carol").tags("ci-cd,jenkins").completed(false).build(),
            TodoItem.builder().title("Refactor page object model").description("Separate page objects from test scripts and improve maintainability.").priority(TodoItem.Priority.MEDIUM).dueDate(LocalDate.now().plusDays(14)).assignedTo("Alice").tags("refactor,pom").completed(false).build(),
            TodoItem.builder().title("Write API regression suite").description("Create RestAssured tests for all CRUD endpoints.").priority(TodoItem.Priority.HIGH).dueDate(LocalDate.now().plusDays(5)).assignedTo("Bob").tags("api,regression,restassured").completed(false).build(),
            TodoItem.builder().title("Cross-browser testing setup").description("Configure tests to run on Chrome, Firefox, and Edge.").priority(TodoItem.Priority.MEDIUM).dueDate(LocalDate.now().plusDays(10)).assignedTo("Carol").tags("cross-browser,grid").completed(true).build(),
            TodoItem.builder().title("Update test data management").description("Replace hardcoded data with data-driven approach using Excel/CSV files.").priority(TodoItem.Priority.LOW).dueDate(LocalDate.now().plusDays(21)).assignedTo("David").tags("data-driven,refactor").completed(false).build()
        );
        todoRepository.saveAll(todos);
        log.info("Seeded {} todos", todos.size());
    }
}
