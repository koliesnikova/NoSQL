package nosql.mongo.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosql.aislike.entity.Student;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "osoba")

public class MongoStudent {
    @Id
    private Long id;
    private String meno;
    private String priezvisko;
    private char kodpohlavie;
    private String skratkaakadtitul;
    private List<MongoStudium> studium = new ArrayList<>();

    public MongoStudent(Student student) {
        id = student.getId();
        meno = student.getMeno();
        priezvisko = student.getPriezvisko();
        kodpohlavie = student.getKodpohlavie();
        skratkaakadtitul = student.getSkratkaakadtitul();
        studium = student.getStudium().stream().map(st -> new MongoStudium(st)).toList();
    }

    public MongoStudent(String meno) {
    }
}


