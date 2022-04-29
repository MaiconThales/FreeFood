import { Component, OnInit } from '@angular/core';

import  { TokenStorageService } from '../../../services';

@Component({
  selector: 'app-dashboard-painel-one',
  templateUrl: './dashboard-painel-one.component.html',
  styleUrls: ['./dashboard-painel-one.component.css']
})
export class DashboardPainelOneComponent implements OnInit {

  currentUser: any;

  constructor(private token: TokenStorageService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    console.log("Opa: ", this.currentUser);
  }

}
