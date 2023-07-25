package fr.fms.web;

import fr.fms.entities.Category;
import fr.fms.entities.Training;
import fr.fms.exceptions.RecordNotFoundException;
import fr.fms.service.ImplTrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {
    @Autowired
    ImplTrainingService implTrainingService;
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = implTrainingService.getAllCategories();
        return new ResponseEntity<List<Category>>(categories,HttpStatus.OK);
    }
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable("id") Long id){
        return implTrainingService.readCategoryById(id).orElseThrow(() -> new RecordNotFoundException("Id de catégorie " +id+ " n'existe pas"));
    }
}
