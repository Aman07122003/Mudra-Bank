import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Account } from '../models/account.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transaction',
  imports: [FormsModule],
  standalone: true,
  templateUrl: './transaction.html',
  styleUrl: './transaction.css'
})
export class Transaction {

  @Input() account!: Account;
  @Input() type!: 'DEBIT' | 'CREDIT';

  @Output() close = new EventEmitter<void>();

  amount = 0;

  submit() {

    if(this.type === 'DEBIT'){
      this.account.balance -= this.amount;
    } else {
      this.account.balance += this.amount;
    }

    this.close.emit();
  }

  cancel() {
    this.close.emit();
  }
}