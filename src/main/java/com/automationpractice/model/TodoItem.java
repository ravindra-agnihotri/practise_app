package com.automationpractice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200)
    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "is_completed")
    private boolean completed;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "tags")
    private String tags;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (priority == null) priority = Priority.MEDIUM;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}
