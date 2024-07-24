package nosql.mongo.student;

import nosql.aislike.DaoFactory;
import nosql.aislike.StudentDao;
import nosql.aislike.entity.Student;
import nosql.mongo.StudentsByYearAndProgram;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();
    private final StudentRepository repository;
    private final MongoTemplate mongoTemplate;
    public StudentService(StudentRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public void insertAllStudents() {
        List<Student> students = studentDao.getAll();
        List<MongoStudent> mongoStudents = students.stream().map(
                s -> new MongoStudent(s)
        ).toList();
        repository.saveAll(mongoStudents);
    }

    public void printAllStudents() {
        repository.findAll().forEach(s -> System.out.println(s));
    }

    public void deleteAllStudents() {
        repository.deleteAll();
    }

    public List<MongoStudent> getAll() {
        List<MongoStudent> students = new ArrayList<>();
        repository.findAll().forEach(students::add);
        return students;
    }

    public MongoStudent getById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<MenoPriezvisko> getByTitul(String titul) {
        return repository.findNamesByAcademicTitle(titul);
    }

    public List<MongoStudent> getByYearAndProgram(String studijnyProgram, String rokStudia) {
         return repository.findStudentsByProgramAndYear(studijnyProgram, rokStudia);
    }

    public void countStudentsByYearAndProgram() {
        UnwindOperation unwind = Aggregation.unwind("studium");
        ProjectionOperation project = Aggregation.project()
                .andExpression("studium.zaciatokStudia").as("rok")
                .and("studium.studijnyProgram.skratka").as("skratka");

        GroupOperation group = Aggregation.group("rok", "skratka")
                .count().as("count");

        ProjectionOperation projectResult = Aggregation.project()
                .and("_id.rok").as("rok")
                .and("_id.skratka").as("skratka")
                .and("count").as("count")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(unwind, project, group, projectResult);

        AggregationResults<StudentsByYearAndProgram> results = mongoTemplate.aggregate(aggregation, "osoba", StudentsByYearAndProgram.class);
        List<StudentsByYearAndProgram> studentCounts = results.getMappedResults();

        studentCounts.forEach(System.out::println);
    }
    // https://ishansoninitj.medium.com/spring-data-mongodb-crud-aggregations-views-and-materialized-views-6483abe991d8




}
