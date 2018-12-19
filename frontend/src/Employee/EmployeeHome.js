import React, { Component } from 'react';
import EmployeeAppNavbar from './EmployeeAppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class EmployeeHome extends Component {
  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_EMPLOYEE")
    {
    return (
      <div>
        <EmployeeAppNavbar/>
        <Container fluid>
          <Link to="/user"><Button color="link">Zarządzaj użytkownikami</Button></Link>
        </Container>
      </div>
    );}else return <div>BRAK DOSTĘPU</div>
  }
}

export default EmployeeHome;