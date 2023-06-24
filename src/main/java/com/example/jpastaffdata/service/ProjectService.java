package com.example.jpastaffdata.service;

import com.example.jpastaffdata.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);

    ProjectDTO getProjectById(Long id);

    List<ProjectDTO> getAllProjects();

    void deleteProject(Long id);
    
    ProjectDTO updateProject(ProjectDTO projectDTO);
}

