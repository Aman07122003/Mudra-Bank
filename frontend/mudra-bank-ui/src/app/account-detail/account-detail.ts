import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, ActivatedRoute } from '@angular/router';
import { TransferService } from '../services/transfers';
import { AccountService } from '../services/account';
import { ChangeDetectorRef } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-account-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    FormsModule
  ],
  templateUrl: './account-detail.html',
  styleUrl: './account-detail.css'
})
export class AccountDetail implements OnInit {

  account: any;
  transactions: any[] = [];

  currentPage = 0;
  pageSize = 5;

  totalPages = 0;
  totalElements = 0;
  last = false;
  showTransferModal = false;

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private transferService: TransferService,
    private cdr: ChangeDetectorRef,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const accountNumber = Number(
      this.route.snapshot.paramMap.get('accountNumber')
    );

    this.loadAccount(accountNumber);
    this.loadTransactions(accountNumber);
  }

  loadAccount(accountNumber: number): void {
    this.accountService.getAccount(accountNumber).subscribe({
      next: (res) => {
        this.account = res.bankAccount
         // .find((a: any) => a.accountNumber === accountNumber);

        this.cdr.detectChanges();
      },
      error: (err) => {

        console.error(err);

        this.snackBar.open(
          err.error?.message ?? 'Unable to load account details.',
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

  loadTransactions(accountNumber: number): void {
    this.transferService.getAccountTransactions(accountNumber, this.currentPage, this.pageSize)
      .subscribe({
        next: (res) => {

          this.transactions = res.transactions;
          this.currentPage = res.page;
          this.pageSize = res.size;
          this.totalPages = res.totalPages;
          this.totalElements = res.totalElements;
          this.last = res.last;
          this.cdr.detectChanges();
          console.log('Transactions loaded', this.transactions);
        },
        error: (err) => {

          console.error(err);

          this.snackBar.open(
            err.error?.message ?? 'Unable to load transaction history.',
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

  previousPage() {

  if (this.currentPage > 0) {

    this.currentPage--;

    this.loadTransactions(this.account.accountNumber);
  }
}

nextPage() {

  if (!this.last) {

    this.currentPage++;

    this.loadTransactions(this.account.accountNumber);
  }
}

changePageSize() {

  this.currentPage = 0;

  this.loadTransactions(this.account.accountNumber);
}

downloadCsv(): void {

  this.transferService
      .downloadTransactionsCsv(this.account.accountNumber)
      .subscribe(blob => {

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');

        a.href = url;
        a.download = 'transactions.csv';

        a.click();

        window.URL.revokeObjectURL(url);
      });
}

downloadPdf(): void {

  this.transferService
      .downloadTransactionsPdf(this.account.accountNumber)
      .subscribe(blob => {

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');

        a.href = url;
        a.download = 'transactions.pdf';

        a.click();

        window.URL.revokeObjectURL(url);
      });
}
  
}