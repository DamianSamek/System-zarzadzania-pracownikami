import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import EmployeeAppNavbar from '../EmployeeAppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';

class EmployeeAgreementsList extends Component {

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
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedAgreements = [...this.state.agreements].filter(i => i.id !== id);
      this.setState({agreements: updatedAgreements});
    });
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_EMPLOYEE")
    {
      const {agreements, isLoading} = this.state;
    if (isLoading) {
      return <p>Loading...</p>;
    }

    const agreementsList = agreements.map(agreement => {
    //   const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''}`;
      return <tr key={agreement.id}>
        <td style={{whiteSpace: 'nowrap'}}>{agreement.number}</td>
        <td>{agreement.dateFrom}</td>
        <td>{agreement.dateTo}</td>        
        <td>{agreement.salary}</td>
        <td>{agreement.active.toString()}</td>
        
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/employee/agreement/" + agreement.id}>Poproś o podwyżkę</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <EmployeeAppNavbar/>
        <Container fluid>
          
          <h3>Historia umów</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Numer umowy</th>
              <th>Data rozpoczęcia</th>
              <th>Data zakończenia</th>
              
              <th>Płaca</th>
              <th width="10%">Aktywna</th>
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

export default EmployeeAgreementsList;