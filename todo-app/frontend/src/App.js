import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Nav, Navbar } from 'react-bootstrap';
import TodoList from './components/TodoList';
import ApiEndpoints from './components/ApiEndpoints';

function App() {
  const [activeTab, setActiveTab] = useState('todos');

  return (
    <div className="App">
      <Navbar bg="primary" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand>Todo Application</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link 
                onClick={() => setActiveTab('todos')}
                active={activeTab === 'todos'}
              >
                Todo List
              </Nav.Link>
              <Nav.Link 
                onClick={() => setActiveTab('endpoints')}
                active={activeTab === 'endpoints'}
              >
                API Endpoints
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      {activeTab === 'todos' ? <TodoList /> : <ApiEndpoints />}
    </div>
  );
}

export default App; 