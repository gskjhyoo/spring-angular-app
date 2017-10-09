import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {Router} from '@angular/router';

import {Observable, Subject} from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {LoginInfoInStorage, UserInfoService} from './user-info.service';
import {ApiRequestService} from './api-request.service';
import {JwtHelper} from 'angular2-jwt';

export interface LoginRequestParam{
  username:string;
  password:string;
}

@Injectable()
export class LoginService {

  errMsg:string;
  public successPage:string = "/";
  constructor(
    private router:Router,
    private userInfoService: UserInfoService,
    private apiRequest: ApiRequestService
  ) {}

  getToken(username:string, password:string): Observable<any> {
    let me = this;

    let bodyData:LoginRequestParam = {
      "username": username,
      "password": password,
    }
    let loginDataSubject:Subject<any> = new Subject<any>();
    let loginInfoReturn:LoginInfoInStorage;

    this.apiRequest.post('api/authenticate', bodyData)
      .subscribe((res: Response) => {
        let jsonResp = res.json();
        if (jsonResp !== undefined && jsonResp !== null){

          let token = jsonResp.token;
          let info = JwtHelper.prototype.decodeToken(token);
          let displayName = info.sub

          loginInfoReturn = {
            "success":true,
            "landingPage":this.successPage,
            "user"       : {
              "displayName": displayName,
              "token"      : token
            }
          };

          this.userInfoService.storeUserInfo(JSON.stringify(loginInfoReturn.user));
        }
        else {
          loginInfoReturn = {
            "success":false
          };
        }
        loginDataSubject.next(loginInfoReturn);
      }, errResponse => {
        loginDataSubject.error(errResponse);
      });

    return loginDataSubject;
  }

  logout(navigatetoLogout=true): void {
    this.userInfoService.removeUserInfo();
    if(navigatetoLogout){
      this.router.navigate(["logout"]);
    }
  }
}
