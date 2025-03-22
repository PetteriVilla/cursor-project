package com.example.controller;

import com.example.model.Todo;
import com.example.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public Object getAllTodos(@RequestHeader(value = HttpHeaders.ACCEPT, required = false) String acceptHeader) {
        List<Todo> todos = todoService.getAllTodos();
        
        // If request is from a browser (accepts text/html)
        if (acceptHeader != null && acceptHeader.contains(MediaType.TEXT_HTML_VALUE)) {
            return generateHtmlTable(todos);
        }
        
        // Otherwise, return JSON
        return todos;
    }

    private String generateHtmlTable(List<Todo> todos) {
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "   <meta charset='UTF-8'>" +
               "   <title>Todo List</title>" +
               "   <style>" +
               "       * { box-sizing: border-box; }" +
               "       body { " +
               "           font-family: Arial, sans-serif; " +
               "           max-width: 1000px; " +
               "           margin: 0 auto; " +
               "           padding: 20px; " +
               "           line-height: 1.6;" +
               "       }" +
               "       table { " +
               "           width: 100%; " +
               "           border-collapse: collapse; " +
               "           margin-top: 20px; " +
               "       }" +
               "       th, td { " +
               "           border: 1px solid #ddd; " +
               "           padding: 12px; " +
               "           text-align: left; " +
               "       }" +
               "       th { " +
               "           background-color: #f2f2f2; " +
               "           font-weight: bold;" +
               "       }" +
               "       .completed { " +
               "           text-decoration: line-through; " +
               "           color: #888; " +
               "       }" +
               "       .back-button-container {" +
               "           margin-bottom: 20px;" +
               "       }" +
               "       .back-button { " +
               "           display: inline-block; " +
               "           padding: 10px 20px; " +
               "           background-color: #007bff; " +
               "           color: white; " +
               "           text-decoration: none; " +
               "           border-radius: 5px; " +
               "           transition: background-color 0.3s ease;" +
               "           border: none;" +
               "           cursor: pointer;" +
               "       }" +
               "       .back-button:hover { " +
               "           background-color: #0056b3; " +
               "       }" +
               "   </style>" +
               "</head>" +
               "<body>" +
               "   <div class='back-button-container'>" +
               "       <a href='/' class='back-button'>← Back to Home</a>" +
               "   </div>" +
               "   <h1>Todo List</h1>" +
               "   <table>" +
               "       <thead>" +
               "           <tr>" +
               "               <th>ID</th>" +
               "               <th>Title</th>" +
               "               <th>Description</th>" +
               "               <th>Completed</th>" +
               "           </tr>" +
               "       </thead>" +
               "       <tbody>" +
               generateTableRows(todos) +
               "       </tbody>" +
               "   </table>" +
               "</body>" +
               "</html>";
    }

    private String generateTableRows(List<Todo> todos) {
        return todos.stream()
            .map(todo -> 
                "           <tr>" +
                "               <td>" + (todo.getId() != null ? todo.getId() : "") + "</td>" +
                "               <td class='" + (todo.isCompleted() ? "completed" : "") + "'>" + 
                    (todo.getTitle() != null ? todo.getTitle() : "") + "</td>" +
                "               <td>" + (todo.getDescription() != null ? todo.getDescription() : "") + "</td>" +
                "               <td>" + (todo.isCompleted() ? "✓" : "✗") + "</td>" +
                "           </tr>"
            )
            .collect(Collectors.joining("\n"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Todo updatedTodo = todoService.updateTodo(id, todoDetails);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
} 