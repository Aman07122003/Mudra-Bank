import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-account-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './account-detail.html',
  styleUrl: './account-detail.css'
})
export class AccountDetail {

  account = {
    accountNumber: 100001,
    holderName: 'Aman Pratap Singh',
    email: 'aman@gmail.com',
    mobile: '+91 9876543210',
    branch: 'Mudra Bank Delhi',
    balance: 50000
  };

  transactions = [
    {
      date: '2026-06-15',
      type: 'Credit',
      fromTo: 'Salary Account',
      amount: 25000
    },
    {
      date: '2026-06-16',
      type: 'Debit',
      fromTo: 'Amazon',
      amount: 3200
    },
    {
      date: '2026-06-17',
      type: 'Debit',
      fromTo: 'Swiggy',
      amount: 850
    }
  ];
}