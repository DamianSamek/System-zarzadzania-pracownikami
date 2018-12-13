import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EmployeeList from './Employees/EmployeeList';
import UserEdit from'./Employees/UserEdit';
import UsersList from './Users/UsersList';
import ProjectsList from './Projects/ProjectsList';
import AgreementsList from './Agreements/AgreementsList';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/user' exact={true} component={UsersList}/>
          <Route path='/user/:id' component={UserEdit}/>
          <Route path='/employee' exact={true} component={EmployeeList}/>
          {/* <Route path='/employee/:id' component={EmployeeEdit}/> */}
          <Route path='/project' exact={true} component={ProjectsList}/>
          <Route path='/agreement' exact={true} component={AgreementsList}/>
        </Switch>
      </Router>
    )
  }
}

export default App;