import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
  render() {
    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <Link to="/user"><Button color="link">Zarządzaj użytkownikami</Button></Link>
        </Container>
      </div>
    );
  }
}

export default Home;