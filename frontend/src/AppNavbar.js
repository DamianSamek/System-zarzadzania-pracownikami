import React, { Component } from 'react';
import { Navbar, NavbarBrand } from 'reactstrap';
import { Link } from 'react-router-dom';

export default class AppNavbar extends Component {
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

  render() {
    return <Navbar color="dark" dark expand="md">
      <NavbarBrand tag={Link} to="/">Home | </NavbarBrand>
      <NavbarBrand tag={Link} to="/user">Użytkownicy |</NavbarBrand>
      <NavbarBrand tag={Link} to="/employee">Pracownicy |</NavbarBrand>
      <NavbarBrand tag={Link} to="/agreement">Umowy |</NavbarBrand>
      <NavbarBrand tag={Link} to="/project">Projekty |</NavbarBrand>
      <NavbarBrand tag={Link} to="/">Zapytania o podwyżkę |</NavbarBrand>
      <NavbarBrand tag={Link} to="/">Dziennik przychodów |</NavbarBrand>
      <NavbarBrand tag={Link} to="/">Dziennik wydatków |</NavbarBrand>
    </Navbar>;
  }
}