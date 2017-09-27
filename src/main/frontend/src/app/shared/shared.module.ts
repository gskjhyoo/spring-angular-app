import { NgModule } from '@angular/core';

import { ApiRequestService } from './service/api-request.service';
import { LoginService } from './service/login.service';
import { UserInfoService } from './service/user-info.service';
import { NgbModule, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    NgbModule.forRoot()
  ],
  providers: [
    LoginService,
    ApiRequestService,
    LoginService,
    UserInfoService,
    NgbActiveModal
  ],
  exports: [
    NgbModule
  ]
})
export class SharedModule {}
