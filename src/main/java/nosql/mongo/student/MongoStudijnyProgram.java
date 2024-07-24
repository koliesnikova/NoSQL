package nosql.mongo.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosql.aislike.entity.StudijnyProgram;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoStudijnyProgram {
    private Long id;
    @Indexed
    private String skratka;
    private String popis;

    public MongoStudijnyProgram(StudijnyProgram studijnyProgram) {
        id = studijnyProgram.getId();
        skratka = studijnyProgram.getSkratka();
        popis = studijnyProgram.getPopis();
    }
}


