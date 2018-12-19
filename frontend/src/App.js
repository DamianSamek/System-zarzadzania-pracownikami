import React, { Component } from 'react';
import './App.css';
import ManagerHome from './Manager/ManagerHome';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ManagerEmployeeList from './Manager/Employees/ManagerEmployeeList';
import ManagerEmployeeEdit from './Manager/Employees/ManagerEmployeeEdit';
import ManagerUserEdit from'./Manager/Users/ManagerUserEdit';
import ManagerUsersList from './Manager/Users/ManagerUsersList';
import ManagerProjectsList from './Manager/Projects/ManagerProjectsList';
import ManagerProjectEdit from './Manager/Projects/ManagerProjectEdit';
import ManagerAgreementsList from './Manager/Agreements/ManagerAgreementsList';
import ManagerAgreementEdit from './Manager/Agreements/ManagerAgreementEdit';
import Login from './Components/Login';
import EmployeeHome from './Employee/EmployeeHome';
import EmployeeAgreementsList from './Employee/Agreements/EmployeeAgreementsList';
import EmployeeProjectsList from './Employee/Projects/EmployeeProjectsList';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Login}/>
          <Route path='/manager' exact={true} component={ManagerHome}/>
          <Route path='/manager/user' exact={true} component={ManagerUsersList}/>
          <Route path='/manager/user/:id' component={ManagerUserEdit}/>
          <Route path='/manager/employee' exact={true} component={ManagerEmployeeList}/>
          <Route path='/manager/employee/:id' component={ManagerEmployeeEdit}/>
          <Route path='/manager/project' exact={true} component={ManagerProjectsList}/>
          <Route path='/manager/project/:id' exact={true} component={ManagerProjectEdit}/>
          <Route path='/manager/agreement' exact={true} component={ManagerAgreementsList}/>
          <Route path='/manager/agreement/:id' exact={true} component={ManagerAgreementEdit}/>
          <Route path='/employee' exact={true} component={EmployeeHome}/>
          <Route path='/employee/agreement' exact={true} component={EmployeeAgreementsList}/>
          <Route path='/employee/project' exact={true} component={EmployeeProjectsList}/>
          
        </Switch>
      </Router>
    )
  }
}

export default App;