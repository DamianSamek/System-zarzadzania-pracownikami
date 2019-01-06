import React, { Component } from 'react';
import {Button, ButtonGroup, Container, Table, Row, Col} from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import axios from 'axios';

class ManagerRaiseRequestList extends Component {

  emptyItem = {
    accepted: false
  }
  constructor(props) {
    super(props);
    this.state = {raiseRequests: [], isLoading: true, item: this.emptyItem};
    this.handleButtonClick = this.handleButtonClick.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});


    axios.get('/api/raiserequest?projection=withAgreementAndEmployee', {
      headers: {
        Authorization: window.localStorage.getItem("token") 
      }
    })
  .then(
    response => {
        const data = response.data._embedded.raiserequest;
        this.setState({raiseRequests: data, isLoading:false});
            
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

  async handleButtonClick(event, id) {

    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
    //  const {item} = this.state;

    await fetch(`/api/raiserequest/${id}`, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
      body: JSON.stringify(item),
    }).then(() => {
      let updatedRaiseRequests = [...this.state.raiseRequests].filter(i => i.id !== id);
      this.setState({raiseRequests: updatedRaiseRequests});
    });
  }
  

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
    const {raiseRequests, isLoading} = this.state;
    if (isLoading) {
      return <p>Loading...</p>;
    }
    const raiseRequestsList = raiseRequests.map(raiseRequest => {
     if(raiseRequest.considered===false && raiseRequest.active){
      return <tr key={raiseRequest.id} className="table table-warning">
        <td style={{whiteSpace: 'nowrap'}}>{raiseRequest.agreementId}</td>
        <td>{raiseRequest.firstName} {raiseRequest.secondName}</td>
        <td>{raiseRequest.salaryRequest}</td>
        <td><ul className={"list-unstyled"}>{raiseRequest.projects.map(project => {return <li>{project.client}</li>})}</ul></td>


        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" name="accepted" value={true} onClick={(e) => this.handleButtonClick(e,raiseRequest.id)}>Zatwierdź</Button>
            <Button size="sm" color="danger" name="accepted" value={false} onClick={(e) => {this.handleButtonClick(e,raiseRequest.id)}}>Odrzuć</Button>
          </ButtonGroup>
        </td>
      </tr>}
    });

    const raiseRequestsHistoryList = raiseRequests.map(raiseRequest => {
      if(raiseRequest.considered===true && raiseRequest.active) {
        return <tr key = {raiseRequest.id} className={raiseRequest.accepted ? "table table-success" : "table table-danger"}>
          <td>{raiseRequest.agreementId}</td>
          <td>{raiseRequest.firstName} {raiseRequest.secondName}</td>

          <td>{raiseRequest.salaryRequest}</td>
          <td>{raiseRequest.accepted ? "Tak" : "Nie"}</td>

        </tr>
      }
      }).reverse();

    return (
      <div>
        <ManagerAppNavbar/>
        <Row><Col sm={{size: 8, offset: 2}}>
       <Row>
        <Container fluid>
          <h3>Zapytania o podwyżkę</h3>
          <Table className="mt-4 table table-hover">
            <thead>
            <tr>
              <th >Numer umowy</th>
              <th >Pracownik</th>
              <th>Propozycja</th>
              <th>Projekty</th>
              <th>Akcja</th>
              
            </tr>
            </thead>
            <tbody>
            {raiseRequestsList}
            </tbody>
          </Table>
        </Container>
        </Row>
        <Row>
          <Container fluid>
            <h3>Historia zapytań</h3>
            <Table className="mt-4 table table-hover">
              <thead>
              <tr>
                <th width="20%">Numer umowy</th>
                <th width="20%">Pracownik</th>
                <th>Propozycja</th>
                <th>Zaakceptowano</th>

              </tr>
              </thead>
              <tbody>
              {raiseRequestsHistoryList}
              </tbody>
            </Table>
          </Container>
        </Row>
        </Col></Row>
      </div>
    );
  } else return <div>BRAK DOSTĘPU</div>
  }
}

export default ManagerRaiseRequestList;