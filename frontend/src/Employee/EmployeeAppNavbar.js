import React, { Component } from 'react';
import { Navbar, NavbarBrand } from 'reactstrap';
import { Link } from 'react-router-dom';

class EmployeeAppNavbar extends Component {
  constructor(props) {
    super(props);
    this.state = {isOpen: false};
    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  logout() {
    localStorage.setItem("token","");
    localStorage.setItem("loggedUserRole","");
  }

  render() {
    return <Navbar color="dark" dark expand="md">
      <NavbarBrand tag={Link} to="/employee">Home | </NavbarBrand>
      <NavbarBrand tag={Link} to="/employee/agreement">Moje Umowy |</NavbarBrand>
      <NavbarBrand tag={Link} to="/employee/project">Moje Projekty |</NavbarBrand>
      <NavbarBrand tag={Link} to="/">Zapytania o podwyżkę |</NavbarBrand>
      <NavbarBrand tag={Link} to="/" onClick={this.logout}>Wyloguj się</NavbarBrand>
    </Navbar>;
  }
}
export default EmployeeAppNavbar;