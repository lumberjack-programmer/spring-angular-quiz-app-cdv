import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  registrationFormGroup: FormGroup;
  hasError: boolean = false;
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router) { }

  ngOnInit(): void {
    this.registrationFormGroup = this.fb.group({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      passwordRepeat: new FormControl('', [Validators.required]),
    });
  }

  onSubmit() {
    console.log('onSubmit()');
    const password = this.registrationFormGroup.get('password');
    const passwordRepeat = this.registrationFormGroup.get('passwordRepeat');

    if (password?.value != passwordRepeat?.value) {
      this.errorMessage = 'Passwords are not the same';
      this.hasError = true;
    } else if (this.registrationFormGroup.invalid) {
      this.errorMessage = 'Your fields are empty or not valid';
      this.hasError = true;
    } else {
      this.hasError = false;
      console.log('OK');
      const userCreds = JSON.stringify(this.registrationFormGroup.getRawValue());
      console.log(userCreds);
      this.authService.registartion(userCreds).subscribe(
        wasRegistered => {
          if (wasRegistered) {
            this.router.navigate(['/login'],);
          } else {
            this.errorMessage = 'Something went wrong or user with such an email or login already exists';
            this.registrationFormGroup.get('email')?.patchValue('');
            this.registrationFormGroup.get('username')?.patchValue('');
            this.hasError = true;
          }
        }
      );
    }

  }

}
