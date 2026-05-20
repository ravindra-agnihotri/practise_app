package com.automationpractice.service;

import com.automationpractice.model.TodoItem;
import com.automationpractice.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoItem> getAllTodos() {
        return todoRepository.findAll();
    }

    public List<TodoItem> getPendingTodos() {
        return todoRepository.findByCompletedFalse();
    }

    public List<TodoItem> getCompletedTodos() {
        return todoRepository.findByCompletedTrue();
    }

    public Optional<TodoItem> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public TodoItem createTodo(TodoItem todo) {
        return todoRepository.save(todo);
    }

    public TodoItem updateTodo(Long id, TodoItem updated) {
        return todoRepository.findById(id).map(todo -> {
            todo.setTitle(updated.getTitle());
            todo.setDescription(updated.getDescription());
            todo.setCompleted(updated.isCompleted());
            todo.setPriority(updated.getPriority());
            todo.setDueDate(updated.getDueDate());
            todo.setAssignedTo(updated.getAssignedTo());
            todo.setTags(updated.getTags());
            return todoRepository.save(todo);
        }).orElseThrow(() -> new RuntimeException("Todo not found: " + id));
    }

    public TodoItem toggleComplete(Long id) {
        return todoRepository.findById(id).map(todo -> {
            todo.setCompleted(!todo.isCompleted());
            return todoRepository.save(todo);
        }).orElseThrow(() -> new RuntimeException("Todo not found: " + id));
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public long count() {
        return todoRepository.count();
    }
}
