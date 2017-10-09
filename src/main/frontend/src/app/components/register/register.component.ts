import { Component, OnInit } from '@angular/core';
import {ApiRequestService} from '../../shared/service/api-request.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  confirmPassword: string;
  doNotMatch: string;
  error: string;
  errorEmailExists: string;
  errorUserExists: string;
  registerAccount: any;
  success: boolean;

  constructor(
    private apiRequest: ApiRequestService
  ) {
  }

  ngOnInit() {
    this.success = false;
    this.registerAccount = {};
  }

  register() {
    if (this.registerAccount.password !== this.confirmPassword) {
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      this.error = null;
      this.errorUserExists = null;
      this.errorEmailExists = null;
      this.registerAccount.langKey = 'en';

      this.apiRequest.post('api/register', this.registerAccount).subscribe(() => {
        this.success = true;
      }, (response) => this.processError(response));
    }
  }

  private processError(response) {
    this.success = null;
    if (response.status === 400 && response._body === 'login already in use') {
      this.errorUserExists = 'ERROR';
    } else if (response.status === 400 && response._body === 'email address already in use') {
      this.errorEmailExists = 'ERROR';
    } else {
      this.error = 'ERROR';
    }
  }
}
