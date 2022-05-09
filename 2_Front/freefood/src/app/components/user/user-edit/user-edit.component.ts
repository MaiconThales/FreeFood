import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

import { User } from 'src/app/models';
import { TokenStorageService, UserService } from 'src/app/services';
import { MyErrorStateMatcher } from '../../../errors';
import { EventBusService } from '../../../shared/event-bus.service';
import { EventData } from 'src/app/models';
import { environment as e } from '../../../../environments/environment.prod';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  userEditForm!: FormGroup;
  matcher = new MyErrorStateMatcher();
  
  user!: User;
  language: string[] = e.LANGUAGE_OPTIONS;

  constructor(
    private userService: UserService,
    private token: TokenStorageService,
    private snackBar: MatSnackBar,
    private eventBusService: EventBusService,
    private translate: TranslateService
  ) { }

  ngOnInit(): void {
    this.createFormUser(undefined, 1);
    this.getDataUser();
  }

  getDataUser() {
    this.userService.getDataUser(this.token.getIdUser()).subscribe({
      next: data => {
        this.createFormUser(data, 2);
      },
      error: err => {
        this.functionBusService(err);
        this.snackBar.open(this.translate.instant('GLOBAL_WORD.WORD_MSG_SERVER_ERROR'), 'Ok', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      }
    });
  }

  createFormUser(user: any, type: number): void {
    switch (type) {
      case 1:
        this.userEditForm = new FormGroup({
          id: new FormControl(''),
          username: new FormControl('', [Validators.required]),
          password: new FormControl(''),
          email: new FormControl('', [Validators.required]),
          language: new FormControl('', [Validators.required]),
          phone: new FormControl(''),
          name: new FormControl('')
        });
        break;
      case 2:
        this.userEditForm = new FormGroup({
          id: new FormControl(user.id),
          username: new FormControl(user.username, [Validators.required]),
          password: new FormControl(user.password),
          email: new FormControl(user.email, [Validators.required]),
          language: new FormControl(user.language, [Validators.required]),
          phone: new FormControl(user.phone),
          name: new FormControl(user.name)
        });
        break;
    }
  }

  onSubmit(): void {
    this.user = {
      id: this.userEditForm.get('id')?.value,
      username: this.userEditForm.get('username')?.value,
      password: this.userEditForm.get('password')?.value,
      email: this.userEditForm.get('email')?.value,
      language: this.userEditForm.get('language')?.value,
      name: this.userEditForm.get('name')?.value,
      phone: this.userEditForm.get('phone')?.value,
      roles: []
    };
    if(this.user != null) {
      this.userService.alterDataUser(this.user).subscribe({
        next: () => {
          this.snackBar.open(this.translate.instant('GLOBAL_WORD.WORD_MSG_ALTER_SUCCESS'), 'Ok', {
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            duration: 10000
          });
        },
        error: err => {
          this.functionBusService(err);
          this.snackBar.open(this.translate.instant('GLOBAL_WORD.WORD_MSG_SERVER_ERROR'), 'Ok', {
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            duration: 10000
          });
        }
      });
    }
  }

  private functionBusService(err: any): void {
    if (err.status === 403) {
      this.eventBusService.emit(new EventData('logout', null));
    }
  }

}
