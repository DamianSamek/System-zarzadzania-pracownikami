import React, { Component } from 'react';
import {Row,Col, Container, Table} from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import axios from 'axios';
import {Pie} from 'react-chartjs-2';

class ExpensesJournal extends Component {

    constructor(props) {
        super(props);
        this.state = {agreements: [], isLoading: true};
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

    render() {
        if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
            const {agreements, isLoading} = this.state;

            if (isLoading) {
                return <p>Loading...</p>;
            }

            var sumOfExpenses = 0;

            const labels = [];
            const salaries = [];
            const agreementsList = agreements.map(agreement => {

                if(agreement.active){
                sumOfExpenses +=  agreement.salary;
                labels.push(agreement.firstName+" "+agreement.secondName);
                    salaries.push(agreement.salary);
                return <tr key={agreement.id}>
                    <td style={{whiteSpace: 'nowrap'}}>{agreement.firstName + " "+agreement.secondName}</td>
                    <td>{agreement.salary}</td>
                </tr>}
            });

            const data = {
                labels: labels,
                datasets: [
                    {
                        label: 'Miesięczne zestawienie wydatków',
                        backgroundColor: 'rgba(0,0,128,1)',
                        borderColor: 'rgba(255,255,255,0)',
                        borderWidth: 3,
                        hoverBackgroundColor: 'rgba(0,0,255,1)',
                        hoverBorderColor: 'rgba(255,255,0,1)',
                        data: salaries
                    }
                ],
            };





            return (
                <div>
                    <ManagerAppNavbar/>
                    <Row> <Col sm={{size: 8, offset: 2}}>
                    <Container fluid>
                        <h3>Dziennik miesięcznych wydatków</h3>
                        <Table className="mt-4 table table-hover">
                            <thead>
                            <tr>
                                <th width="20%">Pracownik</th>
                                <th width="20%">Koszt zatrudnienia</th>
                            </tr>
                            </thead>
                            <tbody>
                            {agreementsList}
                            </tbody>
                        </Table>
                        <h5>Suma miesięcznych wydatków: {sumOfExpenses} zł</h5>


                        <Container>
                            <div>
                                <Pie data={data}/>
                            </div></Container>
                    </Container>
                    </Col></Row>
                </div>
            );}else return <div>BRAK DOSTĘPU</div>
    }
}

export default ExpensesJournal;