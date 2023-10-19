package com.example.SortBoxs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SortBoxs.entitys.Project;

@Repository
public interface ProjectRepository  extends JpaRepository<Project, Long>{

	List<Project> findByUserId(Long userId);

}
