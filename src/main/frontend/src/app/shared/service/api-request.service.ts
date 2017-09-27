import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';
import { Router } from '@angular/router';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { UserInfoService } from './user-info.service';

@Injectable()
export class ApiRequestService {

  private baseApiPath:string = "http://localhost:8080/";

  constructor(
    private http: Http,
    private router:Router,
    private userInfoService:UserInfoService
  ) { }

  appendAuthHeader():Headers {
    let headers = new Headers({'Content-Type': 'application/json'});
    let token = this.userInfoService.getStoredToken();
    if (token !==null) {
      headers.append("Authorization", token);
    }
    return headers;
  }

  getRequestOptions(requestMethod, url:string, urlParam?:URLSearchParams, body?:Object):RequestOptions {
    let options = new RequestOptions({
      headers: this.appendAuthHeader(),
      method : requestMethod,
      url    : this.baseApiPath + url
    });
    if (urlParam){
      options = options.merge({ params: urlParam});
    }
    if (body){
      options = options.merge({body: JSON.stringify(body)});
    }
    return options;
  }

  get(url:string, urlParams?:URLSearchParams):Observable<any>{
    let me = this;
    let requestOptions = this.getRequestOptions(RequestMethod.Get, url, urlParams);
    return this.http.request(new Request(requestOptions))
      .catch(function(error:any){
        if (error.status === 401 || error.status === 403){
          me.router.navigate(['/logout']);
        }
        return Observable.throw(error || 'Server error')
      });
  }

  post(url:string, body:Object):Observable<any>{
    let me = this;
    let requestOptions = this.getRequestOptions(RequestMethod.Post, url, undefined, body);
    return this.http.request(new Request(requestOptions))
      .catch(function(error:any){
        return Observable.throw(error || 'Server error')
      });
  }

  put(url:string, body:Object):Observable<any>{
    let me = this;
    let requestOptions = this.getRequestOptions(RequestMethod.Put, url, undefined, body);
    return this.http.request(new Request(requestOptions))
      .catch(function(error:any){
        if (error.status === 401){
          me.router.navigate(['/logout']);
        }
        return Observable.throw(error || 'Server error')
      });
  }

  delete(url:string):Observable<any>{
    let me = this;
    let requestOptions = this.getRequestOptions(RequestMethod.Delete, url);
    return this.http.request(new Request(requestOptions))
      .catch(function(error:any){
        if (error.status === 401){
          me.router.navigate(['/logout']);
        }
        return Observable.throw(error || 'Server error')
      });
  }
}
