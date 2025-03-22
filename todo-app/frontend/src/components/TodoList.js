import React, { useState, useEffect } from 'react';
import { Container, Row, Col, ListGroup, Button, Form, Modal, Alert } from 'react-bootstrap';
import { TodoService } from '../services/TodoService';

const TodoList = () => {
  const [todos, setTodos] = useState([]);
  const [newTodo, setNewTodo] = useState({ title: '', completed: false });
  const [showModal, setShowModal] = useState(false);
  const [editingTodo, setEditingTodo] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const fetchedTodos = await TodoService.getAllTodos();
      setTodos(fetchedTodos);
      setError(null);
    } catch (error) {
      console.error('Error fetching todos:', error);
      setError('Failed to load todos. Please try again later.');
    }
  };

  const handleCreateTodo = async () => {
    if (!newTodo.title.trim()) {
      setError('Todo title cannot be empty');
      return;
    }

    try {
      const createdTodo = await TodoService.createTodo(newTodo);
      setTodos([...todos, createdTodo]);
      setNewTodo({ title: '', completed: false });
      setShowModal(false);
      setError(null);
    } catch (error) {
      console.error('Error creating todo:', error);
      setError('Failed to create todo. Please try again.');
    }
  };

  const handleUpdateTodo = async () => {
    if (editingTodo && editingTodo.id) {
      if (!editingTodo.title.trim()) {
        setError('Todo title cannot be empty');
        return;
      }

      try {
        const updatedTodo = await TodoService.updateTodo(editingTodo.id, editingTodo);
        setTodos(todos.map(todo => todo.id === updatedTodo.id ? updatedTodo : todo));
        setEditingTodo(null);
        setShowModal(false);
        setError(null);
      } catch (error) {
        console.error('Error updating todo:', error);
        setError('Failed to update todo. Please try again.');
      }
    }
  };

  const handleDeleteTodo = async (id) => {
    try {
      await TodoService.deleteTodo(id);
      setTodos(todos.filter(todo => todo.id !== id));
      setError(null);
    } catch (error) {
      console.error('Error deleting todo:', error);
      setError('Failed to delete todo. Please try again.');
    }
  };

  const handleToggleComplete = async (todo) => {
    if (todo.id) {
      try {
        const updatedTodo = await TodoService.updateTodo(todo.id, { ...todo, completed: !todo.completed });
        setTodos(todos.map(t => t.id === todo.id ? updatedTodo : t));
        setError(null);
      } catch (error) {
        console.error('Error toggling todo:', error);
        setError('Failed to update todo status. Please try again.');
      }
    }
  };

  const openEditModal = (todo) => {
    setEditingTodo(todo);
    setShowModal(true);
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col xs={12} md={8} lg={6}>
          <h1 className="text-center mb-4">Todo List</h1>
          
          {error && (
            <Alert variant="danger" onClose={() => setError(null)} dismissible>
              {error}
            </Alert>
          )}

          <Button 
            variant="primary" 
            onClick={() => { setEditingTodo(null); setShowModal(true); }} 
            className="mb-3 w-100"
          >
            Add New Todo
          </Button>

          {todos.length === 0 ? (
            <Alert variant="info">
              No todos yet. Click "Add New Todo" to get started!
            </Alert>
          ) : (
            <ListGroup>
              {todos.map(todo => (
                <ListGroup.Item 
                  key={todo.id} 
                  className="d-flex justify-content-between align-items-center"
                >
                  <div>
                    <Form.Check 
                      type="checkbox"
                      checked={todo.completed}
                      onChange={() => handleToggleComplete(todo)}
                      label={todo.title}
                      className={todo.completed ? 'text-muted text-decoration-line-through' : ''}
                    />
                  </div>
                  <div>
                    <Button 
                      variant="outline-info" 
                      size="sm" 
                      className="me-2" 
                      onClick={() => openEditModal(todo)}
                    >
                      Edit
                    </Button>
                    <Button 
                      variant="outline-danger" 
                      size="sm" 
                      onClick={() => todo.id && handleDeleteTodo(todo.id)}
                    >
                      Delete
                    </Button>
                  </div>
                </ListGroup.Item>
              ))}
            </ListGroup>
          )}

          <Modal show={showModal} onHide={() => setShowModal(false)}>
            <Modal.Header closeButton>
              <Modal.Title>{editingTodo ? 'Edit Todo' : 'Create New Todo'}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Form>
                <Form.Group>
                  <Form.Label>Title</Form.Label>
                  <Form.Control
                    type="text"
                    value={editingTodo ? editingTodo.title : newTodo.title}
                    onChange={(e) => editingTodo 
                      ? setEditingTodo({...editingTodo, title: e.target.value}) 
                      : setNewTodo({...newTodo, title: e.target.value})
                    }
                    placeholder="Enter todo title"
                  />
                </Form.Group>
                <Form.Group className="mt-2">
                  <Form.Label>Description (Optional)</Form.Label>
                  <Form.Control
                    type="text"
                    value={editingTodo?.description || newTodo.description || ''}
                    onChange={(e) => editingTodo 
                      ? setEditingTodo({...editingTodo, description: e.target.value}) 
                      : setNewTodo({...newTodo, description: e.target.value})
                    }
                    placeholder="Enter todo description"
                  />
                </Form.Group>
              </Form>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={() => setShowModal(false)}>
                Cancel
              </Button>
              <Button 
                variant="primary" 
                onClick={editingTodo ? handleUpdateTodo : handleCreateTodo}
              >
                {editingTodo ? 'Update' : 'Create'}
              </Button>
            </Modal.Footer>
          </Modal>
        </Col>
      </Row>
    </Container>
  );
};

export default TodoList; 