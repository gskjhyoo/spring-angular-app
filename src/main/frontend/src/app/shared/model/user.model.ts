export class User {
  public id?: any;
  public login?: string;
  public email?: string;
  public name?: string;
  public authorities?: any[];
  public createdBy?: string;
  public createdDate?: Date;
  public lastModifiedBy?: string;
  public lastModifiedDate?: Date;
  public activated?: Boolean;

  constructor(
    id?: any,
    email?: string,
    name?: string,
    authorities?: any[],
    activated?: Boolean,
    createdBy?: string,
    createdDate?: Date,
    lastModifiedBy?: string,
    lastModifiedDate?: Date,
  ) {
    this.id = id ? id : null;
    this.email = email ? email : null;
    this.name = name ? name : null;
    this.authorities = authorities ? authorities : null;
    this.activated = activated ? activated : false;
    this.createdBy = createdBy ? createdBy : null;
    this.createdDate = createdDate ? createdDate : null;
    this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
    this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
  }
}
