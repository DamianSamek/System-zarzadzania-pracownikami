import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';

class ManagerProjectEdit extends Component {

  emptyItem = {
    client: '',
    description: '',
    fee: '',
    finished: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const project = await (await fetch(`/api/project/${this.props.match.params.id}`, {
        headers: {
          'Authorization': localStorage.getItem("token")
        }
      })).json();
      console.log(project);
      this.setState({item: project});
    }
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

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER")
    {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edycja projektu' : 'Dodawanie projektu'}</h2>;

    return <div>
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
            <Label for="email">Zakończony</Label>
            <Input type="text" name="finished" id="finished" value={item.finished || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Zapisz</Button>{' '}
            <Button color="secondary" tag={Link} to="/manager/project">Anuluj</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>} else return <div>BRAK DOSTĘPU</div>
  }
}

export default withRouter(ManagerProjectEdit);