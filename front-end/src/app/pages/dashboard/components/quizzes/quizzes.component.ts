import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, take, tap } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { Quiz } from 'src/app/model/quiz';
import { QuizService } from 'src/app/service/quiz.service';

@Component({
  selector: 'app-quizzes',
  templateUrl: './quizzes.component.html',
  styleUrls: ['./quizzes.component.scss']
})
export class QuizzesComponent implements OnInit  {
  routerC: Router;
  constructor(private router: Router,
              private quizService: QuizService,
              private authService: AuthService){
              this.routerC = router;
  }

  hasError: boolean = false;
  loading: boolean = true;
  quizzessList: Quiz[] = [];
  isQuizListEmpty: boolean = false;

  ngOnInit(): void {

    const auth = this.authService.isAuthenticated().subscribe(
      (res) => {
        // console.log(res);
      }
    );


    this.quizService.getAllQuizzess().pipe(
      tap((res) => {
        if (res.length === 0) {
          this.isQuizListEmpty = true;
        }
        res.forEach(quiz => {
          this.quizzessList.push(quiz);
        });
        this.loading = false;
      }),
      catchError((err) => {
        this.hasError = true;
        console.error(`Error occured: ${err}`);
        return err;
      }),
    ).subscribe();
  }
}
