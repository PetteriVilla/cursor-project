package com.example.service;

import com.example.model.Todo;
import com.example.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo(1L, "Test Todo", "Description", false);
    }

    @Test
    void testGetAllTodos() {
        when(todoRepository.findAll()).thenReturn(List.of(todo));
        
        List<Todo> todos = todoService.getAllTodos();
        
        assertNotNull(todos);
        assertEquals(1, todos.size());
        verify(todoRepository).findAll();
    }

    @Test
    void testGetTodoById() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        
        Optional<Todo> foundTodo = todoService.getTodoById(1L);
        
        assertTrue(foundTodo.isPresent());
        assertEquals(todo, foundTodo.get());
        verify(todoRepository).findById(1L);
    }

    @Test
    void testCreateTodo() {
        when(todoRepository.save(todo)).thenReturn(todo);
        
        Todo createdTodo = todoService.createTodo(todo);
        
        assertNotNull(createdTodo);
        assertEquals(todo, createdTodo);
        verify(todoRepository).save(todo);
    }
} 