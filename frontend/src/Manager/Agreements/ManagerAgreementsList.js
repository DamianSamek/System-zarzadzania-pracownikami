import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';

class ManagerAgreementsList extends Component {

  constructor(props) {
    super(props);
    this.state = {agreements: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});


    axios.get('/api/agreement?projection=withEmployeeDetails',{
      headers: {
        Authorization: localStorage.getItem("token")
      } 
    })
  .then(
    response => {
        const data = response.data._embedded.agreement;
        this.setState({agreements: data, isLoading:false});
            
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
    await fetch(`/api/agreement/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      }})
      .then(() => {
        let updatedAgreements = [...this.state.agreements].filter(i => i.id !== id);;
        this.setState({agreements: updatedAgreements});
      });
    
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER")
    {
      const {agreements, isLoading} = this.state;
    if (isLoading) {
      return <p>Loading...</p>;
    }

    const agreementsList = agreements.map(agreement => {
    if(agreement.active) {
      return <tr key={agreement.id}>
        <td style={{whiteSpace: 'nowrap'}}>{agreement.id}</td>
        <td>{agreement.user.firstName} {agreement.user.secondName}</td>
        <td>{agreement.dateFrom.toString().slice(0,10)}</td>
        <td>{agreement.dateTo.toString().slice(0,10)}</td>
        <td>{agreement.salary}</td>
        
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/manager/agreement/" + agreement.id}>Edytuj</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(agreement.id)}>Zakończ</Button>
          </ButtonGroup>
        </td>
      </tr>
    }});

    return (
      <div>
        <ManagerAppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/manager/agreement/new">Dodaj umowę</Button>
          </div>
          <h3>Zarządzaj umowami</h3>
          <Table className="mt-4 table table-hover">
            <thead>
            <tr>
              <th width="20%">Numer umowy</th>
              <th width="20%">Pracownik</th>
              <th>Data rozpoczęcia</th>
              <th>Data zakończenia</th>
              
              <th>Płaca</th>
              <th>Akcja</th>
            </tr>
            </thead>
            <tbody>
            {agreementsList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  } else return <div>BRAK DOSTĘPU</div>;
}
}

export default ManagerAgreementsList;