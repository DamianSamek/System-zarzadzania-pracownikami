import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';

class ManagerProjectsList extends Component {

  constructor(props) {
    super(props);
    this.state = {users: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});


    axios.get('/api/project',{
      headers: {
        Authorization: localStorage.getItem("token")
      }
    })
  .then(
    response => {
        const data = response.data._embedded.project;
        this.setState({projects: data, isLoading:false});
            
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
    await fetch(`/api/project/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      }
    }).then(() => {
      let updatedProjects = [...this.state.projects].filter(i => i.id !== id);
      this.setState({projects: updatedProjects});
    });
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
    const {projects, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const projectsList = projects.map(project => {
    //   const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''}`;
      return <tr key={project.id}>
        <td style={{whiteSpace: 'nowrap'}}>{project.client}</td>
        <td>{project.description}</td>
        <td>{project.fee}</td>
        <td>{project.finished.toString()}</td>
        
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/manager/project/" + project.id}>Edytuj</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(project.id)}>Usuń</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <ManagerAppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/manager/project/new">Dodaj projekt</Button>
          </div>
          <h3>Zarządzaj projektami</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Klient</th>
              <th width="20%">Opis</th>
              <th>Stawka</th>
              <th>Zakończony</th>
              <th width="10%">Akcja</th>
            </tr>
            </thead>
            <tbody>
            {projectsList}
            </tbody>
          </Table>
        </Container>
      </div>
    );}else return <div>BRAK DOSTĘPU</div>
  }
}

export default ManagerProjectsList;