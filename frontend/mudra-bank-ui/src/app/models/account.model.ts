export interface BankAccount {
  accountNumber: number;
  holderName: string;
  balance: number;
}

export interface BankAccountDetail {
  accountNumber: number;
  holderName: string;
  balance: number;
}

export interface CreateBankAccountRequest {
  accountHolderName: string;
  accountBalance: number;
}

export interface CreateBankAccountResponse {
  accountHolderName: string;
  message: string;
}

export interface GetBankAccountsRequest {
  bankAccountNumber: number;
}

export interface GetBankAccountsResponse {
  bankAccounts: BankAccount[];

  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}

export interface GetBankAccountDetailRequest {
  bankAccountNumber: number;
}

export interface GetBankAccountDetailResponse {
  bankAccount: BankAccountDetail;
}