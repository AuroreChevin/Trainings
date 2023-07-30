package fr.fms;

import fr.fms.dao.TrainingRepository;
import fr.fms.entities.Training;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class TrainingJpaTests {
    @Autowired
    TrainingRepository trainingRepository;
    @Test
    void should_find_all_trainings(){
        /*trainingRepository.save(new Training(null, "babar", "personnage", 0, 0, null, null));
        Iterable<Training> trainings = trainingRepository.findAll();*/
       // assertThat(trainings).isNotEmpty();
    }
}
