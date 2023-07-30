package fr.fms.web;

import fr.fms.entities.Training;
import fr.fms.exceptions.RecordNotFoundException;
import fr.fms.service.ImplTrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@Slf4j
public class TrainingController {
    @Autowired
    private ImplTrainingService implTrainingService;

    @GetMapping("/trainings")
    public ResponseEntity<List<Training>> getAllTrainings(){
        List<Training> trainings = implTrainingService.getAllTrainings();
        return new ResponseEntity<List<Training>>(trainings, HttpStatus.OK);
    }
    @GetMapping("/trainings/pagination")
    public ResponseEntity<List<Training>> allTrainings(@RequestParam (defaultValue = "0") int page,
                                       @RequestParam (defaultValue = "4") int size){
        Pageable paging = PageRequest.of(page, size);
        List<Training> trainings = implTrainingService.getTrainings(paging).getContent();
        return new ResponseEntity<List<Training>>(trainings, HttpStatus.OK);
    }

    @PostMapping("/trainings/addtraining")
    public ResponseEntity<Training> saveTraining(@RequestBody Training t){
        Training training = implTrainingService.saveTraining(t);
        if(Objects.isNull(training)){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(training.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/trainings/{id}")
    public ResponseEntity<?>deleteTraining(@PathVariable("id") Long id){
        try{
            implTrainingService.deleteTraining(id);
        }catch (Exception e){
            log.error("Impossible de supprimer la formation d'id : {}", id);
            return ResponseEntity.internalServerError().body((e.getCause()));
        }
        log.info("Suppression de la formation d'id : {}", id);
            return ResponseEntity.ok().build();
    }
    @GetMapping("/trainings/{id}")
    public Training getTrainingById(@PathVariable("id") Long id){
      return implTrainingService.readTraining(id).orElseThrow(() -> new RecordNotFoundException("Id de formation " +id+ " n'existe pas"));
    }
    @GetMapping(path="/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getPhoto(@PathVariable("id") Long id) throws IOException{
        byte[] file = null;
        try{
            Training training = implTrainingService.readTraining(id).get();
            if(training.getPhoto()==null) training.setPhoto("unknown.png");
            file = Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/trainings/images/"+training.getPhoto()));
        }catch (Exception e){
            log.error("problème lors du download de l'image correspondant à la formation d'id : {}", id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        return ResponseEntity.ok().body(file);
    }
    @PostMapping(path="/photo/{id}")
    public ResponseEntity<?> uploadPhoto(MultipartFile file, @PathVariable Long id) throws IOException{
        try {
            Training training = implTrainingService.readTraining(id).get();
            training.setPhoto(file.getOriginalFilename());
            Files.write(Paths.get(System.getProperty("user.home")+"/trainings/images/" + training.getPhoto()),file.getBytes());
            implTrainingService.saveTraining(training);
        }
        catch(Exception e) {
            log.error("pb avec upload de l'image correspondant à la formation d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("file upload ok {}",id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/trainings/category/{id}")
    public List<Training> trainingsByCategoryId(@PathVariable("id") Long id){
        return implTrainingService.getTrainingsByCategoryId(id);
    }

}
