import React, { Component } from 'react';
import { Container, Table, Row, Col } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import axios from 'axios';
import {Pie} from 'react-chartjs-2';

class IncomesJournal extends Component {

    constructor(props) {
        super(props);
        this.state = {projects: [], isLoading: true};
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

    render() {
        if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
            const {projects, isLoading} = this.state;

            if (isLoading) {
                return <p>Loading...</p>;
            }

            var sumOfIncomes = 0;

            const labels = [];
            const fees = [];
            const projectsList = projects.map(project => {

                sumOfIncomes += (project.finished) ? project.fee : 0;
                if(project.finished) {labels.push(project.client);
                fees.push(project.fee)}
                return (project.finished) ? <tr key={project.id}>
                    <td style={{whiteSpace: 'nowrap'}}>{project.client}</td>
                    <td>{project.description}</td>
                    <td>{project.fee}</td>
                </tr> : null
            });

            const data = {
                labels: labels,
                datasets: [
                    {
                        label: 'Wysokość wynagrodzenia',
                        backgroundColor: 'rgba(0,0,128,1)',
                        borderColor: 'rgba(255,255,255,0)',
                        borderWidth: 3,
                        hoverBackgroundColor: 'rgba(0,0,255,1)',
                        hoverBorderColor: 'rgba(255,255,0,1)',
                        data: fees
                    }
                ],
            };





            return (
                <div>
                    <ManagerAppNavbar/>
                    <Row><Col sm={{size: 8, offset: 2}}>
                    <Container fluid>
                        <h3>Dziennik przychodów</h3>
                        <Table className="mt-4 table table-hover">
                            <thead>
                            <tr>
                                <th width="20%">Klient</th>
                                <th width="20%">Opis</th>
                                <th>Stawka</th>
                            </tr>
                            </thead>
                            <tbody>
                            {projectsList}
                            </tbody>
                        </Table>
                        <h5>Suma przychodów: {sumOfIncomes} zł</h5>


                        <Container>
                            <div>
                                <Pie data={data}/>
                            </div></Container>
                    </Container></Col></Row>
                </div>
            );}else return <div>BRAK DOSTĘPU</div>
    }
}

export default IncomesJournal;