import axios from 'axios';
import { Todo } from '../types/Todo';

const API_URL = 'http://localhost:8080/api/todos';

export const TodoService = {
  getAllTodos: async () => {
    const response = await axios.get<Todo[]>(API_URL);
    return response.data;
  },

  getTodoById: async (id: number) => {
    const response = await axios.get<Todo>(`${API_URL}/${id}`);
    return response.data;
  },

  createTodo: async (todo: Todo) => {
    const response = await axios.post<Todo>(API_URL, todo);
    return response.data;
  },

  updateTodo: async (id: number, todo: Todo) => {
    const response = await axios.put<Todo>(`${API_URL}/${id}`, todo);
    return response.data;
  },

  deleteTodo: async (id: number) => {
    await axios.delete(`${API_URL}/${id}`);
  }
}; 