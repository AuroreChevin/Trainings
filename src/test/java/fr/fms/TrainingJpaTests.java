package fr.fms;

import fr.fms.dao.TrainingRepository;
import fr.fms.entities.Training;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TrainingJpaTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    TrainingRepository trainingRepository;
    @Test
    void should_find_all_trainings(){
        trainingRepository.save(new Training(null, "babar", "personnage", 0, 0, null, null));
        Iterable<Training> trainings = trainingRepository.findAll();
        assertThat(trainings).isNotEmpty();
    }
}
