import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Transaction } from '../transaction/transaction';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-account',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './add-account.html',
  styleUrl: './add-account.css'
})
export class AddAccount {

  @Output() close = new EventEmitter<void>();

  accountNumber = 0;
  holderName = '';
  balance = 0;

  save() {

    console.log({
      accountNumber: this.accountNumber,
      holderName: this.holderName,
      balance: this.balance
    });

    this.close.emit();
  }

  cancel() {
    this.close.emit();
  }
}