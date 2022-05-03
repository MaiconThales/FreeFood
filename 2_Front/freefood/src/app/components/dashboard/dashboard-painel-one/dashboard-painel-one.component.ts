import { Component, OnInit } from '@angular/core';

import { UserService } from '../../../services';
import { EventBusService } from '../../../shared/event-bus.service';
import { EventData } from 'src/app/models';

@Component({
  selector: 'app-dashboard-painel-one',
  templateUrl: './dashboard-painel-one.component.html',
  styleUrls: ['./dashboard-painel-one.component.css']
})
export class DashboardPainelOneComponent implements OnInit {

  content?: string;

  constructor(
    private userService: UserService,
    private eventBusService: EventBusService
  ) { }

  ngOnInit(): void {
    
  }

  showTest(): void {
    this.userService.listAllUser().subscribe({
      next: data => {
        console.log("Resultado: ", data);
      },
      error: err => {
        this.content = err.error.message || err.error || err.message;

        if (err.status === 403)
          this.eventBusService.emit(new EventData('logout', null));
        console.log("Error");
      }
    });
  }

}
