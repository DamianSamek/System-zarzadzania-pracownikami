import React, { Component } from 'react';
import { Container, Table } from 'reactstrap';
import EmployeeAppNavbar from '../EmployeeAppNavbar';
import axios from 'axios';

class EmployeeProjectsList extends Component {

  constructor(props) {
    super(props);
    this.state = {users: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});


    axios.get(`/api/project/${localStorage.getItem("loggedUserId")}`,{
      headers: {
        Authorization: localStorage.getItem("token")
      }
    })
  .then(
    response => {
        const data = response.data;
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
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedProjects = [...this.state.projects].filter(i => i.id !== id);
      this.setState({projects: updatedProjects});
    });
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_EMPLOYEE"){
    const {projects, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const projectsList = projects.map(project => {
    //   const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''}`;
      return <tr key={project.id}>
        <td style={{whiteSpace: 'nowrap'}}>{project.client}</td>
        <td>{project.description}</td>
        <td>{project.finished.toString()}</td>
      </tr>
    });

    return (
      <div>
        <EmployeeAppNavbar/>
        <Container fluid>
          <h3>Moje projekty</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Klient</th>
              <th width="20%">Opis</th>
              <th>Zakończony</th>
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

export default EmployeeProjectsList;