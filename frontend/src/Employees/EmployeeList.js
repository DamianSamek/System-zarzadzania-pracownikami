import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';

class EmployeeList extends Component {

  constructor(props) {
    super(props);
    this.state = {employees: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});


    axios.get('/api/employee?projection=withUserDetails')
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
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedEmployees = [...this.state.employees].filter(i => i.id !== id);
      this.setState({employees: updatedEmployees});
    });
  }

  render() {
    const {employees, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }
    console.log(employees);
    const employeesList = employees.map(employee => {
    //   const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''}`;
      return <tr key={employee.id}>
        <td style={{whiteSpace: 'nowrap'}}>{employee.user.firstName}</td>
        <td>{employee.user.secondName}</td>
        <td>{employee.user.email}</td>
        <td>{employee.phone}</td>
        <td>{employee.position}</td>
        
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/employee/" + employee.id}>Edytuj</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(employee.id)}>Usuń</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/employee/new">Dodaj pracownika</Button>
          </div>
          <h3>Zarządzaj pracownikami</h3>
          <Table className="mt-4">
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
  }
}

export default EmployeeList;