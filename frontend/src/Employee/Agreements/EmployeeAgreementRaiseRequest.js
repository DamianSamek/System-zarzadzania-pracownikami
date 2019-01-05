import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Col, Row, Label,ButtonDropdown,DropdownToggle,DropdownMenu,DropdownItem } from 'reactstrap';
import EmployeeAppNavbar from '../EmployeeAppNavbar';
import axios from 'axios';

class EmployeeAgreementRaiseRequest extends Component {

  emptyItem = {
    salaryRequest: '',
    agreementId: this.props.match.params.id
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.toggle = this.toggle.bind(this);
  }


  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  toggle() {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch(`/api/raiserequest/`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/employee');
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_EMPLOYEE")
    {
    const {item} = this.state;
    const title = <h2>{'Prośba o podwyżkę'}</h2>;

    return(


      <Container>
        <Row><Col sm={{size: 6, offset: 3}}>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="dateFrom">Proponowana kwota po podwyżce:</Label>
            <Input required type="number" name="salaryRequest" id="salaryRequest"
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>

          <FormGroup>
            <Button color="primary" type="submit">Poproś</Button>
            <Button color="secondary" tag={Link} to="/employee/">Anuluj</Button>
          </FormGroup>
        </Form>
        </Col></Row>
      </Container>

      )
  }else return <div>BRAK DOSTĘPU</div>
}
}

export default withRouter(EmployeeAgreementRaiseRequest);