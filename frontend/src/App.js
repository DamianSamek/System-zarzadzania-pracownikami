import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ManagerEmployeeList from './Manager/Employees/ManagerEmployeeList';
import ManagerEmployeeEdit from './Manager/Employees/ManagerEmployeeEdit';
import ManagerProjectsList from './Manager/Projects/ManagerProjectsList';
import ManagerProjectEdit from './Manager/Projects/ManagerProjectEdit';
import ManagerAgreementsList from './Manager/Agreements/ManagerAgreementsList';
import ManagerAgreementEdit from './Manager/Agreements/ManagerAgreementEdit';
import ManagereRaiseRequestsList from './Manager/RaiseRequests/ManagerRaiseRequestsList';
import IncomesJournal from './Manager/IncomesJournal/IncomesJournal';
import Login from './Components/Login';
import EmployeeHome from './Employee/EmployeeHome';
import EmployeeAgreementRaiseRequest from './Employee/Agreements/EmployeeAgreementRaiseRequest';
import EmployeeProjectsList from './Employee/Projects/EmployeeProjectsList';
import ExpensesJournal from "./Manager/ExpensesJournal/ExpensesJournal";

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Login}/>
          <Route path='/manager/employee' exact={true} component={ManagerEmployeeList}/>
          <Route path='/manager/employee/:id' component={ManagerEmployeeEdit}/>
          <Route path='/manager/project' exact={true} component={ManagerProjectsList}/>
          <Route path='/manager/project/:id' exact={true} component={ManagerProjectEdit}/>
          <Route path='/manager/agreement' exact={true} component={ManagerAgreementsList}/>
          <Route path='/manager/agreement/:id' exact={true} component={ManagerAgreementEdit}/>
          <Route path='/manager/raiserequest' exact={true} component = {ManagereRaiseRequestsList}/>
          <Route path='/manager/incomes' exact={true} component = {IncomesJournal}/>
          <Route path='/employee' exact={true} component={EmployeeHome}/>
          <Route path='/employee/agreement/:id' exact={true} component={EmployeeAgreementRaiseRequest} />
          <Route path='/employee/project' exact={true} component={EmployeeProjectsList}/>
          <Route path='/manager/expenses' exact component={ExpensesJournal}/>
          
          
        </Switch>
      </Router>
    )
  }
}

export default App;