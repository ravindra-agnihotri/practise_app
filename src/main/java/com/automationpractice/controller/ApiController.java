package com.automationpractice.controller;

import com.automationpractice.model.*;
import com.automationpractice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApiController {

    private final UserService userService;
    private final ProductService productService;
    private final ContactService contactService;
    private final TodoService todoService;

    // ===== HEALTH CHECK =====
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            asad
            "status", "UP",
            "app", "Automation Practice App",
            "version", "1.0.0",
            "timestamp", System.currentTimeMillis()
        ));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> stats() {
        return ResponseEntity.ok(Map.of(
            "users", userService.count(),
            "products", productService.count(),
            "contacts", contactService.count(),
            "todos", todoService.count()
        ));
    }

    // ===== USERS API =====
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody AppUser user) {
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        }
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already taken"));
        }
        AppUser created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody AppUser user) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return userService.getUserById(id).map(user -> {
            if (updates.containsKey("active")) user.setActive((Boolean) updates.get("active"));
            if (updates.containsKey("newsletterSubscribed")) user.setNewsletterSubscribed((Boolean) updates.get("newsletterSubscribed"));
            if (updates.containsKey("bio")) user.setBio((String) updates.get("bio"));
            return ResponseEntity.ok(userService.createUser(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isEmpty()) return ResponseEntity.notFound().build();
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ===== PRODUCTS API =====
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean featured) {
        if (search != null) return ResponseEntity.ok(productService.searchProducts(search));
        if (category != null) {
            try {
                return ResponseEntity.ok(productService.getProductsByCategory(Product.Category.valueOf(category.toUpperCase())));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        if (Boolean.TRUE.equals(featured)) return ResponseEntity.ok(productService.getFeaturedProducts());
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.getProductById(id).isEmpty()) return ResponseEntity.notFound().build();
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ===== TODOS API =====
    @GetMapping("/todos")
    public ResponseEntity<List<TodoItem>> getAllTodos(@RequestParam(required = false) Boolean completed) {
        if (Boolean.TRUE.equals(completed)) return ResponseEntity.ok(todoService.getCompletedTodos());
        if (Boolean.FALSE.equals(completed)) return ResponseEntity.ok(todoService.getPendingTodos());
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoItem> createTodo(@Valid @RequestBody TodoItem todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todo));
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoItem todo) {
        try {
            return ResponseEntity.ok(todoService.updateTodo(id, todo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/todos/{id}/toggle")
    public ResponseEntity<TodoItem> toggleTodo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(todoService.toggleComplete(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if (todoService.getTodoById(id).isEmpty()) return ResponseEntity.notFound().build();
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    // ===== CONTACTS API =====
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        return contactService.getContactById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/contacts")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.createContact(contact));
    }

    @PatchMapping("/contacts/{id}/status")
    public ResponseEntity<?> updateContactStatus(@PathVariable Long id,
                                                  @RequestBody Map<String, String> body) {
        try {
            Contact.Status status = Contact.Status.valueOf(body.get("status").toUpperCase());
            return ResponseEntity.ok(contactService.updateStatus(id, status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid status"));
        }
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (contactService.getContactById(id).isEmpty()) return ResponseEntity.notFound().build();
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
