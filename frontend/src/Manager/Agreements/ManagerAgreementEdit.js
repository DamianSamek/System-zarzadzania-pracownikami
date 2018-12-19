import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';

class ManagerAgreementEdit extends Component {

  emptyItem = {
    firstName: '',
    secondName: '',
    email: '',
    enabled: ''
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
      const agreement = await (await fetch(`/api/agreement/${this.props.match.params.id}`,{
        headers: {
          'Authorization': localStorage.getItem("token")
        }
      })).json();
      this.setState({item: agreement});
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

    await fetch('/api/agreement', {
      method: (item.id) ? 'PATCH' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/manager/agreement');
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER")
    {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edycja umowy'  : 'Dodawanie umowy'}</h2>;

    return <div>
      <ManagerAppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="dateFrom">Data rozpoczęcia</Label>
            <Input type="text" name="dateFrom" id="dateFrom" value={item.dateFrom || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Label for="email">Data zakończenia</Label>
            <Input type="text" name="dateTo" id="dateTo" value={item.dateTo || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
                   </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Zapisz</Button>{' '}
            <Button color="secondary" tag={Link} to="/manager/agreement">Anuluj</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }else return <div>BRAK DOSTĘPU</div>
}
}

export default withRouter(ManagerAgreementEdit);