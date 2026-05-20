package com.automationpractice.repository;

import com.automationpractice.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByCompletedFalse();
    List<TodoItem> findByCompletedTrue();
    List<TodoItem> findByPriority(TodoItem.Priority priority);
    List<TodoItem> findByAssignedTo(String assignedTo);
    List<TodoItem> findByTitleContainingIgnoreCase(String title);
}
