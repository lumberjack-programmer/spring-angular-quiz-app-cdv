import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ErrorComponent } from './pages/error/error.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { QuizzesComponent } from './pages/dashboard/components/quizzes/quizzes.component';
import { AddQuizComponent } from './pages/dashboard/components/quizzes/add-quiz/add-quiz.component';
import { QuizInterfaceComponent } from './pages/quizzes-list/quiz-interface/quiz-interface.component';
import { QuizzesListComponent } from './pages/quizzes-list/quizzes-list.component';
import { AuthGuard } from './auth/auth.guard';
import { TakesComponent } from './pages/dashboard/components/takes/takes.component';
import { TakeDetailComponent } from './pages/dashboard/components/takes/take-detail/take-detail.component';

const routes: Routes = [
  { path: '', component: QuizzesListComponent, canActivate: [AuthGuard], },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'home', component: HomeComponent },
  {
    path: 'admin', component: DashboardComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    },
    children: [
      {
        path: 'quizzes', component: QuizzesComponent,
        children: [
          { path: 'add', component: AddQuizComponent },
        ]
      },
      {
        path: 'takes', component: TakesComponent,
      },
      { path: 'takes/:id', component: TakeDetailComponent },

    ]
  },
  { path: 'quizzes-list', component: QuizzesListComponent, canActivate: [AuthGuard], },
  { path: 'quiz-interface/:id', component: QuizInterfaceComponent, canActivate: [AuthGuard] },
  { path: '**', component: ErrorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }
