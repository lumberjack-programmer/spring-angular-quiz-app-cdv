import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { catchError, of, tap } from 'rxjs';
import { Question } from 'src/app/model/question';
import { Quiz } from 'src/app/model/quiz';
import { QuizDto } from 'src/app/model/quiz-dto';
import { Take } from 'src/app/model/take';
import { UserQuestionAnswer } from 'src/app/model/user-question-answer';
import { QuizTakeService } from 'src/app/service/quiz-take.service';
import { QuizService } from 'src/app/service/quiz.service';

@Component({
  selector: 'app-quiz-interface',
  templateUrl: './quiz-interface.component.html',
  styleUrls: ['./quiz-interface.component.scss']
})
export class QuizInterfaceComponent implements OnInit {
  loading: boolean = true;
  hasError: boolean = false;
  quiz: Quiz;
  quizId: string;
  quizQuestions: Question[] = [];
  currentQuestion: number = 0;
  optionCodes: string[] = ['A', 'B', 'C', 'D', 'E', 'F'];
  showFinishButton: boolean = false;
  take: Take;
  timeTaken: number;

  optionsFormGroupList: FormGroup[] = [];

  constructor(private route: ActivatedRoute,
    private router: Router,
    private quizService: QuizService,
    private fb: FormBuilder,
    private takeService: QuizTakeService) {

}

  ngOnInit(): void {
    this.quizId = this.route.snapshot.paramMap.get('id')!;
    this.take = new Take();
    this.take.userQuestionAnswers = [];
    const test = this.quizService.getQuizById(this.quizId).subscribe(
      (data) => {
        if (data == null) {
          this.router.navigate([`/quizzes-list`]);
        }
        console.log(data);
        this.quiz = data
        this.quiz.questions.forEach(question => {
          this.quizQuestions.push(question);
        });

        for(let question of this.quizQuestions) {
          const tmpForm = this.fb.group({});
          let userQuestionAnswer = new UserQuestionAnswer();
          userQuestionAnswer.question = question;

          userQuestionAnswer.userAnswers = [];
          this.take.userQuestionAnswers.push(userQuestionAnswer);

          for (let i = 0; i < question.options.length; i++) {
            tmpForm.addControl(`${i}`, new FormControl(false));
          }
          this.optionsFormGroupList.push(tmpForm);

        }

        if (this.quiz.timeLimit.timeLimitEnabled) {
          this.startTimer(this.quiz.timeLimit.time);
          const startTime = new Date();
          this.take.startTime = this.getDateAndTime(startTime);
          this.timeTaken = startTime.getTime();
          console.log(this.timeTaken);
        }
        this.loading = false;
        // sessionStorage.setItem('optionsFormGroupList', JSON.stringify(this.optionsFormGroupList));
      },
      err => {
        console.error(`Error: ${err}`);
        this.loading = false;
        this.hasError = true;
      }
    );
  }


  onNextQuestion() {
    if (this.currentQuestion < this.quizQuestions.length - 1) {
      this.currentQuestion++;
    }

    if (this.currentQuestion == this.quizQuestions.length - 1) {
      this.showFinishButton = true;
    }
  }

  onBackQuestion() {
    if (this.currentQuestion > 0) {
      this.currentQuestion--;
      this.showFinishButton = false;
    }
  }

  onFinish() {
    this.router.navigate([`/quizzes-list`]);
    this.pauseTimer();
    sessionStorage.removeItem('hours');
    sessionStorage.removeItem('minutes');
    sessionStorage.removeItem('seconds');

    const endTime = new Date();
    let quizDto = QuizDto.quizToQuizDto(this.quiz);
    this.take.quiz = quizDto;
    this.take.endTime = this.getDateAndTime(endTime);
    this.timeTaken = endTime.getTime() - this.timeTaken;
    this.take.timeTaken = this.timeTaken;
    // console.log(this.getDateAndTime(endTime));
    // console.log(JSON.stringify(this.take));

    this.takeService.postTake(this.take).pipe(
      tap((res) => {
        console.log(res);
      }),
      catchError((err) => {
        console.error(err);
        return of(false)
      })
    ).subscribe();
  }

  getDateAndTime(date: Date) {
    return `${date.getFullYear()}-${date.getMonth()}-${date.getDay()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
  }

  // Timer
  time: number = 30;
  interval: any;
  hours: string = '--';
  minutes: string = '--';
  seconds: string = '--';

  startTimer(time: string) {
    const timeAsStringArray = time.split(':');
    let hours: number;
    let minutes: number;
    let seconds: number;

      hours = parseInt(timeAsStringArray[0]);
      minutes = parseInt(timeAsStringArray[1]);
      seconds = parseInt(timeAsStringArray[2]);

    this.interval = setInterval(() => {

     if (seconds > 0) {
      seconds--;
     } else if (seconds == 0 && minutes > 0) {
      minutes--;
      seconds = 59;
     } else if (minutes == 0 && hours > 0) {
      hours--;
      minutes = 59;
     } else if (minutes == 0 && hours == 0) {
      seconds = 0;
      this.onFinish();
     }

     if (hours >=0 && hours < 10) {
      this.hours = `0${hours}`;
     } else {
      this.hours = hours.toString();
     }
     if (minutes >=0 && minutes < 10) {
      this.minutes = `0${minutes}`;
     } else {
      this.minutes = minutes.toString();
     }
     if (seconds >=0 && seconds < 10) {
      this.seconds = `0${seconds}`;
     } else {
      this.seconds = seconds.toString();
     }
    }, 1000);
  }

  pauseTimer() {
    clearInterval(this.interval);
  }

  onSelectOption(questionIndex: number, optionIndex: number) {
   const formControlOptionValue = this.optionsFormGroupList[questionIndex].get(`${optionIndex}`);
   formControlOptionValue?.setValue(!formControlOptionValue.value);
   if (this.take.userQuestionAnswers[questionIndex].userAnswers.includes(this.quiz.questions[questionIndex].options[optionIndex].optionId)) {
     const index = this.take.userQuestionAnswers[questionIndex].userAnswers.indexOf(this.quiz.questions[questionIndex].options[optionIndex].optionId);
     console.log(`Includes! Index: ${index}, Value: ${this.quiz.questions[questionIndex].options[optionIndex].optionId}`);
     this.take.userQuestionAnswers[questionIndex].userAnswers.splice(index, 1);
   } else {
    console.log(`Doesn't Include! Value: ${this.quiz.questions[questionIndex].options[optionIndex].optionId}`);
     this.take.userQuestionAnswers[questionIndex].userAnswers.push(this.quiz.questions[questionIndex].options[optionIndex].optionId);
   }
   console.log(JSON.stringify(this.take));
  }


}
