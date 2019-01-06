import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import {
    Button,
    ButtonGroup,
    Container,
    Table,
    Row,
    Col
} from 'reactstrap';
import axios from "axios";

class EmployeeHome extends Component {

    constructor(props) {
        super(props);
        this.state={
            employee: '',
        agreements: [],
        projects: [],
        isLoading: true};
        this.logout.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        axios.get(`/api/user/${localStorage.getItem("loggedUserId")}`,{
            headers: {
                Authorization: localStorage.getItem("token")
            }
        })
            .then(
                response => {
                    const data = response.data;
                    this.setState({employee: data, isLoading:false, agreements: data.agreements});

                }
            )
            .catch(function (error) {
                // handle error
                console.log(error);
            })
            .then(function () {
                // always executed
            });

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

    logout() {
        localStorage.setItem("token","");
        localStorage.setItem("loggedUserRole","");
        localStorage.setItem("loggedUserId","");
    }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_EMPLOYEE")
    {

        const projectsList = this.state.projects.map(project => {
            //   const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''}`;
            return <tr key={project.id}>
                <td style={{whiteSpace: 'nowrap'}}>{project.client}</td>
                <td>{project.description}</td>
                <td>{project.finished ? "Tak" : "Nie"}</td>
            </tr>
        });




        var activeAgreement;
        var raiseRequests = [];
        this.state.agreements.map(agreement => {
            if(agreement.active) {
                activeAgreement = agreement;
                raiseRequests = agreement.raiseRequests;
            }
        });

        const raiseRequestsList = raiseRequests.map(raiseRequest => {
            return <tr className={raiseRequest.considered && raiseRequest.accepted ? "table table-success" : raiseRequest.considered ? "table table-danger" : "table table-warning"}>
                <td>{raiseRequest.salaryRequest}</td>
                <td>{raiseRequest.considered ? "Tak" : "Nie"}</td>
                <td>{raiseRequest.accepted ? "Tak" : "Nie"}</td>

            </tr>
        });

    return (

        <Row><Col sm={{size: 8, offset: 2}}>

        <Container>


            <Row >
                <Col sm="6">
                    <Container>
                        <h1 className="display-4">Moje dane</h1>
                    <p className="lead">Imię: {this.state.employee.firstName}</p>
                    <p className="lead">Nazwisko: {this.state.employee.secondName}</p>
                    <p className="lead">Stanowisko: {this.state.employee.position}</p>
                    <p className="lead">Numer telefonu: {this.state.employee.phone}</p>
                    <p className="lead">Adres e-mail: {this.state.employee.email}</p>
                    </Container>
                </Col>

                <Col sm="6">

                    <div className="float-right">
                        <Button color="danger" tag={Link} to="/" onClick={this.logout}>Wyloguj</Button>
                    </div>
                    <Container>
                        <h1 className="display-4">Adres</h1>
                    <p className="lead">{this.state.employee.streetAddress}</p>
                    <p className="lead">{this.state.employee.postalCode} {this.state.employee.city}</p>
                    <p className="lead"> Województwo: {this.state.employee.state}</p>
                    </Container>
                </Col>


            </Row>
            <Row><Col sm="6">
                        <Container>
                        <h1 className="display-4">Moja umowa</h1>

                            {activeAgreement ?
                                <div>
                            <p className="lead"> Numer umowy: {activeAgreement.id}</p>
                            <p className="lead">Data rozpoczęcia: {activeAgreement.dateFrom}</p>
                            <p className="lead">Data zakończenia: {activeAgreement.dateTo}</p>

                            <p className="lead"> Płaca: {activeAgreement.salary}</p>


                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/employee/agreement/" + activeAgreement.id}>Poproś o podwyżkę</Button>
                        </ButtonGroup></div> : <h3>Brak aktywnej umowy</h3> }
        </Container>
            </Col>
            <Col sm="6">
            <Container>

                        <h1 className="display-4">Moje projekty</h1>
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
            </Col></Row>
            <Row>
                <Col sm="6">
                    <Container>

                        <h1 className="display-4">Prośby o podwyżkę</h1>
                        <Table className="mt-4">
                            <thead>
                            <tr>
                                <th>Kwota</th>
                                <th>Rozpatrzona</th>
                                <th>Zaakceptowana</th>
                            </tr>
                            </thead>
                            <tbody>
                            {raiseRequestsList}
                            </tbody>
                        </Table>
                    </Container>
                </Col>
            </Row>
        </Container>
        </Col></Row>

    );}else return <div>BRAK DOSTĘPU</div>
  }
}

export default EmployeeHome;