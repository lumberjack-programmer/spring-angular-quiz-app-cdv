import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router) {}


  loginFormGroup: FormGroup;
  authenticationFailed: boolean = false;

  ngOnInit(): void {
    this.loginFormGroup = this.fb.group({
      username: new FormControl(''),
      password: new FormControl('')
    })
  }

  onSubmit() {
   const userCreds = JSON.stringify(this.loginFormGroup.getRawValue());
   this.authService.login(userCreds).subscribe(
    isAuthenticated => {
      if (isAuthenticated) {
        this.router.navigate(['/quizzes-list'], );
      } else {
        this.authenticationFailed = true;
        this.loginFormGroup.get('username')?.patchValue('');
        this.loginFormGroup.get('password')?.patchValue('');
      }
    }
   );
  }
}
