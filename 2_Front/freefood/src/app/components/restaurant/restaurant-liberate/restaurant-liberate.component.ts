import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';

import { MyErrorStateMatcher } from 'src/app/shared';

@Component({
  selector: 'app-restaurant-liberate',
  templateUrl: './restaurant-liberate.component.html',
  styleUrls: ['./restaurant-liberate.component.css']
})
export class RestaurantLiberateComponent {

  userForm!: FormGroup;
  matcher = new MyErrorStateMatcher();

  labelClose!: string;
  labelSave!: string;

  constructor(
    public dialogRef: MatDialogRef<RestaurantLiberateComponent>,
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
    this.userForm = new FormGroup({
      username: new FormControl('', [Validators.required])
    });
  }

  onSubmit(): void {
    let username = this.userForm.get('username')?.value;
    this.dialogRef.close(username);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
