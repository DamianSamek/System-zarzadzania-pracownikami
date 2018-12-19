import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';

class ManagerEmployeeEdit extends Component {

  emptyItem = {
    firstName: '',
    secondName: '',
    email: '',
    position: '',
    phone: '',
    streetAddress: '',
    state: '',
    city: '',

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
      const employee = await (await fetch(`/api/employee/${this.props.match.params.id}?projection=withUserDetails`,{
        headers: {
          'Authorization': localStorage.getItem("token")
        }
      }
        
      )).json();
      this.setState({item: employee});
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

    await fetch('/api/employee', {
      method: (item.id) ? 'PATCH' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
      body: JSON.stringify(item),
      
    });
    this.props.history.push('/manager/employee');
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER")
    {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edycja pracownika' : 'Dodawanie pracownika'}</h2>;
    console.log(item);

    return(<div>
      <ManagerAppNavbar/>
      <Container>
        {title}
        {item.id ? <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="firstName">Imię</Label>
            <Input type="text" name="firstName" id="firstName" value={item.firstName || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="secondName">Nazwisko</Label>
            <Input type="text" name="secondName" id="secondName" value={item.secondName || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Label for="email">E-mail</Label>
            <Input type="text" name="email" id="email" value={item.email || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
                   </FormGroup>
          <FormGroup>

            </FormGroup>
            <Label for="phone">Telefon</Label>
            <Input type="text" name="phone" id="phone" value={item.phone || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
                
          <FormGroup>

            </FormGroup>
            <Label for="phone">Stanowisko</Label>
            <Input type="text" name="position" id="position" value={item.position || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
                
          <FormGroup>
            <Button color="primary" type="submit">Zapisz</Button>{' '}
            <Button color="secondary" tag={Link} to="/manager/employee">Anuluj</Button>
          </FormGroup>
        </Form> :
      
      <Form onSubmit={this.handleSubmit}>
      <FormGroup>
        <Label for="firstName">Imię</Label>
        <Input type="text" name="firstName" id="firstName" value={item.firstName || ''}
               onChange={this.handleChange} autoComplete="name"/>
      </FormGroup>
      <FormGroup>
        <Label for="secondName">Nazwisko</Label>
        <Input type="text" name="secondName" id="secondName" value={item.secondName || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>
      </FormGroup>
      <FormGroup>
      </FormGroup>
        <Label for="email">E-mail</Label>
        <Input type="text" name="email" id="email" value={item.email || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>
               
      <FormGroup>           
        </FormGroup>
        <Label for="phone">Telefon</Label>
        <Input type="text" name="phone" id="phone" value={item.phone || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>
      <FormGroup>
        </FormGroup>
        <Label for="phone">Stanowisko</Label>
        <Input type="text" name="position" id="position" value={item.position || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>

       <FormGroup>
        </FormGroup>
        <Label for="phone">Ulica</Label>
        <Input type="text" name="streetAddress" id="streetAddress" value={item.streetAddress || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>         
            
            <FormGroup>
        </FormGroup>
        <Label for="phone">Miejscowość</Label>
        <Input type="text" name="city" id="city" value={item.city || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>

         <FormGroup>
        </FormGroup>
        <Label for="phone">Kod pocztowy</Label>
        <Input type="text" name="postalCode" id="postalCode" value={item.postalCode || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>

                 <FormGroup>
        </FormGroup>
        <Label for="phone">Województwo</Label>
        <Input type="text" name="state" id="state" value={item.state || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>
      <FormGroup>
        <Button color="primary" type="submit">Zapisz</Button>{' '}
        <Button color="secondary" tag={Link} to="/manager/employee">Anuluj</Button>
      </FormGroup>
    </Form>
      }
        
      </Container>
    </div>);
    
   
   } else return <div>BRAK DOSTĘPU</div>
  }
}

export default withRouter(ManagerEmployeeEdit);