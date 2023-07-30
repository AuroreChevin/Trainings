package fr.fms.dao;

import fr.fms.entities.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;


public interface TrainingRepository extends JpaRepository <Training, Long>{

    List<Training> findByCategoryId(Long id);
}
