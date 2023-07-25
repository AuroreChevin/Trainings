package fr.fms.service;

import fr.fms.entities.Category;
import fr.fms.entities.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITrainingService {
    List<Training> getAllTrainings();
    Page<Training> getTrainings(Pageable pageable);

    Training saveTraining(Training t);

    void deleteTraining(Long id);

    Optional<Training> readTraining(Long id);

    List<Category> getAllCategories();

    Optional<Category> readCategoryById(Long id);

    List<Training> getTrainingsByCategoryId(Long id);
}
