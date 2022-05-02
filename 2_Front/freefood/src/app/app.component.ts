import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatSidenav } from '@angular/material/sidenav';

import { TokenStorageService, LayoutMenuService } from './services';
import { environment as e } from '../environments/environment.prod';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Freefood';

  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  showMenu: boolean = false;

  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;
  email?: string;
  avatarImage!: string;

  constructor(
    private tokenStorageService: TokenStorageService,
    private router: Router,
    private layoutMenuService: LayoutMenuService
  ) {
    
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    this.layoutMenuService.showMenu.subscribe(show => {
      this.showMenu = show;
    });
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.user.roles;
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');
      this.username = user.user.username;
      this.email = user.user.email;
      this.avatarImage = "../assets/img/avatar/avatar.jpg";
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