import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';

class ManagerUsersList extends Component {

  constructor(props) {
    super(props);
    this.state = {users: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});


    axios.get('/api/user', {
      headers: {
        Authorization: localStorage.getItem("token")
      }
    })
  .then(
    response => {
        const data = response.data._embedded.user;
        this.setState({users: data, isLoading:false});
            
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
    await fetch(`/api/user/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedUsers = [...this.state.users].filter(i => i.id !== id);
      this.setState({users: updatedUsers});
    });
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
    const {users, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const usersList = users.map(user => {
    //   const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''}`;
      return <tr key={user.id}>
        <td style={{whiteSpace: 'nowrap'}}>{user.firstName}</td>
        <td>{user.secondName}</td>
        <td>{user.email}</td>
        <td>{user.enabled.toString()}</td>
        
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/manager/user/" + user.id}>Edytuj</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(user.id)}>Usuń</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <ManagerAppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/manager/user/new">Dodaj użytkownika</Button>
          </div>
          <h3>Zarządzaj użytkownikami</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Imię</th>
              <th width="20%">Nazwisko</th>
              <th>E-mail</th>
              <th>Aktywny</th>
              <th width="10%">Akcja</th>
            </tr>
            </thead>
            <tbody>
            {usersList}
            </tbody>
          </Table>
        </Container>
      </div>
    );}else return <div>BRAK DOSTĘPU</div>
  }
}

export default ManagerUsersList;