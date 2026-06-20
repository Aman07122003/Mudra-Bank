import { Component } from '@angular/core';
import { Account } from '../models/account.model';
import { CommonModule } from '@angular/common';
import { AddAccount } from '../add-account/add-account';
import { Transaction } from '../transaction/transaction';
import { Router } from '@angular/router';


@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [
    CommonModule,
    AddAccount,
    Transaction
  ],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts {

  constructor(private router: Router) {}
  accounts: Account[] = [
    {
      accountNumber: 100001,
      holderName: 'Aman Pratap Singh',
      balance: 50000
    },
    {
      accountNumber: 100002,
      holderName: 'Rahul Sharma',
      balance: 25000
    },
    {
      accountNumber: 100003,
      holderName: 'Priya Verma',
      balance: 75000
    },
    {
      accountNumber: 100001,
      holderName: 'Aman Pratap Singh',
      balance: 50000
    },
    {
      accountNumber: 100002,
      holderName: 'Rahul Sharma',
      balance: 25000
    },
    {
      accountNumber: 100003,
      holderName: 'Priya Verma',
      balance: 75000
    },
    {
      accountNumber: 100001,
      holderName: 'Aman Pratap Singh',
      balance: 50000
    },
    {
      accountNumber: 100002,
      holderName: 'Rahul Sharma',
      balance: 25000
    },
    {
      accountNumber: 100003,
      holderName: 'Priya Verma',
      balance: 75000
    },
    {
      accountNumber: 100001,
      holderName: 'Aman Pratap Singh',
      balance: 50000
    },
    {
      accountNumber: 100002,
      holderName: 'Rahul Sharma',
      balance: 25000
    },
    {
      accountNumber: 100003,
      holderName: 'Priya Verma',
      balance: 75000
    },
    {
      accountNumber: 100001,
      holderName: 'Aman Pratap Singh',
      balance: 50000
    },
    {
      accountNumber: 100002,
      holderName: 'Rahul Sharma',
      balance: 25000
    },
    {
      accountNumber: 100003,
      holderName: 'Priya Verma',
      balance: 75000
    },
    {
      accountNumber: 100001,
      holderName: 'Aman Pratap Singh',
      balance: 50000
    },
    {
      accountNumber: 100002,
      holderName: 'Rahul Sharma',
      balance: 25000
    },
    {
      accountNumber: 100003,
      holderName: 'Priya Verma',
      balance: 75000
    },
    {
      accountNumber: 100001,
      holderName: 'Aman Pratap Singh',
      balance: 50000
    },
    {
      accountNumber: 100002,
      holderName: 'Rahul Sharma',
      balance: 25000
    },
    {
      accountNumber: 100003,
      holderName: 'Priya Verma',
      balance: 75000
    },
    {
      accountNumber: 100001,
      holderName: 'Aman Pratap Singh',
      balance: 50000
    },
    {
      accountNumber: 100002,
      holderName: 'Rahul Sharma',
      balance: 25000
    },
    {
      accountNumber: 100003,
      holderName: 'Priya Verma',
      balance: 75000
    }
  ];

showTransactionModal = false;
  showAddAccountModal = false;

  selectedAccount!: Account;
  transactionType: 'DEBIT' | 'CREDIT' = 'DEBIT';

   openAccount(account: Account): void {
    this.router.navigate([
      '/account',
      account.accountNumber
    ]);
  }

  debit(account: Account): void {
    this.selectedAccount = account;
    this.transactionType = 'DEBIT';
    this.showTransactionModal = true;
  }

  credit(account: Account): void {
    this.selectedAccount = account;
    this.transactionType = 'CREDIT';
    this.showTransactionModal = true;
  }

  addAccount(): void {
  console.log('Add Account Clicked');
  this.showAddAccountModal = true;
}

  closeTransactionModal() {
    this.showTransactionModal = false;
  }

  closeAddAccountModal() {
    this.showAddAccountModal = false;
  }
}
