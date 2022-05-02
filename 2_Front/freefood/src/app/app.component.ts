import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatSidenav } from '@angular/material/sidenav';

import { TokenStorageService, LayoutMenuService } from './services';
import { environment as e } from '../environments/environment.prod';
import { UserAuth } from './models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  showMenu: boolean = false;

  avatarImage!: string;
  infoUser: UserAuth = {
    username: '',
    email: '',
    roles: []
  };

  constructor(
    private tokenStorageService: TokenStorageService,
    private router: Router,
    private layoutMenuService: LayoutMenuService
  ) {
    
  }

  ngOnInit(): void {
    this.layoutMenuService.showMenu.subscribe(show => {
      this.showMenu = show;
    });
    this.layoutMenuService.user.subscribe(u => {
      this.infoUser = u;
    });
    if (!!this.tokenStorageService.getToken()) {
      const userInfo = this.tokenStorageService.getUser();
      this.avatarImage = "../assets/img/avatar/avatar.jpg";
      this.infoUser.username = userInfo.user.username;
      this.infoUser.email = userInfo.user.email;

      this.layoutMenuService.setValueUser(this.infoUser);
    }
  }

  redirectComponent(component: string): void {
    switch (component) {
      case e.REDIRECT_DASHBOARD:
        this.router.navigate([e.REDIRECT_DASHBOARD]);
        this.sidenav.close();
        break;
      case e.REDIRECT_RESTAURANT:
        this.router.navigate([e.REDIRECT_RESTAURANT]);
        this.sidenav.close();
        break;
      case e.REDIRECT_MENU:
        this.router.navigate([e.REDIRECT_MENU]);
        this.sidenav.close();
        break;
      case e.REDIRECT_RESQUEST:
        this.router.navigate([e.REDIRECT_RESQUEST]);
        this.sidenav.close();
        break;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.router.navigate([e.REDIRECT_AUTHENTICATION]);
    this.sidenav.close();
  }

}