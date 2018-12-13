import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EmployeeList from './Employees/EmployeeList';
import EmployeeEdit from './Employees/EmployeeEdit';
import UserEdit from'./Users/UserEdit';
import UsersList from './Users/UsersList';
import ProjectsList from './Projects/ProjectsList';
import ProjectEdit from './Projects/ProjectEdit';
import AgreementsList from './Agreements/AgreementsList';
import AgreementEdit from './Agreements/AgreementEdit';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/user' exact={true} component={UsersList}/>
          <Route path='/user/:id' component={UserEdit}/>
          <Route path='/employee' exact={true} component={EmployeeList}/>
          <Route path='/employee/:id' component={EmployeeEdit}/>
          <Route path='/project' exact={true} component={ProjectsList}/>
          <Route path='/project/:id' exact={true} component={ProjectEdit}/>
          <Route path='/agreement' exact={true} component={AgreementsList}/>
          <Route path='/agreement/:id' exact={true} component={AgreementEdit}/>
        </Switch>
      </Router>
    )
  }
}

export default App;