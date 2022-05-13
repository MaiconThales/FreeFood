import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

import { SignupRequest } from '../../../models'
import { environment as e } from '../../../../environments/environment.prod';
import { MyErrorStateMatcher } from 'src/app/shared';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-login-create',
  templateUrl: './login-create.component.html',
  styleUrls: ['./login-create.component.css']
})
export class LoginCreateComponent {

  userCreateForm!: FormGroup;
  matcher = new MyErrorStateMatcher();
  language: string[] = e.LANGUAGE_OPTIONS;

  labelClose!: string;
  labelSave!: string;

  constructor(
    public dialogRef: MatDialogRef<LoginCreateComponent>,
    private translate: TranslateService
  ) { 
    this.setLabels();
    this.createForm();
  }

  setLabels(): void {
    this.labelClose = this.translate.instant('GLOBAL_WORD.WORD_CLOSE');
    this.labelSave = this.translate.instant('GLOBAL_WORD.WORD_SAVE');
  }

  createForm(): void {
    this.userCreateForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      language: new FormControl('', [Validators.required])
    });
  }

  onSubmit(): void {
    let user: SignupRequest = {
      username: this.userCreateForm.get('username')?.value,
      password: this.userCreateForm.get('password')?.value,
      email: this.userCreateForm.get('email')?.value,
      language: this.userCreateForm.get('language')?.value
    }
    this.dialogRef.close(user);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}