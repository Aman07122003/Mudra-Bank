import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GetBankAccountDetailResponse } from '../models/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl = 'http://localhost:8080/accounts';

  constructor(private http: HttpClient) {}

  getAllAccounts(page: number, size: number) {

  return this.http.get<any>(
    `${this.baseUrl}?page=${page}&size=${size}`
  );

}

getAccount(accountNumber: number) {
  return this.http.get<GetBankAccountDetailResponse>(
    `${this.baseUrl}/${accountNumber}`
  );
}

  createAccount(payload: any): Observable<any> {
    return this.http.put<any>(this.baseUrl, payload);
  }

  downloadAccountsCsv() {
  return this.http.get(
    `${this.baseUrl}/export/csv`,
    {
      responseType: 'blob'
    }
  );
}

downloadAccountsPdf() {
  return this.http.get(
    `${this.baseUrl}/export/pdf`,
    {
      responseType: 'blob'
    }
  );
}
}