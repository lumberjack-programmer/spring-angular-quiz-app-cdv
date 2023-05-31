import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs';
import { Take } from 'src/app/model/take';
import { QuizTakeService } from 'src/app/service/quiz-take.service';

@Component({
  selector: 'app-takes',
  templateUrl: './takes.component.html',
  styleUrls: ['./takes.component.scss']
})
export class TakesComponent implements OnInit {
  quizTakes: Take[];
  isQuizListEmpty: boolean = false;
  loading: boolean = true;

  constructor(private takeService: QuizTakeService,
              private router: Router,
              private route: ActivatedRoute) {
    this.route = route;
  }

  ngOnInit(): void {
    this.takeService.getAllTakes().pipe(
      tap((res) => {
        if (res.length === 0) {
          this.isQuizListEmpty = true;
        }
        this.loading = false;
        this.quizTakes = res;
      }),
    ).subscribe();
  }

  onNavigateToTakeDetails(take: Take) {
    this.router.navigate([`admin/takes/${take.id}`]);
  }

  getTimeTakenFormatted(tameTaken: number) {
    return Take.convertTimeTakenToString(tameTaken);
  }

}
