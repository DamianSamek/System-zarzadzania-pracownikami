import React, {Component} from 'react';
import {Redirect} from 'react-router-dom';
import {PostData} from '../Services/PostData';

class Login extends Component {

  constructor(){
    super();
   
    this.state = {
     username: '',
     password: '',
     redirectToReferrer : false,
     userRole: ''
         };

    this.login = this.login.bind(this);
    this.onChange = this.onChange.bind(this);

  } 

  login() {
    if(this.state.username && this.state.password){
      PostData('api/login',{'username': this.state.username, 'password': this.state.password}).then((result) => {
        console.log(this.state);
       let responseJson = result;
       if(responseJson.tokenValid){
                
         this.setState({userRole: responseJson.role.role});
         localStorage.setItem("loggedUserRole",this.state.userRole);  
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
          return (<Redirect to={'/manager'}/>)
        }
        else if((this.state.userRole)==="ROLE_EMPLOYEE"){
          return(<Redirect to={'/employee'}/>)
        }
     }
   
    

     return (
      <div className="row" id="Body">
        <div className="medium-5 columns left">
        <h4>Login</h4>
        <label>Username</label>
        <input type="text" name="username" placeholder="Username" onChange={this.onChange}/>
        <label>Password</label>
        <input type="password" name="password"  placeholder="Password" onChange={this.onChange}/>
        <input type="submit" className="button success" value="Login" onClick={this.login}/>
        <a href="/signup">Registration</a>
        </div>
      </div>
    );
  }
}

export default Login;