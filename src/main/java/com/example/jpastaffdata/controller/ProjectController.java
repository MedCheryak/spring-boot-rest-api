package com.example.jpastaffdata.controller;

import com.example.jpastaffdata.dto.ProjectDTO;
import com.example.jpastaffdata.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO savedProject = projectService.saveProject(projectDTO);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        ProjectDTO project = projectService.getProjectById(id);
        if (project != null) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("id") Long id, @RequestBody ProjectDTO projectDTO) {
        if (!id.equals(projectDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        ProjectDTO updatedProjectDTO = projectService.updateProject(projectDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProjectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

