package com.example.jpastaffdata.service.impl;

import com.example.jpastaffdata.dto.ElementDTO;
import com.example.jpastaffdata.model.Element;
import com.example.jpastaffdata.model.Project;
import com.example.jpastaffdata.repository.ElementRepository;
import com.example.jpastaffdata.repository.ProjectRepository;
import com.example.jpastaffdata.service.ElementService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElementServiceImpl implements ElementService {

    private final ElementRepository elementRepository;
    private final ProjectRepository projectRepository;

    public ElementServiceImpl(ElementRepository elementRepository, ProjectRepository projectRepository) {
        this.elementRepository = elementRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ElementDTO saveElement(ElementDTO elementDTO) {
        Element element = convertToEntity(elementDTO);
        element = elementRepository.save(element);
        return convertToDTO(element);
    }

    @Override
    public ElementDTO updateElement(ElementDTO elementDTO) {
        Element existingElement = elementRepository.findById(elementDTO.getId())
                .orElseThrow(() -> new RuntimeException("Element not found with id: " + elementDTO.getId()));

        Project project = projectRepository.findById(elementDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + elementDTO.getProjectId()));

        existingElement.setName(elementDTO.getName());
        existingElement.setProject(project);

        Element savedElement = elementRepository.save(existingElement);

        ElementDTO updatedElementDTO = new ElementDTO();
        BeanUtils.copyProperties(savedElement, updatedElementDTO);
        return updatedElementDTO;
    }
    
    @Override
    public ElementDTO getElementById(Long id) {
        Optional<Element> elementOptional = elementRepository.findById(id);
        if (elementOptional.isPresent()) {
            Element element = elementOptional.get();
            return convertToDTO(element);
        }
        return null; // or throw an exception if desired
    }

    @Override
    public List<ElementDTO> getAllElements() {
        List<Element> elements = elementRepository.findAll();
        return convertToDTOList(elements);
    }

    @Override
    public void deleteElement(Long id) {
        elementRepository.deleteById(id);
    }

    public void deleteAllElements(Long id) {
        elementRepository.deleteAll();
    }
    private ElementDTO convertToDTO(Element element) {
        ElementDTO elementDTO = new ElementDTO();
        elementDTO.setId(element.getId());
        elementDTO.setName(element.getName());
        elementDTO.setProjectId((element.getProject().getId()));
        // Set any other necessary fields

        return elementDTO;
    }

    private List<ElementDTO> convertToDTOList(List<Element> elements) {
        return elements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Element convertToEntity(ElementDTO elementDTO) {
        Element element = new Element();
        element.setId(elementDTO.getId());
        element.setName(elementDTO.getName());

        Optional<Project> projectOptional = projectRepository.findById(elementDTO.getProjectId());
        if (projectOptional.isPresent()) {
            element.setProject(projectOptional.get());
        } else {
            // Handle the case when the project is not found
            throw new NoSuchElementException("Project not found with id: " + elementDTO.getProjectId());
        }

        return element;
    }
}
