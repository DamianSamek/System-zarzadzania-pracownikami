import React, {Component} from 'react';
import {Redirect} from 'react-router-dom';
import {PostData} from '../Services/PostData';
import {
  Container, Col, Row, Form,
  FormGroup, Label, Input,
  Button, FormText, FormFeedback,
} from 'reactstrap';

class Login extends Component {

  constructor(){
    super();
   
    this.state = {
     username: '1',
     password: '2',
     redirectToReferrer : false,
     userRole: ''
         };

    this.login = this.login.bind(this);
    this.onChange = this.onChange.bind(this);

  } 

  login(e) {
    e.preventDefault();
    if(this.state.username && this.state.password){
      PostData('api/login',{'username': this.state.username, 'password': this.state.password}).then((result) => {
        console.log(this.state);
       let responseJson = result;
       if(responseJson.tokenValid){
                
         this.setState({userRole: responseJson.role.role});
         localStorage.setItem("loggedUserRole",this.state.userRole);
         localStorage.setItem("loggedUserId",responseJson.id);
         this.setState({redirectToReferrer: true});
       }
       
      });
    }
    
   }

  onChange(e){
    this.setState({[e.target.name]:e.target.value});
   }

  
  

  render() {
      
      if (this.state.redirectToReferrer) {
        if((this.state.userRole)==="ROLE_MANAGER"){
          return (<Redirect to={'/manager/employee'}/>)
        }
        else if((this.state.userRole)==="ROLE_EMPLOYEE"){
          return(<Redirect to={'/employee'}/>)
        }
     }
   
    

     return (
         <Row><Col sm={{size: 4, offset: 4}}>
  <Container className="App">
    <h2>Zaloguj się</h2>
    <Form className="form" onSubmit={(e) => this.login(e) }>
      <Col>
        <FormGroup>
          <Label>E-mail:</Label>
          <Input
            type="text"
            name="username"
            id="exampleEmail"
            required
            onChange={ this.onChange }
          />
        </FormGroup>
      </Col>
      <Col>
        <FormGroup>
          <Label for="examplePassword">Hasło:</Label>
          <Input
            type="password"
            name="password"
            id="examplePassword"
            onChange={ this.onChange }
            required

        />
        </FormGroup>
      </Col>
      <Button>Zaloguj</Button>
  </Form>
</Container>
         </Col></Row>);
  }
}

export default Login;