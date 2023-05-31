import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Quiz } from 'src/app/model/quiz';
import { QuizService } from 'src/app/service/quiz.service';

@Component({
  selector: 'app-quizzes-list',
  templateUrl: './quizzes-list.component.html',
  styleUrls: ['./quizzes-list.component.scss']
})
export class QuizzesListComponent implements OnInit {

  quizzessList: Quiz[] = [];
  hasError: boolean = false;
  loading: boolean = true;
  isQuizListEmpty: boolean = false;
  constructor(private router: Router, private quizService: QuizService) {
  }

  ngOnInit(): void {
    this.quizService.getAllQuizzess().subscribe(
      (data) => {
        this.hasError = false;
        if (data.length === 0) {
          this.isQuizListEmpty = true;
        }
        data.forEach(quiz => {
          this.quizzessList.push(quiz);
        });
        this.loading = false;
        // this.quizzessList = data;
      },
      err => {
        this.loading = false;
        this.hasError = true;
      }
    );
  }

  navigateToQuizInterface(index: number) {
     const quiz: Quiz = this.quizzessList[index];
    this.router.navigate([`/quiz-interface/${quiz.id}`]);
  }

  onLogOut() {
    localStorage.clear();
    this.router.navigate([`/login`]);
  }
}
