import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ControlContainer, FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-option',
  templateUrl: './option.component.html',
  styleUrls: ['./option.component.scss']
})
export class OptionComponent implements OnInit {
@Input() questionForm: FormGroup;
@Output() questionFormUpdated: EventEmitter<FormGroup> = new EventEmitter<FormGroup>();

optionCodes: string[] = ['A', 'B', 'C', 'D', 'E', 'F'];
numberOfOption: number = 1;
optionCodesSelected: string[] = [];


constructor(private controlContainer: ControlContainer, private fb: FormBuilder){
}

optionsFormGroup: FormGroup[] = [];

get options(): FormGroup {
  return this.questionForm.get('options') as FormGroup;
}

ngOnInit(): void {
  this.populateOptionCodes();
  this.questionForm = <FormGroup>this.controlContainer.control;

  const formGroupForOption1 = this.fb.group({
    correctAnswer: [false],
  }) as FormGroup;

  const formGroupForOption2 = this.fb.group({
    correctAnswer: [false],
  }) as FormGroup;
// Here is the place where I stopped...
  this.optionsFormGroup.push(formGroupForOption1);
  this.optionsFormGroup.push(formGroupForOption2);

  this.questionForm.addControl('options', new FormArray([
    formGroupForOption1,
    formGroupForOption2
  ]));

  this.questionFormUpdated.emit(this.questionForm);
}


onQuestionFormUpdate(index: number, childQuestionFormGroupUpdated: FormGroup) {
  const options = this.questionForm.get('options') as FormArray;
  this.optionsFormGroup[index] = childQuestionFormGroupUpdated;
  // console.log(options);
}

private populateOptionCodes() {
  this.optionCodes.forEach((code, index) => {
    if (index <= this.numberOfOption) {
      this.optionCodesSelected.push(code);
    }
  });
}

onAddOption() {
  if (this.numberOfOption < this.optionCodes.length - 1) {
    this.numberOfOption++;
    this.optionCodesSelected = [];
    this.populateOptionCodes();

    const newFormGroupForOption = this.fb.group({
      correctAnswer: [false],
    });

    this.optionsFormGroup.push(newFormGroupForOption);

    const options = this.questionForm.get('options') as FormArray;
    options.push(newFormGroupForOption);

    this.questionFormUpdated.emit(this.questionForm);
  }
}

onRemoveOption(optionCodeToBeRemoved: number) {
  if (this.numberOfOption > 1) {
    const options = this.questionForm.get('options') as FormArray;
    options.removeAt(optionCodeToBeRemoved);
    this.optionsFormGroup.splice(optionCodeToBeRemoved, 1);
    this.optionCodesSelected.splice(optionCodeToBeRemoved, 1);
    this.numberOfOption--;
    this.populateOptionCodes();
    this.questionFormUpdated.emit(this.questionForm);
  }
}

}
