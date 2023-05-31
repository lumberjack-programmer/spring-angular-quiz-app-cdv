import { Option } from "./option";

export class Question {
  questionText: string;
  questionCode: string;
  options: Option[] = [];
  correctAnswers: number[] = [];
  questionType: string;
}
