import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label,ButtonDropdown,DropdownToggle,DropdownMenu,DropdownItem } from 'reactstrap';
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
    this.props.history.push('/employee/agreement');
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_EMPLOYEE")
    {
    const {item} = this.state;
    const title = <h2>{'Prośba o podwyżkę'}</h2>;

    return <div>
      <EmployeeAppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="dateFrom">Proponowana kwota po podwyżce:</Label>
            <Input type="text" name="salaryRequest" id="salaryRequest"
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>

          <FormGroup>
            <Button color="primary" type="submit">Poproś</Button>
            <Button color="secondary" tag={Link} to="/employee/agreement">Anuluj</Button>
          </FormGroup>
        </Form>
        
      </Container>
    </div>
  }else return <div>BRAK DOSTĘPU</div>
}
}

export default withRouter(EmployeeAgreementRaiseRequest);