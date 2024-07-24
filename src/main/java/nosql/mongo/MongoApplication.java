package nosql.mongo;

import nosql.mongo.student.MongoStudent;
import nosql.mongo.student.StudentService;
import nosql.mongo.student.MenoPriezvisko;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class MongoApplication {

    private static final Logger logger = LoggerFactory.getLogger(MongoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MongoApplication.class, args);
        StudentService studentService = context.getBean(StudentService.class);
        testAllStudents(studentService);
        testTasks(studentService);
    }

    private static void testAllStudents(StudentService studentService) {
        studentService.deleteAllStudents();
        studentService.insertAllStudents();
        logger.info("Všetci študenti:");
        studentService.printAllStudents();
        long testId = 1006262;
        logger.info("Študent podľa ID: {}", testId);
        logger.info("{}", studentService.getById(testId));

        logger.info("Vymazanie študenta s ID {}", testId);
        studentService.deleteById(testId);
        MongoStudent deletedStudent = studentService.getById(testId);
        if (deletedStudent == null) {
            logger.info("Študent s ID {} bol úspešne vymazaný.", testId);
        } else {
            logger.warn("Študent s ID {} stále existuje: {}", testId, deletedStudent);
        }

    }

    private static void testTasks(StudentService studentService) {
        String testTitul = "RNDr.";
        logger.info("Študenti s titulom {}:", testTitul);
        List<MenoPriezvisko> titulStudents = studentService.getByTitul(testTitul);
        for (MenoPriezvisko student : titulStudents) {
            logger.info("{} {}", student.getMeno(), student.getPriezvisko());
        }

        String testYear = "1998-01-01";
        String testProgram = "MF";
        logger.info("Študenti v roku {} študujúci program {}:", testYear, testProgram);
        long startTime = System.nanoTime();
        List<MongoStudent> yearProgramStudents = studentService.getByYearAndProgram(testProgram, testYear);
        long endTime = System.nanoTime();
        // bez indexu - 23377500ms
        // z indexom - 17258200 ms
        logger.info("Cas behu {} ms", endTime - startTime);
        for (MongoStudent yearProgramStudent : yearProgramStudents) {
            logger.info("{}", yearProgramStudent);
        }
        logger.info("Tabulka:");
        studentService.countStudentsByYearAndProgram();

    }
}
