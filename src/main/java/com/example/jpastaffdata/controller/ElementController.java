package com.example.jpastaffdata.controller;

import com.example.jpastaffdata.dto.ElementDTO;
import com.example.jpastaffdata.service.ElementService;
import com.example.jpastaffdata.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/elements")
@CrossOrigin("*")
public class ElementController {

    private final ElementService elementService;
    private final ProjectService projectService;

    public ElementController(ElementService elementService, ProjectService projectService) {
        this.elementService = elementService;
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ElementDTO> createElement(@RequestBody ElementDTO elementDTO) {
        ElementDTO savedElement = elementService.saveElement(elementDTO);
        return new ResponseEntity<>(savedElement, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElementDTO> getElementById(@PathVariable Long id) {
        ElementDTO element = elementService.getElementById(id);
        if (element != null) {
            return new ResponseEntity<>(element, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ElementDTO>> getAllElements() {
        List<ElementDTO> elements = elementService.getAllElements();
        return new ResponseEntity<>(elements, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ElementDTO> updateElement(@PathVariable Long id, @RequestBody ElementDTO elementDTO) {
        if (!id.equals(elementDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        ElementDTO updatedElementDTO = elementService.updateElement(elementDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedElementDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElement(@PathVariable Long id) {
        elementService.deleteElement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
