import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

import { User } from '../../../models'
import { MyErrorStateMatcher } from '../../../errors';

@Component({
  selector: 'app-login-create',
  templateUrl: './login-create.component.html',
  styleUrls: ['./login-create.component.css']
})
export class LoginCreateComponent {

  userCreateForm!: FormGroup;
  matcher = new MyErrorStateMatcher();

  constructor(
    public dialogRef: MatDialogRef<LoginCreateComponent>
  ) { 
    this.userCreateForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required]),
      businessTitle: new FormControl('', [Validators.required])
    });
  }

  onSubmit(): void {
    let user: User = {
      username: this.userCreateForm.get('username')?.value,
      password: this.userCreateForm.get('password')?.value,
      email: this.userCreateForm.get('email')?.value,
      phone: this.userCreateForm.get('phone')?.value,
      name: this.userCreateForm.get('name')?.value,
      businessTitle: this.userCreateForm.get('businessTitle')?.value
    }
    this.dialogRef.close(user);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}