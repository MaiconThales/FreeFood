import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
1
import { LoginUser, User, UserAuth } from '../../../models';
import { MyErrorStateMatcher } from '../../../errors';
import { LoginCreateComponent } from '../login-create/login-create.component';
import { AuthService, TokenStorageService, LayoutMenuService } from '../../../services';
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
  errorMessage = '';
  userInfo!: UserAuth;

  constructor(
    public dialog: MatDialog,
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private snackBar: MatSnackBar,
    private layoutMenuService: LayoutMenuService
  ) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
    if (this.tokenStorage.getToken()) {
      this.layoutMenuService.alterValue(true);
      this.userInfo = this.tokenStorage.getUser().user;
      this.layoutMenuService.setValueUser(this.userInfo);
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
        this.userInfo = data.user;
        this.layoutMenuService.alterValue(true);
        this.layoutMenuService.setValueUser(this.userInfo);
        this.router.navigate([e.REDIRECT_DASHBOARD]);

      },
      error: err => {
        this.resetForm();
        this.errorMessage = err.message;
        this.layoutMenuService.alterValue(false);
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
      next: data => {},
      error: err => {
        this.errorMessage = err.error.message;
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