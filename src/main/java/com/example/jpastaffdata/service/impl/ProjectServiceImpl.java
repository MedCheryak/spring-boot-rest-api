package com.example.jpastaffdata.service.impl;

import com.example.jpastaffdata.dto.ProjectDTO;
import com.example.jpastaffdata.model.Project;
import com.example.jpastaffdata.repository.ProjectRepository;
import com.example.jpastaffdata.service.ProjectService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        Project project = convertToEntity(projectDTO);
        project = projectRepository.save(project);
        return convertToDTO(project);
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            return convertToDTO(project);
        }
        return null; // or throw an exception if desired
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return convertToDTOList(projects);
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findById(projectDTO.getId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectDTO.getId()));

        existingProject.setName(projectDTO.getName());

        Project savedProject = projectRepository.save(existingProject);

        ProjectDTO updatedProjectDTO = new ProjectDTO();
        BeanUtils.copyProperties(savedProject, updatedProjectDTO);
        return updatedProjectDTO;
    }
    
    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        // Set any other necessary fields

        return projectDTO;
    }

    private List<ProjectDTO> convertToDTOList(List<Project> projects) {
        return projects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Project convertToEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        // Set any other necessary fields

        return project;
    }
}

