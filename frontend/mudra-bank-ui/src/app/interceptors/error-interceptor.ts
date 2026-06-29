import { Injectable } from '@angular/core';
import {
  HttpErrorResponse,
  HttpInterceptor,
  HttpRequest,
  HttpHandler
} from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, throwError } from 'rxjs';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private snackBar: MatSnackBar) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    return next.handle(req).pipe(

      catchError((error: HttpErrorResponse) => {

        this.snackBar.open(
          error.error.message,
          'Close',
          { duration: 3000 }
        );

        return throwError(() => error);

      })

    );

  }
}