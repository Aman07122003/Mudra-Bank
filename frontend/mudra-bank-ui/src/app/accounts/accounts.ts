import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../models/account.model';
import { CommonModule } from '@angular/common';
import { AddAccount } from '../add-account/add-account';
import { Transfer} from '../transfer/transfer';
import { Router } from '@angular/router';
import { AccountService } from '../services/account';
import { ChangeDetectorRef } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [
    CommonModule,
    AddAccount,
    Transfer,
    FormsModule
  ],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts implements OnInit {

  constructor(
    private router: Router,
    private accountService: AccountService,
    private cdr: ChangeDetectorRef,
    private snackBar: MatSnackBar
  ) {}

  accounts: BankAccount[] = [];

  showTransactionModal = false;
  showAddAccountModal = false;
  currentPage = 0;
  pageSize = 5;

  totalPages = 0;
  totalElements = 0;
  last = false;

  selectedAccount!: BankAccount;
  transactionType: 'DEBIT' | 'CREDIT' = 'DEBIT';

  ngOnInit(): void {
  this.loadAccounts();
}
  loadAccounts(): void {
  this.accountService.getAllAccounts(this.currentPage, this.pageSize).subscribe({
    next: (res) => {
      console.log('Accounts fetched successfully', res);
      this.accounts = res.bankAccounts;
      this.totalPages = res.totalPages;
      this.totalElements = res.totalElements;
      this.last = res.last;

      console.log(res);

      this.cdr.detectChanges();
    },
    error: (err) => {

      console.error(err);

      this.snackBar.open(
        err.error?.message ?? 'Unable to load accounts.',
        'Close',
        {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top'
        }
      );
    }
  });
}

  openAccount(account: BankAccount): void {
    this.router.navigate(['/account', account.accountNumber]);
  }

  debit(account: BankAccount): void {
    this.selectedAccount = account;
    this.transactionType = 'DEBIT';
    this.showTransactionModal = true;
  } 

  credit(account: BankAccount): void {
    this.selectedAccount = account;
    this.transactionType = 'CREDIT';
    this.showTransactionModal = true;
  }

  addAccount(): void {
    this.showAddAccountModal = true;
  }

  previousPage() {

  if (this.currentPage > 0) {

    this.currentPage--;

    this.loadAccounts();
  }

}

nextPage() {

  if (!this.last) {

    this.currentPage++;

    this.loadAccounts();
  }

}

changePageSize() {

  this.currentPage = 0;

  this.loadAccounts();

}

  closeTransactionModal() {
    this.showTransactionModal = false;
  }

  closeAddAccountModal() {
    this.showAddAccountModal = false;
  }

  trackByAccountNumber(index: number, account: BankAccount): number {
  return account.accountNumber;
}

downloadCsv(): void {

  this.accountService
      .downloadAccountsCsv()
      .subscribe((blob: Blob) => {

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');

        a.href = url;
        a.download = 'accounts.csv';

        a.click();

        window.URL.revokeObjectURL(url);
      });
}

downloadPdf(): void {

  this.accountService
      .downloadAccountsPdf()
      .subscribe((blob: Blob) => {

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');

        a.href = url;
        a.download = 'accounts.pdf';

        a.click();

        window.URL.revokeObjectURL(url);
      });
}
}