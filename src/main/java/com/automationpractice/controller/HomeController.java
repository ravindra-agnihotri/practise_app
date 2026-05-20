package com.automationpractice.controller;

import com.automationpractice.model.*;
import com.automationpractice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ProductService productService;
    private final ContactService contactService;
    private final TodoService todoService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("userCount", userService.count());
        model.addAttribute("productCount", productService.count());
        model.addAttribute("contactCount", contactService.count());
        model.addAttribute("todoCount", todoService.count());
        model.addAttribute("featuredProducts", productService.getFeaturedProducts());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/elements")
    public String elements() {
        return "elements";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if (error != null) model.addAttribute("error", "Invalid username or password");
        if (logout != null) model.addAttribute("message", "You have been logged out.");
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("todos", todoService.getAllTodos());
        model.addAttribute("contacts", contactService.getAllContacts());
        model.addAttribute("userCount", userService.count());
        model.addAttribute("productCount", productService.count());
        model.addAttribute("contactCount", contactService.count());
        model.addAttribute("pendingTodos", todoService.getPendingTodos().size());
        return "dashboard";
    }

    // ===== USERS =====
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new AppUser());
        return "users";
    }

    @PostMapping("/users")
    public String createUser(@Valid @ModelAttribute("newUser") AppUser user,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            return "users";
        }
        userService.createUser(user);
        redirectAttrs.addFlashAttribute("successMessage", "User created successfully!");
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        AppUser user = userService.getUserById(id).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("genders", AppUser.Gender.values());
        return "user-edit";
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("user") AppUser user,
                             BindingResult result,
                             RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) return "user-edit";
        userService.updateUser(id, user);
        redirectAttrs.addFlashAttribute("successMessage", "User updated successfully!");
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        userService.deleteUser(id);
        redirectAttrs.addFlashAttribute("successMessage", "User deleted.");
        return "redirect:/users";
    }

    // ===== PRODUCTS =====
    @GetMapping("/products")
    public String products(@RequestParam(required = false) String search,
                           @RequestParam(required = false) String category,
                           Model model) {
        if (search != null && !search.isBlank()) {
            model.addAttribute("products", productService.searchProducts(search));
            model.addAttribute("search", search);
        } else if (category != null && !category.isBlank()) {
            model.addAttribute("products", productService.getProductsByCategory(Product.Category.valueOf(category)));
            model.addAttribute("selectedCategory", category);
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }
        model.addAttribute("categories", Product.Category.values());
        model.addAttribute("newProduct", new Product());
        return "products";
    }

    @PostMapping("/products")
    public String createProduct(@Valid @ModelAttribute("newProduct") Product product,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("categories", Product.Category.values());
            return "products";
        }
        productService.createProduct(product);
        redirectAttrs.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/products";
    }

    // ===== CONTACT =====
    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("priorities", Contact.Priority.values());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContact(@Valid @ModelAttribute("contact") Contact contact,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("priorities", Contact.Priority.values());
            return "contact";
        }
        contactService.createContact(contact);
        redirectAttrs.addFlashAttribute("successMessage", "Message sent! We'll get back to you soon.");
        return "redirect:/contact";
    }

    // ===== TODOS =====
    @GetMapping("/todos")
    public String todos(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("priorities", TodoItem.Priority.values());
        model.addAttribute("pendingCount", todoService.getPendingTodos().size());
        model.addAttribute("completedCount", todoService.getCompletedTodos().size());
        return "todos";
    }

    @PostMapping("/todos")
    public String createTodo(@Valid @ModelAttribute("newTodo") TodoItem todo,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("todos", todoService.getAllTodos());
            model.addAttribute("priorities", TodoItem.Priority.values());
            return "todos";
        }
        todoService.createTodo(todo);
        redirectAttrs.addFlashAttribute("successMessage", "Todo added!");
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        todoService.toggleComplete(id);
        redirectAttrs.addFlashAttribute("successMessage", "Todo status updated!");
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        todoService.deleteTodo(id);
        redirectAttrs.addFlashAttribute("successMessage", "Todo deleted.");
        return "redirect:/todos";
    }

    // ===== FORMS PAGE (UI Automation Practice) =====
    @GetMapping("/forms")
    public String formsPage(Model model) {
        model.addAttribute("user", new AppUser());
        model.addAttribute("genders", AppUser.Gender.values());
        return "forms";
    }

    @PostMapping("/forms/submit")
    public String submitForm(@ModelAttribute AppUser user, RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("formData", user);
        redirectAttrs.addFlashAttribute("successMessage", "Form submitted successfully!");
        return "redirect:/forms";
    }

    // ===== TABLES PAGE =====
    @GetMapping("/tables")
    public String tablesPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("products", productService.getAllProducts());
        return "tables";
    }

    // ===== ALERTS & MODALS =====
    @GetMapping("/alerts")
    public String alertsPage() {
        return "alerts";
    }

    @GetMapping("/modals")
    public String modalsPage() {
        return "modals";
    }
}
