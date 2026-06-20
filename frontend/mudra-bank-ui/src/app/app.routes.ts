import { Routes } from '@angular/router';
import { Accounts } from './accounts/accounts';
import { AccountDetail } from './account-detail/account-detail';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'accounts',
    pathMatch: 'full'
  },
  {
    path: 'accounts',
    component: Accounts
  },
  {
    path: 'account/:accountNumber',
    component: AccountDetail
  }
];