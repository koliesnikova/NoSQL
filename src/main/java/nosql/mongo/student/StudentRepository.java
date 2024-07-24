package nosql.mongo.student;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<MongoStudent, Long> {

    @Query(value = "{ 'skratkaakadtitul' : ?0 }", fields = "{ '_id': 0, 'meno' : 1, 'priezvisko' : 1 }")
    List<MenoPriezvisko> findNamesByAcademicTitle(String titul);
    @Query(value = "{ 'studium.studijnyProgram.skratka': ?0, 'studium.zaciatokStudia': { $lte: ?1 }, 'studium.koniecStudia': { $gt: ?1 } }")
    List<MongoStudent> findStudentsByProgramAndYear(String skratkaProgramu, String rok);

}
