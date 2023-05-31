import { QuizDto } from "./quiz-dto";
import { UserQuestionAnswer } from "./user-question-answer";

export class Take {
  id: string;
  userName: string;
  quiz: QuizDto;

  startTime: string;
  endTime: string;
  userQuestionAnswers: UserQuestionAnswer[];
  score: number;
  numberCorrectAnswers: number;
  numberIncorrectAnswers: number;
  timeTaken: number;

  static JsonToTake(json: string) {
    const data = JSON.parse(json);
    console.log(data);
    const quiz: Take = Object.assign(new Take(), data);
    return quiz;
  }

  static convertTimeTakenToString(timeTaken: number) {
    let allSeconds = Math.floor(timeTaken / 1000);
    let hour = Math.floor(allSeconds / 3600);
    let min = Math.floor(allSeconds / 60);
    let sec = Math.round(((allSeconds / 60) - min) * 60);
    return `${hour}:${min}:${sec}`;
  }
}
