import { Component, OnInit } from '@angular/core';
import { NgbTimepickerConfig, NgbTimepickerModule, NgbTimeStruct } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormsModule } from '@angular/forms';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { Router } from '@angular/router';
import { Quiz } from 'src/app/model/quiz';
import { QuizService } from 'src/app/service/quiz.service';

@Component({
  selector: 'app-add-quiz',
  templateUrl: './add-quiz.component.html',
  styleUrls: ['./add-quiz.component.scss'],
})
export class AddQuizComponent implements OnInit {

  constructor(config: NgbTimepickerConfig,
    private fb: FormBuilder,
    private router: Router,
    private quizService: QuizService) {
    config.seconds = true;
    config.spinners = false;
  }

  ngOnInit() {
    this.quizFormGroup = this.fb.group({
      title: new FormControl(''),
      category: new FormControl('Java'),
      timeLimit: this.fb.group({
        timeLimitEnabled: [true],
        time: new FormControl(`${this.time.hour}:${this.time.minute}:${this.time.second}`),
      }),
    });

    this.questionForm.addControl('questions', this.formArrayQuestions);
  }

  time: NgbTimeStruct = { hour: 0, minute: 0, second: 0 };
  timePickerDisabled = true;
  seconds = true;
  payLoad = '';
  questionIndex: number = 1;



  toggleSeconds() {
    this.seconds = !this.seconds;
  }

  onCheckboxChange() {
    this.timePickerDisabled = !this.timePickerDisabled;
    const timeLimitFormGroup = this.quizFormGroup.get('timeLimit') as FormGroup;
    const timeLimitEnabled = timeLimitFormGroup.get('timeLimitEnabled');
    timeLimitEnabled?.setValue(this.timePickerDisabled);
  }

  onUpdateTime() {
    const timeLimit = this.quizFormGroup.get('timeLimit') as FormGroup;
    timeLimit.patchValue({
      timeLimitEnambled: this.timePickerDisabled as boolean,
      time: `${this.time.hour}:${this.time.minute}:${this.time.second}`,
    });
  }


  formArrayQuestions: FormArray = new FormArray<FormGroup>([]);
  quizFormGroup: FormGroup;
  questionForm: FormGroup = new FormGroup({});
  arrayOfQuestionsAsFormGroups: FormGroup[] = [];


  get getFormGroup() {
    return this.questionForm.get('questions') as FormArray;
  }

  onQuestionFormUpdated(index: number, childQuestionFormGroup: FormGroup) {
    this.arrayOfQuestionsAsFormGroups[index] = childQuestionFormGroup;
    this.formArrayQuestions.setControl(index, childQuestionFormGroup);
  }


  onSubmit() {
    const questionsArray = this.questionForm.get('questions') as FormArray;
    this.quizFormGroup.addControl('questions', questionsArray);
    this.payLoad = JSON.stringify(this.quizFormGroup.getRawValue());
    console.log(this.payLoad);
    const quiz = Quiz.JsonToQuiz(this.payLoad) as Quiz;
    let quizAdded = false;
    this.quizService.postQuiz(quiz).subscribe(res => {
      console.log('Status code: ', res.status);
      this.router.navigate(['/admin/quizzes']);
    }, err => {
      console.error('Error: ', err);
    });
  }


  addQuestion() {
    const newQuestionFormGroup = this.fb.group({
      questionForm: this.fb.group({
      }) as FormGroup,
    });

    const questionFormArray = this.questionForm.get('questions') as FormArray;
    this.arrayOfQuestionsAsFormGroups.push(newQuestionFormGroup.get('questionForm') as FormGroup);
    questionFormArray.push(newQuestionFormGroup);
    this.questionIndex++;
  }

  convertToForm() {
    const json: string = '{"title":"","category":"Java","timeLimit":{"timeLimitEnabled":true,"time":"0:0:0"},"questions":[{"options":[{"correctAnswer":false,"textInput":""},{"correctAnswer":false,"codeInput":"//Place your code here"}],"questionCode":"//Place your code here","questionText":""},{"options":[{"correctAnswer":false,"textInput":""},{"correctAnswer":false,"codeInput":"//Place your code here"}],"questionCode":"//Place your code here","questionText":""}]}';
    const quiz: Quiz = Quiz.JsonToQuiz(json);
    console.log(quiz.category);
    console.log(quiz.questions[1].questionCode);
    console.log(JSON.stringify(quiz));
    this.quizService.postQuiz(quiz);
    // const questionFormArray = this.questionForm.get('questions') as FormArray;
    // console.log(questionFormArray);
    // questionFormArray.patchValue(parsedData['questions']);
    // console.log(questionFormArray);

    // // Patch the values to the form group
    // this.getFormGroup.patchValue(parsedData);

    // // Handle questions array separately
    // const questionsArray = parsedData.questions as any[];


    // questionsArray.forEach((question) => {
    //   const questionForm = this.fb.group(question.questionForm);
    //   questionFormArray.push(questionForm);
    // });
  }

}
