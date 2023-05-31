import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { HighlightModule, HIGHLIGHT_OPTIONS } from 'ngx-highlightjs';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { HomeComponent } from './pages/home/home.component';
import { StatsComponent } from './pages/stats/stats.component';
import { ErrorComponent } from './pages/error/error.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { QuizzesComponent } from './pages/dashboard/components/quizzes/quizzes.component';
import { TakesComponent } from './pages/dashboard/components/takes/takes.component';
import { UsersComponent } from './pages/dashboard/components/users/users.component';
import { CategoriesComponent } from './pages/dashboard/components/categories/categories.component';
import { AddQuizComponent } from './pages/dashboard/components/quizzes/add-quiz/add-quiz.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CodeHighlightDirective } from './directive/code-highlight.directive';
import { QuestionComponent } from './pages/dashboard/components/quizzes/add-quiz/forms/question/question.component';
import { OptionComponent } from './pages/dashboard/components/quizzes/add-quiz/forms/question/option/option.component';
import { InputTextComponent } from './pages/dashboard/components/quizzes/add-quiz/forms/question/dynamic-forms/input-text/input-text.component';
import { InputCodeComponent } from './pages/dashboard/components/quizzes/add-quiz/forms/question/dynamic-forms/input-code/input-code.component';
import { OptionComponentComponent } from './pages/dashboard/components/quizzes/add-quiz/forms/question/option/option-component/option-component.component';
import { QuizzesListComponent } from './pages/quizzes-list/quizzes-list.component';
import { QuizDetailComponent } from './pages/quizzes-list/quiz-detail/quiz-detail.component';
import { QuizInterfaceComponent } from './pages/quizzes-list/quiz-interface/quiz-interface.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthGuard } from './auth/auth.guard';
import { TakeDetailComponent } from './pages/dashboard/components/takes/take-detail/take-detail.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    HomeComponent,
    StatsComponent,
    ErrorComponent,
    RegistrationComponent,
    QuizzesComponent,
    TakesComponent,
    UsersComponent,
    CategoriesComponent,
    AddQuizComponent,
    CodeHighlightDirective,
    QuestionComponent,
    OptionComponent,
    InputTextComponent,
    InputCodeComponent,
    OptionComponentComponent,
    QuizzesListComponent,
    QuizDetailComponent,
    QuizInterfaceComponent,
    TakeDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HighlightModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HIGHLIGHT_OPTIONS,
      useValue: {
        fullLibraryLoader: () => import('highlight.js'),
      },
    },
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
