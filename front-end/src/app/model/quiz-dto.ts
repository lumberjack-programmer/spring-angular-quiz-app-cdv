import { Question } from "./question";
import { Quiz } from "./quiz";
import { TimeLimit } from "./time-limit";

export class QuizDto {

  id: string;
  title: string;
  category: string;
  timeLimit: TimeLimit;
  createdAt: string;

  static quizToQuizDto(quiz: Quiz) {
    let quizDto = new QuizDto();
    quizDto.category = quiz.category;
    quizDto.createdAt = quiz.createdAt;
    quizDto.id = quiz.id;
    quizDto.title = quiz.title;
    quizDto.timeLimit = quiz.timeLimit;
    return quizDto;
  }
}
