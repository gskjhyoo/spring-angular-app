import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isNavbarCollapsed:boolean;

  constructor() { }

  ngOnInit() {
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return true;//개발편의를 위해 일단 true
  }

  logout() {
  }

}
