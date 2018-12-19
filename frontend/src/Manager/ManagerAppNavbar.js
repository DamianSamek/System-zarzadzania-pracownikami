import React, { Component } from 'react';
import { Navbar, NavbarBrand } from 'reactstrap';
import { Link } from 'react-router-dom';

class AppNavbar extends Component {
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
      <NavbarBrand tag={Link} to="/manager">Home | </NavbarBrand>
      {/* <NavbarBrand tag={Link} to="/manager/user">Użytkownicy |</NavbarBrand> */}
      <NavbarBrand tag={Link} to="/manager/employee">Pracownicy |</NavbarBrand>
      <NavbarBrand tag={Link} to="/manager/agreement">Umowy |</NavbarBrand>
      <NavbarBrand tag={Link} to="/manager/project">Projekty |</NavbarBrand>
      <NavbarBrand tag={Link} to="/">Zapytania o podwyżkę |</NavbarBrand>
      <NavbarBrand tag={Link} to="/">Dziennik przychodów |</NavbarBrand>
      <NavbarBrand tag={Link} to="/">Dziennik wydatków |</NavbarBrand>
      <NavbarBrand tag={Link} to="/" onClick={this.logout}>Wyloguj się</NavbarBrand>
    </Navbar>;
  }
}
export default AppNavbar;