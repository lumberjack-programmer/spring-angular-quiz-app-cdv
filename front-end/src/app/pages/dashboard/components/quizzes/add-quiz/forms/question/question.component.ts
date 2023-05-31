import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ControlContainer, FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {
  @Input() questionForm: FormGroup;
  @Output() questionFormUpdated: EventEmitter<FormGroup> = new EventEmitter<FormGroup>();
  @ViewChild('textInputButton', {read: ElementRef, static: false}) textInputButton: ElementRef;
  @ViewChild('codeInputButton', {read: ElementRef, static: false}) codeInputButton: ElementRef;


  constructor(private controlContainer: ControlContainer, private fb: FormBuilder){}

  ngOnInit(): void {
    this.questionForm = <FormGroup>this.controlContainer.control;
  }

  textInputButtonLabel: string = 'Add text input';
  codeInputButtonLabel: string = 'Add code input';


  textInputState: boolean = false;
  codeInputState: boolean = false;

  onQuestionFormUpdate(childQuestionFormGroupUpdated: FormGroup) {
    this.questionForm = childQuestionFormGroupUpdated;
  }

  onAnyChange() {
    this.questionFormUpdated.emit(this.questionForm);
  }

  onAddTextInput() {
    this.textInputState = !this.textInputState;
    if (this.textInputState) {
      this.questionForm.addControl('questionText', new FormControl(''));
      this.questionFormUpdated.emit(this.questionForm);
      this.textInputButtonLabel = 'Remove text input';
      this.textInputButton.nativeElement.classList.remove('btn-add');
      this.textInputButton.nativeElement.classList.add('btn-remove');
    } else {
      this.questionForm.removeControl('questionText');
      this.questionFormUpdated.emit(this.questionForm);
      this.textInputButtonLabel = 'Add text input';
      this.textInputButton.nativeElement.classList.add('btn-add');
      this.textInputButton.nativeElement.classList.remove('btn-remove');
    }
  }


  onAddCodeInput() {
    this.codeInputState = !this.codeInputState;
    if (this.codeInputState) {
      this.questionForm.addControl('questionCode', new FormControl(''));
      this.questionFormUpdated.emit(this.questionForm);
      this.codeInputButtonLabel = 'Remove code input';
      this.codeInputButton.nativeElement.classList.remove('btn-add');
      this.codeInputButton.nativeElement.classList.add('btn-remove');
    } else {
      this.questionForm.removeControl('questionCode');
      this.questionFormUpdated.emit(this.questionForm);
      this.codeInputButtonLabel = 'Add code input';
      this.codeInputButton.nativeElement.classList.add('btn-add');
      this.codeInputButton.nativeElement.classList.remove('btn-remove');
    }
  }


}
