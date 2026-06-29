export interface Transfer {
  transactionNumber: number;
  sourceAccountNumber: number;
  targetAccountNumber: number;
  amount: number;
  transactionDate: string;
}

export interface TransferDetail {
  transactionNumber: number;
  sourceAccountNumber: number;
  targetAccountNumber: number;
  fromToAccountNumber: String;
  amount: number;
  transactionDate: string;
}

export interface AccountTransactionDetail {
    transactionNumber: number;
    transferType: TransferType;
    fromToAccountNumber: string;
    amount: number;
    transactionDate: string;
}

export enum TransferType {
  CREDIT = 'CREDIT',
  DEBIT = 'DEBIT'
}

export interface CreateTransferRequest {
  amount: number;
  destinationAccountNumber: number;
  sourceAccountNumber: number;
}

export interface GetAccountTransactionsRequest {
    accountNumber: number;  
}

export interface GetAccountTransactionsResponse {
    transactions: AccountTransactionDetail[];

    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    last: boolean;
}

export interface CreateTransferResponse {
    message: string;
    transactionNumber: number;
}
