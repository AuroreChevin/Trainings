package fr.fms.service;

import fr.fms.dao.CategoryRepository;
import fr.fms.dao.TrainingRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplTrainingService implements ITrainingService{
    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public Page<Training> getTrainings(Pageable pageable) {
        return trainingRepository.findAll(pageable);
    }

    @Override
    public Training saveTraining(Training t) {
        return trainingRepository.save(t);
    }
    @Override
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    @Override
    public Optional<Training> readTraining(Long id) {

        return trainingRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> readCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Training> getTrainingsByCategoryId(Long id) {
        return trainingRepository.findByCategoryId(id);
    }

}
