import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
1
import { LoginUser, User } from '../../../models';
import { MyErrorStateMatcher } from '../../../errors';
import { LoginCreateComponent } from '../login-create/login-create.component';
import { AuthService, TokenStorageService } from '../../../services';
import { environment as e } from '../../../../environments/environment.prod';

@Component({
  selector: 'app-login-authentication',
  templateUrl: './login-authentication.component.html',
  styleUrls: ['./login-authentication.component.css']
})
export class LoginAuthenticationComponent implements OnInit {

  loginForm!: FormGroup;
  matcher = new MyErrorStateMatcher();

  loginUser!: LoginUser;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  isLoggedIn = false;
  isLoginFailed = false;
  roles: string[] = [];

  constructor(
    public dialog: MatDialog,
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });

    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  onSubmit(): void {
    this.loginUser = {
      username: this.loginForm.controls['username'].value,
      password: this.loginForm.controls['password'].value
    }

    this.authService.login(this.loginUser).subscribe({
      next: data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().user.roles;
        this.router.navigate([e.REDIRECT_DASHBOARD]);
      },
      error: err => {
        this.errorMessage = err.message;
        this.isLoginFailed = true;
        this.snackBar.open('Login ou senha inválido', '', {
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          duration: 10000
        });
      }
    });
  }

  openDialogCreateUser(): void {
    this.resetForm();

    const dialogRef = this.dialog.open(LoginCreateComponent, {
      width: '500px',
      height: '350px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.createUser(result);
      }
    });
  }

  createUser(u: User): void {
    this.authService.register(u).subscribe({
      next: data => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    });
  }

  resetForm(): void {
    this.loginForm.reset();

    for (const key in this.loginForm.controls) {
      this.loginForm.controls[key].clearValidators();
      this.loginForm.controls[key].updateValueAndValidity();
    }
  }

}