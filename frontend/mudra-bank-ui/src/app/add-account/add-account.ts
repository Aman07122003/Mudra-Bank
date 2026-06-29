import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AccountService } from '../services/account';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-account',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-account.html',
  styleUrl: './add-account.css'
})
export class AddAccount {

  @Output() close = new EventEmitter<void>();
  @Output() refresh = new EventEmitter<void>();

  holderName = '';
  balance: number | null = null;
  isSaving = false;

  constructor(
    private accountService: AccountService,
    private snackBar: MatSnackBar
  ) {}

  save() {

  if (this.isSaving) {
    return;
  }

  this.isSaving = true;

  const payload = {
    accountHolderName: this.holderName,
    accountBalance: this.balance
  };

  this.accountService.createAccount(payload).subscribe({

    next: (res) => {

      this.snackBar.open(
        res.message,
        'Close',
        {
          duration: 3000
        }
      );

      this.isSaving = false;

      this.close.emit();
      this.refresh.emit();
    },

    error: (err) => {

      this.isSaving = false;

      this.snackBar.open(
        err.error?.message ?? 'Unable to create account.',
        'Close',
        {
          duration: 3000
        }
      );

      this.close.emit();
    }
  });
}


  cancel() {
    this.close.emit();
  }
}