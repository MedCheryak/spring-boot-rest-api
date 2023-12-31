package com.example.jpastaffdata.dto;

import com.example.jpastaffdata.model.Project;

import lombok.Data;

@Data
public class ElementDTO {
    private Long id;
    private String name;
    private Long projectId;
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}	
}
