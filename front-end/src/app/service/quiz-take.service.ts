import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Take } from '../model/take';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuizTakeService {
  private apiUrl = 'http://localhost:8080/api/v1/take'; // Replace with your API endpoint

  private headers: HttpHeaders;
  constructor(private http: HttpClient) {}

  postTake(take: Take): Observable<HttpResponse<Take>> {
    this.headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<HttpResponse<Take>>(`${this.apiUrl}/saveTake`, take, {headers: this.headers});
  }

  getAllTakes(): Observable<Take[]> {
    this.headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`);
    return this.http.get<Take[]>(`${this.apiUrl}/getAllTakes`, {headers: this.headers});
  }

  getTakeById(takeId: string): Observable<Take> {
    this.headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`);
    return this.http.get<Take>(`${this.apiUrl}/getTakeById/${takeId}`, {headers: this.headers});
  }

  getTakeByUsername(username: string): Observable<Take> {
    this.headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`);
    return this.http.get<Take>(`${this.apiUrl}/getTakeByUsername/${username}`, {headers: this.headers});
  }

  getTakeByCategoryName(category: string): Observable<Take> {
    this.headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`);
    return this.http.get<Take>(`${this.apiUrl}/getTakeByCategoryName/${category}`, {headers: this.headers});
  }

  deleteTake(takeId: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/deleteTakeById/${takeId}`);
  }
}
