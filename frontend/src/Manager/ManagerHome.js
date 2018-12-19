import React, { Component } from 'react';
import ManagerAppNavbar from './ManagerAppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class ManagerHome extends Component {
  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
    return (
      
      <div>
        <ManagerAppNavbar/>
        <Container fluid>
          <Link to="/user"><Button color="link">Zarządzaj użytkownikami</Button></Link>
        </Container>
      </div>
    );
  } else return <div>BRAK DOSTĘPU</div>
}
}

export default ManagerHome;