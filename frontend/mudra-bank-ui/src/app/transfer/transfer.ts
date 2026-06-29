import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BankAccount } from '../models/account.model';
import { AccountService } from '../services/account';
import { CreateTransferRequest } from '../models/transfer.model';
import { TransferService } from '../services/transfers';
import { MatSnackBar } from '@angular/material/snack-bar';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './transfer.html',
  styleUrl: './transfer.css'
})
export class Transfer {

  @Input() account!: BankAccount;
  @Input() type!: 'DEBIT' | 'CREDIT';

  @Output() close = new EventEmitter<void>();
  @Output() refresh = new EventEmitter<void>();

  holderName = '';
  balance: number | null = null;
  amount: number | null = null;
  otherAccountNumber: number | null = null; 
  isSubmitting = false;

  constructor(
    private transferService: TransferService,
    private snackBar: MatSnackBar
  ) {}

  
submit(): void {

  if (this.isSubmitting) {
    return;
  }

  if (this.amount === null || this.otherAccountNumber === null) {
    this.snackBar.open(
      'Please enter all fields.',
      'Close',
      {
        duration: 3000,
        horizontalPosition: 'right',
        verticalPosition: 'top'
      }
    );
    return;
  }

  this.isSubmitting = true;

  const request: CreateTransferRequest = {
    amount: this.amount,

    sourceAccountNumber:
      this.type === 'DEBIT'
        ? this.account.accountNumber
        : this.otherAccountNumber,

    destinationAccountNumber:
      this.type === 'CREDIT'
        ? this.account.accountNumber
        : this.otherAccountNumber
  };

  this.transferService
    .createTransfer(request)
    .pipe(
      finalize(() => {
        this.isSubmitting = false;
      })
    )
    .subscribe({
      next: (res) => {

        this.snackBar.open(
          res.message,
          'Close',
          {
            duration: 3000,
            horizontalPosition: 'right',
            verticalPosition: 'top'
          }
        );

        this.close.emit();
        this.refresh.emit();
      },

      error: (err) => {

        this.snackBar.open(
          err.error?.message ?? 'Transfer failed.',
          'Close',
          {
            duration: 3000,
            horizontalPosition: 'right',
            verticalPosition: 'top'
          }
        );

        this.close.emit();
      }
    });
}

cancel(): void {
  this.close.emit();
}
}