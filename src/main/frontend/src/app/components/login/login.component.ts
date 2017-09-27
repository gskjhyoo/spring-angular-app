import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../shared/service/login.service';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: [ './login.component.css']
})
export class LoginComponent implements OnInit {
  model: any = {};
  errMsg:string = '';

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private loginService: LoginService) { }

  ngOnInit() { }

  login() {
    this.loginService.getToken(this.model.username, this.model.password)
      .subscribe(resp => {
          if (resp === undefined || resp.user.token === undefined || resp.user.token === "INVALID" ){
            this.errMsg = 'Username or password is incorrect';
            return;
          }
          this.router.navigate([resp.landingPage]);
        },
        errResponse => {
          switch(errResponse.status){
            case 401:
              this.errMsg = 'Username or password is incorrect';
              break;
            case 404:
              this.errMsg = 'Service not found';
            case 408:
              this.errMsg = 'Request Timedout';
            case 500:
              this.errMsg = 'Internal Server Error';
            default:
              this.errMsg = 'Server Error';
          }
          console.log("errorcode test" + this.errMsg);
        }
      );
  }
}
