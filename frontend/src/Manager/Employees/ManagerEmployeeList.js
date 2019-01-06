import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';

class ManagerEmployeeList extends Component {

  constructor(props) {
    super(props);
    this.state = {employees: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});


    axios.get('/api/employee?projection=withUserDetails', {
      headers: {
        Authorization: window.localStorage.getItem("token") 
      }
    })
  .then(
    response => {
        const data = response.data._embedded.employee;
        this.setState({employees: data, isLoading:false});
            
    }    
  )
  .catch(function (error) {
    // handle error
    console.log(error);
  })
  .then(function () {
    // always executed
  });

  }

  async remove(id) {
    await fetch(`/api/employee/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      }
    }).then(() => {
      let updatedEmployees = [...this.state.employees].filter(i => i.id !== id);
      this.setState({employees: updatedEmployees});
    });
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
    const {employees, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }
    const employeesList = employees.map(employee => {
    if (employee.enabled) {
      return <tr key={employee.id}>
        <td style={{whiteSpace: 'nowrap'}}>{employee.firstName}</td>
        <td>{employee.secondName}</td>
        <td>{employee.email}</td>
        <td>{employee.phone}</td>
        <td>{employee.position}</td>
        
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/manager/employee/" + employee.id}>Edytuj</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(employee.id)}>Zwolnij</Button>
          </ButtonGroup>
        </td>
      </tr>
    }});

    return (
      <div>
        <ManagerAppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/manager/employee/new">Dodaj pracownika</Button>
          </div>
          <h3>Zarządzaj pracownikami</h3>
          <Table className="mt-4 table table-hover">
            <thead>
            <tr>
              <th width="20%">Imię</th>
              <th width="20%">Nazwisko</th>
              <th>E-mail</th>
              <th>Telefon</th>
              <th>Stanowisko</th>
              <th width="10%">Akcja</th>
              
            </tr>
            </thead>
            <tbody>
            {employeesList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  } else return <div>BRAK DOSTĘPU</div>
  }
}

export default ManagerEmployeeList;