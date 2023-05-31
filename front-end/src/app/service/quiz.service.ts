import { Injectable } from '@angular/core';
import { Quiz } from '../model/quiz';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuizService {
  private apiUrl = 'http://localhost:8080/api/v1/quiz'; // Replace with your API endpoint

  private headers: HttpHeaders;
  constructor(private http: HttpClient) {}

  postQuiz(quiz: Quiz): Observable<HttpResponse<Quiz>> {
    this.headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<HttpResponse<Quiz>>(`${this.apiUrl}/saveQuiz`, quiz, {headers: this.headers});
  }

  getAllQuizzess(): Observable<Quiz[]> {
    this.headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`);
    return this.http.get<Quiz[]>(`${this.apiUrl}/getAllQuizzess`, {headers: this.headers});
  }

  getQuizById(quizId: string): Observable<Quiz> {
    this.headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('accessToken')}`);
    return this.http.get<Quiz>(`${this.apiUrl}/getQuizById/${quizId}`, {headers: this.headers});
  }

  deleteQuiz(quizId: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/deleteQuizById/${quizId}`);
  }
}
