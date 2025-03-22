package com.example.controller;

import com.example.dto.ApiEndpointDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WelcomeController {
    @GetMapping("/api/endpoints")
    public List<ApiEndpointDto> getApiEndpoints() {
        return List.of(
            new ApiEndpointDto("/api/todos", "GET", "Retrieve all todos", false),
            new ApiEndpointDto("/api/todos", "POST", "Create a new todo", true),
            new ApiEndpointDto("/api/todos/{id}", "GET", "Retrieve a specific todo", true),
            new ApiEndpointDto("/api/todos/{id}", "PUT", "Update a specific todo", true),
            new ApiEndpointDto("/api/todos/{id}", "DELETE", "Delete a specific todo", true)
        );
    }

    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "   <meta charset='UTF-8'>" +
               "   <title>Welcome to Todo Application</title>" +
               "   <style>" +
               "       body { font-family: Arial, sans-serif; max-width: 800px; margin: 0 auto; padding: 20px; }" +
               "       h1 { color: #333; }" +
               "       ul { list-style-type: none; padding: 0; }" +
               "       li { margin-bottom: 10px; }" +
               "       a { " +
               "           color: #0066cc; " +
               "           text-decoration: none; " +
               "           background-color: #f0f0f0; " +
               "           padding: 5px 10px; " +
               "           border-radius: 3px; " +
               "           display: inline-block; " +
               "           margin-right: 10px;" +
               "       }" +
               "       a:hover { background-color: #e0e0e0; }" +
               "       .method { " +
               "           font-weight: bold; " +
               "           margin-right: 10px;" +
               "       }" +
               "   </style>" +
               "</head>" +
               "<body>" +
               "   <h1>Welcome to Todo Application</h1>" +
               "   <h2>Available API Endpoints:</h2>" +
               "   <ul>" +
               "       <li>" +
               "           <span class='method'>GET</span>" +
               "           <a href='/api/todos'>api/todos</a> - Retrieve all todos" +
               "       </li>" +
               "       <li>" +
               "           <span class='method'>GET</span>" +
               "           <a href='/api/todos/1'>api/todos/{id}</a> - Retrieve a specific todo" +
               "       </li>" +
               "       <li>" +
               "           <span class='method'>POST</span>" +
               "           <a href='#' onclick='openPostModal()'>api/todos</a> - Create a new todo" +
               "       </li>" +
               "       <li>" +
               "           <span class='method'>PUT</span>" +
               "           <a href='#' onclick='openPutModal()'>api/todos/{id}</a> - Update an existing todo" +
               "       </li>" +
               "       <li>" +
               "           <span class='method'>DELETE</span>" +
               "           <a href='#' onclick='openDeleteModal()'>api/todos/{id}</a> - Delete a todo" +
               "       </li>" +
               "   </ul>" +
               "   <script>" +
               "       function openPostModal() {" +
               "           const todoData = prompt('Enter todo details (JSON format):', '{\"title\":\"New Todo\",\"description\":\"Todo description\",\"completed\":false}');" +
               "           if (todoData) {" +
               "               fetch('/api/todos', {" +
               "                   method: 'POST'," +
               "                   headers: { 'Content-Type': 'application/json' }," +
               "                   body: todoData" +
               "               })" +
               "               .then(response => response.json())" +
               "               .then(data => alert(JSON.stringify(data)))" +
               "               .catch(error => alert('Error: ' + error));" +
               "           }" +
               "       }" +
               "       function openPutModal() {" +
               "           const todoId = prompt('Enter todo ID to update:');" +
               "           const todoData = prompt('Enter updated todo details (JSON format):', '{\"title\":\"Updated Todo\",\"description\":\"Updated description\",\"completed\":true}');" +
               "           if (todoId && todoData) {" +
               "               fetch(`/api/todos/${todoId}`, {" +
               "                   method: 'PUT'," +
               "                   headers: { 'Content-Type': 'application/json' }," +
               "                   body: todoData" +
               "               })" +
               "               .then(response => response.json())" +
               "               .then(data => alert(JSON.stringify(data)))" +
               "               .catch(error => alert('Error: ' + error));" +
               "           }" +
               "       }" +
               "       function openDeleteModal() {" +
               "           const todoId = prompt('Enter todo ID to delete:');" +
               "           if (todoId) {" +
               "               fetch(`/api/todos/${todoId}`, { method: 'DELETE' })" +
               "               .then(response => {" +
               "                   if (response.ok) alert('Todo deleted successfully');" +
               "                   else throw new Error('Delete failed');" +
               "               })" +
               "               .catch(error => alert('Error: ' + error));" +
               "           }" +
               "       }" +
               "   </script>" +
               "</body>" +
               "</html>";
    }
} 