import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import Select from 'react-select';
import makeAnimated from 'react-select/lib/animated';
import axios from 'axios';

class ManagerProjectEdit extends Component {

  emptyItem = {
    client: '',
    description: '',
    fee: '',
    finished: '',
    employees: []
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      options : [],
        checked: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.toggleCheckbox = this.toggleCheckbox.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const project = await (await fetch(`/api/project/${this.props.match.params.id}`, {
        headers: {
          'Authorization': localStorage.getItem("token")
        }
      })).json();
      console.log(project);
      this.setState({item: project, checked: project.finished});
    }

    axios.get('/api/employee?projection=withUserDetails', {
      headers: {
        Authorization: localStorage.getItem("token")
      }
    })
  .then(
    response => {
        const data = response.data._embedded.employee;
        this.setState({options: data});
    }
  );
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    const employees = [];
    const items = this.state.selectedOptions.map(item => employees.push(item.value));
    item.employees = employees
    item.finished = this.state.checked;

    

    await fetch((item.id) ? `/api/project/${this.props.match.params.id}`: '/api/project/', {
      method: (item.id) ? 'PATCH' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
      
      body: JSON.stringify(item),
    });
    console.log(item);
    this.props.history.push('/manager/project');
  }

  onChangeSelect = (selectedOptions) => {
    this.setState({selectedOptions});
  }

  toggleCheckbox(evt){
      this.setState({checked: evt.target.checked});
  }

  render() {

    const techCompanies = this.state.options.map(option =>
        ({value: option.email, label:option.firstName+" "+ option.secondName}));

    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER")
    {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edycja projektu' : 'Dodawanie projektu'}</h2>;

    return (
    <div>

      
      <ManagerAppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
          
            <Label for="firstName">Klient</Label>
            <Input type="text" name="client" id="client" value={item.client || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="secondName">Opis</Label>
            <Input type="text" name="description" id="description" value={item.description || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Label for="email">Stawka</Label>
            <Input type="text" name="fee" id="fee" value={item.fee || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>

            <Input type="checkbox" checked={this.state.checked} name="finished" id="finished" value={item.finished || ''}
                   onChange={this.toggleCheckbox} autoComplete="address-level1"/>
            <Label>Zakończony</Label>
          </FormGroup>
          <FormGroup>
          <Label for="email">Pracownicy biorący udział</Label>
          <Select components={makeAnimated()}isMulti onChange={this.onChangeSelect} options = {techCompanies} />
          </FormGroup>
          
          <FormGroup>
            <Button color="primary" type="submit">Zapisz</Button>{' '}
            <Button color="secondary" tag={Link} to="/manager/project">Anuluj</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>);} else return <div>BRAK DOSTĘPU</div>
  }
}

export default withRouter(ManagerProjectEdit);