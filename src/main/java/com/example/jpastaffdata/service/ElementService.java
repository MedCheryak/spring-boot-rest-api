package com.example.jpastaffdata.service;

import com.example.jpastaffdata.dto.ElementDTO;

import java.util.List;

public interface ElementService {
    ElementDTO saveElement(ElementDTO elementDTO);

    ElementDTO getElementById(Long id);

    List<ElementDTO> getAllElements();

    void deleteElement(Long id);
    
    ElementDTO updateElement(ElementDTO elementDTO);
}
