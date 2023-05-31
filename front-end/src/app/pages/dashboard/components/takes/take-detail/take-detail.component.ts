import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Router } from '@angular/router';
import { tap } from 'rxjs';
import { Take } from 'src/app/model/take';
import { QuizTakeService } from 'src/app/service/quiz-take.service';

@Component({
  selector: 'app-take-detail',
  templateUrl: './take-detail.component.html',
  styleUrls: ['./take-detail.component.scss']
})
export class TakeDetailComponent implements OnInit {
  loading: boolean = true;
  quizId: string;
  quizTake: Take;
  optionCodes: string[] = ['A', 'B', 'C', 'D', 'E', 'F'];
  constructor(private route: ActivatedRoute,
              private router: Router,
              private takeService: QuizTakeService) {}

  ngOnInit(): void {
    this.quizId = this.route.snapshot.paramMap.get('id')!;

    if (this.quizId) {
      this.takeService.getTakeById(this.quizId).pipe(
        tap((res) => {
          console.log(res);
          this.quizTake = res;
          this.loading = false;
        }),
      ).subscribe();
    }
  }

  getUserAnswer( userAnswers: number[], optionId: number) {
    if (userAnswers.includes(optionId)) {
      return true;
    } else {
      return false;
    }
  }
}
