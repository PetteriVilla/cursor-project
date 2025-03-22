import React, { useState, useEffect } from 'react';
import { Container, ListGroup, Button, Modal, Form, Alert } from 'react-bootstrap';
import axios from 'axios';

const ApiEndpoints = () => {
  const [endpoints, setEndpoints] = useState([]);
  const [selectedEndpoint, setSelectedEndpoint] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [inputData, setInputData] = useState({});
  const [responseMessage, setResponseMessage] = useState(null);

  useEffect(() => {
    fetchEndpoints();
  }, []);

  const fetchEndpoints = async () => {
    try {
      const response = await axios.get('/api/endpoints');
      setEndpoints(response.data);
    } catch (error) {
      console.error('Error fetching endpoints:', error);
    }
  };

  const handleEndpointClick = (endpoint) => {
    if (endpoint.requiresInput) {
      setSelectedEndpoint(endpoint);
      setShowModal(true);
      setInputData({});
      setResponseMessage(null);
    } else {
      handleSubmit(endpoint);
    }
  };

  const handleSubmit = async (endpoint) => {
    try {
      let response;
      const basePath = endpoint.path.replace('{id}', inputData.id || '');

      switch (endpoint.method) {
        case 'GET':
          response = await axios.get(basePath, { params: inputData });
          break;
        case 'POST':
          response = await axios.post(basePath, inputData);
          break;
        case 'PUT':
          response = await axios.put(basePath, inputData);
          break;
        case 'DELETE':
          response = await axios.delete(basePath, { params: inputData });
          break;
        default:
          throw new Error('Unsupported method');
      }

      setResponseMessage({
        type: 'success',
        text: JSON.stringify(response.data, null, 2)
      });
      setShowModal(false);
    } catch (error) {
      setResponseMessage({
        type: 'danger',
        text: error.response?.data?.message || error.message
      });
    }
  };

  const renderInputFields = (endpoint) => {
    const pathParams = endpoint.path.match(/\{([^}]+)\}/g) || [];
    return pathParams.map(param => 
      param.replace(/[{}]/g, '')
    ).map(paramName => (
      <Form.Group key={paramName} className="mb-3">
        <Form.Label>{paramName.charAt(0).toUpperCase() + paramName.slice(1)}</Form.Label>
        <Form.Control
          type="text"
          placeholder={`Enter ${paramName}`}
          value={inputData[paramName] || ''}
          onChange={(e) => setInputData({
            ...inputData,
            [paramName]: e.target.value
          })}
        />
      </Form.Group>
    ));
  };

  return (
    <Container className="mt-5">
      <h2 className="text-center mb-4">API Endpoints</h2>
      <ListGroup>
        {endpoints.map((endpoint, index) => (
          <ListGroup.Item 
            key={index} 
            action 
            onClick={() => handleEndpointClick(endpoint)}
            className="d-flex justify-content-between align-items-center"
          >
            <div>
              <strong>{endpoint.path}</strong>
              <br />
              <small>{endpoint.method} - {endpoint.description}</small>
            </div>
            {endpoint.requiresInput && (
              <Button variant="outline-primary" size="sm">
                Interact
              </Button>
            )}
          </ListGroup.Item>
        ))}
      </ListGroup>

      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>
            {selectedEndpoint?.method} {selectedEndpoint?.path}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={(e) => {
            e.preventDefault();
            handleSubmit(selectedEndpoint);
          }}>
            {selectedEndpoint && renderInputFields(selectedEndpoint)}
            
            {responseMessage && (
              <Alert variant={responseMessage.type} className="mt-3">
                <pre>{responseMessage.text}</pre>
              </Alert>
            )}

            <Button 
              variant="primary" 
              type="submit" 
              className="w-100 mt-3"
            >
              Submit
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </Container>
  );
};

export default ApiEndpoints; 