import { Injectable, OnInit } from '@angular/core';
import { Quiz } from '../model/quiz';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, catchError, map, of, pipe, tap } from 'rxjs';
import { AuthResponse } from '../model/auth-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit {
  isAuthenticate: boolean = false;
  private apiUrl = 'http://localhost:8080';
  private headers: HttpHeaders;
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  login(userCredentials: string): Observable<boolean> {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${this.apiUrl}/authenticate`, userCredentials, { headers: this.headers })
      .pipe(
        tap((res: any) => {
          console.log(res);
          localStorage.clear();
          localStorage.setItem('accessToken', res.accessToken);
          localStorage.setItem('email', res.email);
          localStorage.setItem('username', res.username);
          this.isAuthenticate = true;
          console.log(this.isAuthenticate);
        }),
        catchError((err) => {
          if (err.status == '401') {
            console.log(`Error occurred: ${err}`);
          }
          if (err.status == '500') {
            console.log(`Error occurred: ${err}`);
          }
          this.isAuthenticate = false;
          return of(false); // Return Observable with false in case of error
        }),
        map(() => this.isAuthenticate) // Map the value to true when successful
      );
  }

  registartion(userCredentials: string): Observable<boolean> {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${this.apiUrl}/registration`, userCredentials,
      { headers: this.headers }).pipe(
        tap((res) => {
          console.log(res);
          return of(true);
        }),
        catchError((err) => {
          console.error(`Error: ${err}`);
          return of(false);
        }),
      );
  }

  isAuthenticated(): Observable<boolean> {
    // localStorage.setItem('accessToken', 'sdsdsdsd')
    this.headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<boolean>(`${this.apiUrl}/isAuthenticated`, {headers: this.headers})
    .pipe(
      tap((res) => {
        return of(true);
      }),
       catchError((err) => {
        console.error(`Error: ${err}`);
        return of(false);
       }),
    );
  }
}
