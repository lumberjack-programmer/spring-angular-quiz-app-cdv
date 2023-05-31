import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ControlContainer, FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-option-component',
  templateUrl: './option-component.component.html',
  styleUrls: ['./option-component.component.scss']
})
export class OptionComponentComponent implements OnInit {
@Input('code') optionCode: string;
@Input() questionForm: FormGroup;
@Output() questionFormUpdated = new EventEmitter<FormGroup>();
@Output() optionToBeRemoved = new EventEmitter<void>();


optionTextInput: boolean = false;
optionCodeInput: boolean = false;

constructor (private fb: FormBuilder, private controlContainer: ControlContainer) {}

ngOnInit(): void {
  this.questionForm = <FormGroup>this.controlContainer.control;
}

onAddOptionTextInput() {
  this.optionTextInput = !this.optionTextInput;
  if (this.optionTextInput) {
    this.questionForm.addControl('textInput', new FormControl(''));
  } else {
    this.questionForm.removeControl('textInput');
  }
  this.questionFormUpdated.emit(this.questionForm);
}

onAnyChange() {
  this.questionFormUpdated.emit(this.questionForm);
}

onAddOptionCodeInput() {
  this.optionCodeInput = !this.optionCodeInput;
  if (this.optionCodeInput) {
    this.questionForm.addControl('codeInput', new FormControl(''));
  } else {
    this.questionForm.removeControl('codeInput');
  }
  this.questionFormUpdated.emit(this.questionForm);
}

onRemoveOption() {
  this.optionToBeRemoved.emit();
}


lineNumbers: number[] = [];

addLineNumbers() {
  this.lineNumbers = Array.from({ length: 10 }, (_, index) => index + 1);
}

}
