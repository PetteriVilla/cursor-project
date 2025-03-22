import axios from 'axios';

const API_URL = '/api/todos';

export const TodoService = {
  /**
   * Fetch all todos
   * @returns {Promise<Array>}
   */
  getAllTodos: async () => {
    const response = await axios.get(API_URL);
    return response.data;
  },

  /**
   * Get a todo by its ID
   * @param {number} id 
   * @returns {Promise<Object>}
   */
  getTodoById: async (id) => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  },

  /**
   * Create a new todo
   * @param {Object} todo 
   * @returns {Promise<Object>}
   */
  createTodo: async (todo) => {
    const response = await axios.post(API_URL, todo);
    return response.data;
  },

  /**
   * Update an existing todo
   * @param {number} id 
   * @param {Object} todo 
   * @returns {Promise<Object>}
   */
  updateTodo: async (id, todo) => {
    const response = await axios.put(`${API_URL}/${id}`, todo);
    return response.data;
  },

  /**
   * Delete a todo
   * @param {number} id 
   * @returns {Promise<void>}
   */
  deleteTodo: async (id) => {
    await axios.delete(`${API_URL}/${id}`);
  }
}; 