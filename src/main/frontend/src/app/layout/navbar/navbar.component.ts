import {Component, OnInit} from '@angular/core';
import {UserInfoService} from "../../shared/service/user-info.service";

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isNavbarCollapsed:boolean;

  constructor(private userInfoService: UserInfoService) {
    this.isNavbarCollapsed = true;
  }

  ngOnInit() {
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.userInfoService.isLoggedIn();
    //return true;//개발편의를 위해 일단 true
  }

  logout() {
    this.userInfoService.removeUserInfo();
  }

  hasAuthority(role: string): boolean {
    console.log('role : ' + role);
    return this.userInfoService.hasAnyAuthority([<string>role]);
  }

}
