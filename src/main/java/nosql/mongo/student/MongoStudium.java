package nosql.mongo.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosql.aislike.entity.Studium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoStudium {
    private Long id;
    private String zaciatokStudia;
    private String koniecStudia;
    private MongoStudijnyProgram studijnyProgram;


    public MongoStudium(Studium s) {
        this.id = s.getId();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            if (s.getZaciatokStudia() != null && !s.getZaciatokStudia().isEmpty()) {
                Date d = inputFormat.parse(s.getZaciatokStudia());
                this.zaciatokStudia = outputFormat.format(d);
            } else {
                this.zaciatokStudia = null;
            }

            if (s.getKoniecStudia() != null && !s.getKoniecStudia().isEmpty()) {
                Date d = inputFormat.parse(s.getKoniecStudia());
                this.koniecStudia = outputFormat.format(d);
            } else {
                this.koniecStudia = null;
            }
        } catch (ParseException e) {
            throw new RuntimeException("Chyba pri parsovaní dátumu: " + e.getMessage(), e);
        }

        this.studijnyProgram = new MongoStudijnyProgram(s.getStudijnyProgram());
    }
}
