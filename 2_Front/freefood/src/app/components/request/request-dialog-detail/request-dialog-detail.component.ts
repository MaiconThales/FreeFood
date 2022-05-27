import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-request-dialog-detail',
  templateUrl: './request-dialog-detail.component.html',
  styleUrls: ['./request-dialog-detail.component.css']
})
export class RequestDialogDetailComponent {

  constructor(
    public dialogRef: MatDialogRef<RequestDialogDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

}
