import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateTransferRequest, CreateTransferResponse, GetAccountTransactionsResponse } from '../models/transfer.model';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  private baseUrl = 'http://localhost:8080/transfers';

  constructor(
    private http: HttpClient
  ) {}

  createTransfer(
    request: CreateTransferRequest
  ) {
    return this.http.put<CreateTransferResponse>(
      this.baseUrl,
      request
    );
  }

  getAccountTransactions(
    accountNumber: number,
    page: number,
    size: number
  ): Observable<GetAccountTransactionsResponse> {

    return this.http.get<GetAccountTransactionsResponse>(
      `${this.baseUrl}/account/${accountNumber}?page=${page}&size=${size}`
    );
  }

  downloadTransactionsCsv(accountNumber: number) {
  return this.http.get(
    `${this.baseUrl}/account/${accountNumber}/export/csv`,
    {
      responseType: 'blob'
    }
  );
}

  downloadTransactionsPdf(accountNumber: number) {
  return this.http.get(
    `${this.baseUrl}/account/${accountNumber}/export/pdf`,
    {
      responseType: 'blob'
    }
  );
}
}