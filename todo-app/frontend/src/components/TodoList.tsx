import React, { useState, useEffect } from 'react';
import { Container, Row, Col, ListGroup, Button, Form, Modal } from 'react-bootstrap';
import { Todo } from '../types/Todo';
import { TodoService } from '../services/TodoService';

const TodoList: React.FC = () => {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [newTodo, setNewTodo] = useState<Todo>({ title: '', completed: false });
  const [showModal, setShowModal] = useState(false);
  const [editingTodo, setEditingTodo] = useState<Todo | null>(null);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const fetchedTodos = await TodoService.getAllTodos();
      setTodos(fetchedTodos);
    } catch (error) {
      console.error('Error fetching todos:', error);
    }
  };

  const handleCreateTodo = async () => {
    try {
      const createdTodo = await TodoService.createTodo(newTodo);
      setTodos([...todos, createdTodo]);
      setNewTodo({ title: '', completed: false });
      setShowModal(false);
    } catch (error) {
      console.error('Error creating todo:', error);
    }
  };

  const handleUpdateTodo = async () => {
    if (editingTodo && editingTodo.id) {
      try {
        const updatedTodo = await TodoService.updateTodo(editingTodo.id, editingTodo);
        setTodos(todos.map(todo => todo.id === updatedTodo.id ? updatedTodo : todo));
        setEditingTodo(null);
        setShowModal(false);
      } catch (error) {
        console.error('Error updating todo:', error);
      }
    }
  };

  const handleDeleteTodo = async (id: number) => {
    try {
      await TodoService.deleteTodo(id);
      setTodos(todos.filter(todo => todo.id !== id));
    } catch (error) {
      console.error('Error deleting todo:', error);
    }
  };

  const handleToggleComplete = async (todo: Todo) => {
    if (todo.id) {
      try {
        const updatedTodo = await TodoService.updateTodo(todo.id, { ...todo, completed: !todo.completed });
        setTodos(todos.map(t => t.id === todo.id ? updatedTodo : t));
      } catch (error) {
        console.error('Error toggling todo:', error);
      }
    }
  };

  const openEditModal = (todo: Todo) => {
    setEditingTodo(todo);
    setShowModal(true);
  };

  return (
    <Container className="mt-5">
      <h1 className="text-center mb-4">Todo List</h1>
      <Row>
        <Col>
          <Button variant="primary" onClick={() => { setEditingTodo(null); setShowModal(true); }}>
            Add New Todo
          </Button>
          <ListGroup className="mt-3">
            {todos.map(todo => (
              <ListGroup.Item key={todo.id} className="d-flex justify-content-between align-items-center">
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
                  <Button variant="outline-info" size="sm" className="me-2" onClick={() => openEditModal(todo)}>
                    Edit
                  </Button>
                  <Button variant="outline-danger" size="sm" onClick={() => todo.id && handleDeleteTodo(todo.id)}>
                    Delete
                  </Button>
                </div>
              </ListGroup.Item>
            ))}
          </ListGroup>
        </Col>
      </Row>

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
          <Button variant="primary" onClick={editingTodo ? handleUpdateTodo : handleCreateTodo}>
            {editingTodo ? 'Update' : 'Create'}
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default TodoList; 