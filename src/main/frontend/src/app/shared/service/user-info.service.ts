import { Injectable } from '@angular/core';
import { tokenNotExpired, JwtHelper } from 'angular2-jwt';

export interface UserInStorage{
  displayName:string;
  token:string;
}

export interface LoginInfoInStorage{
  success:boolean;
  landingPage?:string;
  user?:UserInStorage;
}

@Injectable()
export class UserInfoService {

  jwtHelper: JwtHelper = new JwtHelper();
  public currentUserKey:string="currentUser";
  public storage:Storage = localStorage;

  constructor() {}

  storeUserInfo(userInfoString:string) {
    this.storage.setItem(this.currentUserKey, userInfoString);
  }

  removeUserInfo() {
    this.storage.removeItem(this.currentUserKey);
  }

  getUserInfo():UserInStorage|null {
    try{
      let userInfoString:string = this.storage.getItem(this.currentUserKey);
      if (userInfoString) {
        let userObj:UserInStorage = JSON.parse(this.storage.getItem(this.currentUserKey));
        return userObj;
      }
      else{
        return null;
      }
    }
    catch (e) {
      return null;
    }
  }

  isLoggedIn():boolean{
    if (this.storage.getItem(this.currentUserKey)) {
      try {
        let userObj:UserInStorage = this.getUserInfo();
        return userObj != null && !this.jwtHelper.isTokenExpired(userObj.token);
      }
      catch (e) {
        console.log(e.message);
        return false;
      }
    }
    return false;
  }

  getUserName():string{
    let userObj:UserInStorage = this.getUserInfo();
    if (userObj!== null){
      return userObj.displayName
    }
    return "no-user";
  }

  getStoredToken():string|null {
    let userObj:UserInStorage = this.getUserInfo();
    if (userObj !== null){
      return userObj.token;
    }
    return null;
  }

  getAuthorities():any[] {
    let userObj:UserInStorage = this.getUserInfo();
    if (userObj!== null){
      let rawToken = userObj.token;
      let decodeToken = this.jwtHelper.decodeToken(rawToken);
      let rolesStr: string = decodeToken.authorities;
      return rolesStr.split(",")
    }
    return null;
  }

  hasAnyAuthority(authorities: string[]): boolean {
    let roleArray: String[] = this.getAuthorities();
    if (roleArray == null) {
      return false;
    }
    for (let i = 0; i < authorities.length; i++) {
      if (roleArray.indexOf(authorities[i]) !== -1) {
        return true;
      }
    }
    return false;
  }
}
