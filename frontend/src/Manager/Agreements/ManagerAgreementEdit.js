import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import axios from 'axios';
import Select from "react-select";
import makeAnimated from "react-select/lib/animated";

class ManagerAgreementEdit extends Component {

  emptyItem = {
    firstName: '',
    secondName: '',
    employeeEmail: '',
    enabled: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      dropdownOpen: false,
      options: [],
      dropdownValue: "",
      selectedOption: "",
      errorMessage: ''
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.toggle = this.toggle.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const agreement = await (await fetch(`/api/agreement/${this.props.match.params.id}`,{
        headers: {
          'Authorization': localStorage.getItem("token")
        }
      })).json();
      this.setState({item: agreement});
    }
    else {
      axios.get('/api/employee?projection=withUserDetails', {
        headers: {
          Authorization: window.localStorage.getItem("token") 
        }
      })
    .then(
      response => {
          const data = response.data._embedded.employee;
          this.setState({options: data, isLoading:false});

      }    
    );
    }
  }

  handleChange(event) {
    // this.setState({dropdownValue: event.target.value})
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
    item.employeeEmail = this.state.selectedOption;

    await fetch((item.id) ? `/api/agreement/${this.props.match.params.id}` : '/api/agreement/', {
      method: (item.id) ? 'PATCH' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
      body: JSON.stringify(item),
    });
    if(this.state.selectedOption!=="")
    this.props.history.push("/manager/agreement");
  }

  onChangeSelect = (selectedOption) => {
    this.setState({ selectedOption: selectedOption.value});
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER")
    {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edycja umowy'  : 'Dodawanie umowy'}</h2>;

      const employees = this.state.options.map(option => {
        var activeAgreements =0;
          option.agreements.map(agreement => {
            if(agreement.active) {activeAgreements+=1}
          })
             if(option.enabled && activeAgreements===0) return {value: option.email, label:option.firstName+" "+ option.secondName}
          }
      ).filter(notUndefined => notUndefined);

    return <div>
      <ManagerAppNavbar/>
      <Container>
        {title}
        {item.id ? <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="dateFrom">Data rozpoczęcia</Label>
            <Input required type="date" name="dateFrom" id="dateFrom" value={item.dateFrom || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Label for="email">Data zakończenia</Label>
            <Input required type="date" name="dateTo" id="dateTo" value={item.dateTo || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
                   </FormGroup>
                   <Label for="email">Płaca</Label>
            <Input required type="number" name="salary" id="salary" value={item.salary || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          <FormGroup>
            <Button color="primary" type="submit">Zapisz</Button>{' '}
            <Button color="secondary" tag={Link} to="/manager/agreement">Anuluj</Button>
          </FormGroup>
        </Form>
        
      :
      
      <Form onSubmit={this.handleSubmit}>

          <FormGroup>
            <Label for="dateFrom">Data rozpoczęcia</Label>
            <Input required type="date" name="dateFrom" id="dateFrom"
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Label for="email">Data zakończenia</Label>
            <Input required type="date" name="dateTo" id="dateTo"
                   onChange={this.handleChange} autoComplete="address-level1"/>
                   </FormGroup>
                   <FormGroup>
            <Label for="email">Płaca</Label>
            <Input required type="number" name="salary" id="salary"
                   onChange={this.handleChange} autoComplete="address-level1"/>
                   </FormGroup>
          <FormGroup> </FormGroup>
          <FormGroup>

            <FormGroup>
              <Label for="email">Pracownik</Label>
              <Select placeholder="Wybierz pracownika" components={makeAnimated()} onChange={this.onChangeSelect} options={employees}  />
            </FormGroup>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Zapisz</Button>{' '}
            <Button color="secondary" tag={Link} to="/manager/agreement">Anuluj</Button>
          </FormGroup>

          
        </Form>}
        
      </Container>
    </div>
  }else return <div>BRAK DOSTĘPU</div>
}
}

export default withRouter(ManagerAgreementEdit);