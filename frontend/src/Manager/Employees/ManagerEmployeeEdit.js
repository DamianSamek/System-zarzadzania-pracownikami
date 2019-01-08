import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label, Row, Col } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import axios from 'axios';

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
    password: '',
    confirmPassword: ''

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


    if(!item.id) {
    var body = JSON.stringify(item);
      axios.post( `/api/employee`,body,
       { headers: {
        "Accept": 'application/json',
            "Content-Type": 'application/json',
            "Authorization": localStorage.getItem("token")
      }}
      ).catch(
          (error) => {
          console.log(error.response.data.message);
          }
      )
        if(item.password===item.confirmPassword)
        this.props.history.push('/manager/employee');
    }
    else {
      var body = JSON.stringify(item);
      axios.patch( `/api/employee/${this.props.match.params.id}`,body,
          { headers: {
              "Accept": 'application/json',
              "Content-Type": 'application/json',
              "Authorization": localStorage.getItem("token")
            }}
      ).catch(
          (error) => {
            console.log(error.response.data.message);
          }
      )

        this.props.history.push('/manager/employee');
    }



  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER")
    {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edycja pracownika' : 'Dodawanie pracownika'}</h2>;

    return(<div>
      <ManagerAppNavbar/>
      <Container>
        {title}
        {item.id ? <Form onSubmit={this.handleSubmit}>
          <Row><Col sm="6">
          <FormGroup>
            <Label for="firstName">Imię</Label>
            <Input required type="text" name="firstName" id="firstName" value={item.firstName || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="secondName">Nazwisko</Label>
            <Input required type="text" name="secondName" id="secondName" value={item.secondName || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Label for="email">E-mail</Label>
            <Input required type="email" name="email" id="email" value={item.email || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
                   </FormGroup>
          <FormGroup>

            </FormGroup>
            <Label for="phone">Telefon</Label>
            <Input required pattern="\d{9}" title="9-cyfrowy numer telefonu"type="text" name="phone" id="phone" value={item.phone || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
                
          <FormGroup>

            </FormGroup>
            <Label for="phone">Stanowisko</Label>
            <Input required type="text" name="position" id="position" value={item.position || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>

          </Col>
            <Col sm="6">
              <FormGroup>
              </FormGroup>
              <Label for="phone">Ulica</Label>
              <Input required type="text" name="streetAddress" id="streetAddress" value={item.streetAddress || ''}
                     onChange={this.handleChange} autoComplete="address-level1"/>

              <FormGroup>
              </FormGroup>
              <Label for="phone">Miejscowość</Label>
              <Input required type="text" name="city" id="city" value={item.city || ''}
                     onChange={this.handleChange} autoComplete="address-level1"/>

              <FormGroup>
              </FormGroup>
              <Label for="phone">Kod pocztowy</Label>
              <Input required pattern="\d{2}-\d{3}" title="Wymagany format: 00-000" type="text" name="postalCode" id="postalCode" value={item.postalCode || ''}
                     onChange={this.handleChange} autoComplete="address-level1"/>

              <FormGroup>
              </FormGroup>
              <FormGroup>
                <Label for="phone">Województwo</Label>
                <Input required type="text" name="state" id="state" value={item.state || ''}
                       onChange={this.handleChange} autoComplete="address-level1"/>
              </FormGroup>
                
          <FormGroup>
            <Button color="primary" type="submit">Zapisz</Button>{' '}
            <Button color="secondary" tag={Link} to="/manager/employee">Anuluj</Button>
          </FormGroup>
            </Col></Row>
        </Form> :
      
      <Form onSubmit={this.handleSubmit}>
        <Row><Col sm="6">
      <FormGroup>
        <Label for="firstName">Imię</Label>
        <Input required type="text" name="firstName" id="firstName" value={item.firstName || ''}
               onChange={this.handleChange} autoComplete="name"/>
      </FormGroup>
      <FormGroup>
        <Label for="secondName">Nazwisko</Label>
        <Input required type="text" name="secondName" id="secondName" value={item.secondName || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>
      </FormGroup>
      <FormGroup>
      </FormGroup>
        <Label for="email">E-mail</Label>
        <Input required type="email" name="email" id="email" value={item.email || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>
               
      <FormGroup>           
        </FormGroup>
        <Label for="phone">Telefon</Label>
        <Input required pattern="\d{9}" title="9-cyfrowy numer telefonu" type="text" name="phone" id="phone" value={item.phone || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>
      <FormGroup>
        </FormGroup>
        <Label for="phone">Stanowisko</Label>
        <Input required type="text" name="position" id="position" value={item.position || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>

       <FormGroup>
        </FormGroup>
        <Label for="phone">Ulica</Label>
        <Input required type="text" name="streetAddress" id="streetAddress" value={item.streetAddress || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>         
        </Col>
          <Col sm="6">
            <FormGroup>
        </FormGroup>
        <Label for="phone">Miejscowość</Label>
        <Input required type="text" name="city" id="city" value={item.city || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>

         <FormGroup>
        </FormGroup>
        <Label for="phone">Kod pocztowy</Label>
        <Input required pattern="\d{2}-\d{3}" title="Wymagany format: 00-000" type="text" name="postalCode" id="postalCode" value={item.postalCode || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>

                 <FormGroup>
        </FormGroup>
          <FormGroup>
        <Label for="phone">Województwo</Label>
        <Input required type="text" name="state" id="state" value={item.state || ''}
               onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
              <Label for="phone">Hasło</Label>
              <Input required type="password" name="password" id="password"
                     onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
              <Label for="phone">Potwierdź hasło</Label>
              <Input required type="password" name="confirmPassword" id="confirmPassword"
                     onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
      <FormGroup>
        <Button color="primary" type="submit">Zapisz</Button>{' '}
        <Button color="secondary" tag={Link} to="/manager/employee">Anuluj</Button>
      </FormGroup>
          </Col></Row>
    </Form>
      }
        
      </Container>
    </div>);
    
   
   } else return <div>BRAK DOSTĘPU</div>
  }
}

export default withRouter(ManagerEmployeeEdit);