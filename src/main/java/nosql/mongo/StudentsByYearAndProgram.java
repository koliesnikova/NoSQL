package nosql.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsByYearAndProgram {
    private String rok;
    private String skratka;
    private long count;

    public String toString() {
        return "Studijny program: " + skratka + "    Zaciatok studia: " + rok + "    Pocet studentov: " + count;
    }

}