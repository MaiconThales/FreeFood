import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { LoginUser, User } from '../../../models';
import { MyErrorStateMatcher } from '../../../errors';
import { LoginCreateComponent } from '../login-create/login-create.component';

@Component({
  selector: 'app-login-authentication',
  templateUrl: './login-authentication.component.html',
  styleUrls: ['./login-authentication.component.css']
})
export class LoginAuthenticationComponent implements OnInit {

  loginForm!: FormGroup;
  matcher = new MyErrorStateMatcher();

  loginUser!: LoginUser;

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  onSubmit(): void {
    this.loginUser = {
      username: this.loginForm.controls['username'].value,
      password: this.loginForm.controls['password'].value
    }

    console.log("Olha aqui o valor: ", this.loginUser);
  }

  openDialogCreateUser(): void {
    this.resetForm();

    const dialogRef = this.dialog.open(LoginCreateComponent, {
      width: '500px',
      height: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.createUser(result);
      } else {
        console.log("Nada cadastrado");
      }
    });
  }

  createUser(u: User): void {
    console.log("Usuário criado: ", u);
  }

  resetForm(): void {
    this.loginForm.reset();

    for (const key in this.loginForm.controls) {
      this.loginForm.controls[key].clearValidators();
      this.loginForm.controls[key].updateValueAndValidity();
    }
  }

}