package com.automationpractice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email
    @Column(nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "Subject is required")
    @Column(nullable = false)
    private String subject;

    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 2000)
    @Column(nullable = false, length = 2000)
    private String message;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = Status.NEW;
        if (priority == null) priority = Priority.MEDIUM;
    }

    public enum Priority {
        LOW, MEDIUM, HIGH, URGENT
    }

    public enum Status {
        NEW, IN_PROGRESS, RESOLVED, CLOSED
    }
}
