package com.lumberjack.quizappbackend;

import com.lumberjack.quizappbackend.dao.UserDao;
import com.lumberjack.quizappbackend.model.quiz.*;
import com.lumberjack.quizappbackend.service.quiz.QuizCategoryService;
import com.lumberjack.quizappbackend.service.quiz.QuizService;
import com.lumberjack.quizappbackend.service.quiz.QuizTakeService;
import com.lumberjack.quizappbackend.service.user.UserService;
import com.mongodb.client.MongoClient;
import com.mongodb.connection.ClusterDescription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizAppBackEndApplicationTests {

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Qualifier("userService")
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizTakeService quizTakeService;

    @Autowired
    private QuizCategoryService quizCategoryService;


    @Test
    void contextLoads() {
    }

    @Test
    public void mongoDbConnectionTest() {
        ClusterDescription clusterDescription = mongoClient.getClusterDescription();
        System.out.println(clusterDescription);
    }

    @Test
    public void saveNewUserToMongoDb() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        User user = new User("admin", "admin", grantedAuthorities);
        mongoTemplate.save(user, "users");
    }

    @Test
    public void checkIfUserExists() {
        Optional<com.lumberjack.quizappbackend.model.User> admin = userDao.findUserByUsername("admin");
        assertTrue(admin.isPresent());
    }

    @Test
    public void getAllUsers() {
        List<com.lumberjack.quizappbackend.model.User> allUsers = userService.getAllUsers();
        assertNotNull(allUsers);
        Optional<com.lumberjack.quizappbackend.model.User> admin = userService.findUserByUsername("admin");
        assertTrue(admin.isPresent());
    }

    @Test
    public void codeTest() {
        List<String> expected = new ArrayList<>(List.of("A", "B", "C", "D", "E"));
        for(int i = 1; i <= 5; i++) {
            System.out.println(Code.codes.get(i));
        }
    }

    @Test
    public void categoryServiceTest() {
        Category category = new Category();
        category.setCategoryName("JavaScript");
        Category java_script = quizCategoryService.findCategoryByName("JavaScript");
 //       assertTrue(java_script == null, "Category must not be present");
        Category categorySaved = quizCategoryService.saveCategory(category);
        boolean exists = quizCategoryService.checkIfNull(categorySaved);
        assertTrue(exists);
        java_script = quizCategoryService.findCategoryByName("JavaScript");
        assertTrue(java_script != null, "Category must be present");
//        boolean categoryDeleted = quizCategoryService.deleteCategoryByName("JavaScript");
//        assertTrue(categoryDeleted);
    }

    @Test
    public void deleteCategory() {
        Category java_script = quizCategoryService.findCategoryByName("JavaScript");
        assertTrue(java_script != null, "Category must be present");
        boolean categoryDeleted = quizCategoryService.deleteCategoryByName("JavaScript");
        assertTrue(categoryDeleted);
        java_script = quizCategoryService.findCategoryByName("JavaScript");
        assertTrue(java_script == null, "Category must not be present");
    }

    @Test
    public void updateCategory() {
        Category category = quizCategoryService.findCategoryByName("JavaScript");
        assertTrue(category != null, "Category must not be null");
        category.setCategoryName("Java");
        quizCategoryService.saveCategory(category);
        List<Category> allCategories = quizCategoryService.getAllCategories();
        assertEquals(1, allCategories.size(), "Must be 1");
    }

    @Test
    public void creatingNewQuizTest() {
        Category category = new Category();
        category.setCategoryName("Java");
        quizCategoryService.saveCategory(category);
        Category java_script = quizCategoryService.findCategoryByName("Java");
        assertTrue(java_script != null, "Category must not be present");
        Category java = quizCategoryService.findCategoryByName("Java");
        Question question = new Question();
        question.setQuestionText("Consider the following code sequence:\n" +
                "\n" +
                "Which of the following statements will compile without syntax or runtime errors if they replace the comment line?");

        question.setQuestionCode("        public class Main{ \n" +
                "            public int i; \n" +
                "            public static void main(String argv[]){ \n" +
                "                Main sc = new Main(); \n" +
                "                // Comment line \n" +
                "            } \n" +
                "         } ");

        Option option1 = new Option();
        option1.setCodeInput("sc.i = 5;");
        Option option2 = new Option();
        option2.setCodeInput("int j = sc.i;");
        Option option3 = new Option();
        option3.setCodeInput("sc.i = 5.0;");
        Option option4 = new Option();
        option4.setCodeInput("System.out.println(sc.i);");
        question.setOptions(List.of(option1, option2, option3, option4));
        question.setCorrectAnswers(List.of("A", "B", "D"));
        question.setQuestionType(QuestionType.MULTIPLE_CHOICE.questionType);
        Set<Option> options = Code.assignCode(question.getOptions());
        question.setOptions(options);

        Question question1 = new Question();
        question1.setQuestionText("What will be printed out if the following code is run with the java Main hello world command?");

        question1.setQuestionCode("        public class Main { \n" +
                "            public static void main(String argv[]) { \n" +
                "               System.out.println(argv[1]); \n" +
                "            } \n" +
                "        } ");

        Option option21 = new Option();
        option21.setCodeInput("world");
        Option option22 = new Option();
        option22.setCodeInput("hello;");
        Option option23 = new Option();
        option23.setCodeInput("hello world;");
        Option option24 = new Option();
        option24.setCodeInput("ArrayIndexOutOfBoundsException is thrown");
        question1.setOptions(List.of(option21, option22, option23, option24));
        question1.setCorrectAnswers(List.of("A"));
        question1.setQuestionType(QuestionType.ONE_CHOICE.questionType);
        List<Option> options2 = Code.assignCode(question1.getOptions());
        question1.setOptions(options2);

        Quiz quiz = new Quiz();
        TimeLimit timeLimit = new TimeLimit();
        timeLimit.setTimeLimitEnabled(true);
        timeLimit.setTime("0:30:0");
        quiz.setTimeLimit(timeLimit);
        quiz.setCategory(java.getCategoryName());
        quiz.setTitle("Quiz for Juniors");
        quiz.setQuestions(List.of(question, question1));
        Quiz quiz1 = quizService.saveQuiz(quiz);
        Quiz quizById = quizService.getQuizById(quiz1.getId());
        assertTrue(quizById != null, "Quiz must not be null");


        // Adding new Take

        Take take = new Take();
        take.setQuiz(quizById);
        take.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance().getTime()));

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 25);

        take.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(instance.getTime()));

        take.setTimeTaken(1500);
        take.setUserName("admin");
        UserQuestionAnswer userQuestionAnswer = new UserQuestionAnswer();
        userQuestionAnswer.setQuestion(question);
        userQuestionAnswer.setAnswerOptions(List.of("A", "B", "D"));
        userQuestionAnswer.setResult(true);
        UserQuestionAnswer userQuestionAnswer2 = new UserQuestionAnswer();
        userQuestionAnswer2.setQuestion(question1);
        userQuestionAnswer2.setAnswerOptions(List.of("B"));
        userQuestionAnswer2.setResult(false);
        take.setUserQuestionAnswers(List.of(userQuestionAnswer, userQuestionAnswer2));
        take.setNumberCorrectAnswers(1);
        take.setNumberIncorrectAnswers(1);
        Take take1 = quizTakeService.saveTake(take);
        Take takeByCriteria = quizTakeService.getTakeById(take1.getId());
        System.out.printf(take1.getId());
        assertNotNull(takeByCriteria);
        takeByCriteria = null;
        takeByCriteria = quizTakeService.findTakeByUsername("admin");
        assertNotNull(takeByCriteria);
        takeByCriteria = null;
        takeByCriteria = quizTakeService.findTakeByQuizId(quizById.getId());
        assertNotNull(takeByCriteria);
        takeByCriteria = null;
        takeByCriteria = quizTakeService.findTakeByQuiz(quizById);
        assertNotNull(takeByCriteria);
        takeByCriteria = null;
        takeByCriteria = quizTakeService.findTakeByCategoryName("Java");
        assertNotNull(takeByCriteria);
        List<Take> allTakes = quizTakeService.getAllTakes();
        allTakes.forEach(t -> quizTakeService.deleteTake(t.getId()));
        allTakes = quizTakeService.getAllTakes();
        assertEquals(0, allTakes.size(), "The size of the list must be 0");
        List<Quiz> allQuizzes = quizService.getAllQuizzes();
        allQuizzes.forEach(q -> quizService.deleteQuizById(q.getId()));
        allQuizzes = quizService.getAllQuizzes();
        assertEquals(0, allQuizzes.size(), "The size must be 0");
    }

    @Test
    public void gettingQuizTest() {
//        Quiz quizById = quizService.findQuizByTitle("Quiz for Juniors");
//        assertTrue(quizById != null, "Quiz must not be null");
        List<Quiz> quizzes = quizService.findQuizByCategoryName("Java");
        assertEquals(1, quizzes.size(), "Must be 1");
    }


}
