import { Question } from "./question";
import { TimeLimit } from "./time-limit";

export class Quiz {
  title: string;
  category: string;
  timeLimit: TimeLimit;
  createdAt: string;
  questions: Question[];
  id: string;

  static JsonToQuiz(json: string) {
    const data = JSON.parse(json);
    console.log(data);
    const quiz: Quiz = Object.assign(new Quiz(), data);
    return quiz;
  }
}
